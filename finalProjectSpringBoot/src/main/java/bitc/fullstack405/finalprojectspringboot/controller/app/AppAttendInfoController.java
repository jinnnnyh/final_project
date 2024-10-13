package bitc.fullstack405.finalprojectspringboot.controller.app;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.AppCertificateResponse;
import bitc.fullstack405.finalprojectspringboot.service.AttendInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class AppAttendInfoController {

    private final AttendInfoService attendInfoService;

    // QR 이미지 보기
    // schedule_id, 여러 회차의 행사 일자, QR 코드 리스트 반환(schedule_id 기준 오름차순)
    // 일주일 전 QR 코드 보기 버튼 활성화 => 앱에서 처리
    @GetMapping("/qr-image/{eventId}/{userId}")
    public ResponseEntity<List<Map<String, Object>>> findQRImages(@PathVariable Long eventId, @PathVariable Long userId) {
        List<Map<String, Object>> qrImages = attendInfoService.findQrImages(eventId, userId);
        return ResponseEntity.ok(qrImages);
    }

    // QR 스캔
    // 매개변수로 event id, schedule id, user id 들어옴
    // [반환] QR 스캔 실패: 1, QR 스캔 성공: 2
    // event_schedule, event_app, attend_info, user 테이블 사용

    // 1. 올바른 QR 코드인지 확인
    // 매개변수로 받아온 schedule_id와 user_id에 맞는 데이터가 attend_info 테이블에 있는지 확인, 현재 날짜가 event_app 테이블의 eventDate 와 같은지 확인 => 데이터가 없거나 날짜가 다르면 잘못된 QR 코드
    // attend_info 테이블의 check_in_time 이랑 check_out_time 컬럼에 데이터가 둘 다 null 이 아니면 => 이미 사용한 QR 코드

    // 2. 입장인지 퇴장인지 체크(attend_info 테이블의 check_in_time 이 null 이면 입장, null 이 아니면 퇴장)

    // 3. [입장일 경우]
    // attend_info 테이블의 attend_date 에 현재 날짜, check_in_time 에 현재 시각 넣어서 db 업데이트

    // 4. [퇴장일 경우]
    // attend_info 테이블의 check_out_time 에 현재 시각 넣어서 db 업데이트
    // 회차 수료 조건 확인 후 db 업데이트(attend_info 테이블의 attend_comp 를 N->Y 변경)

    // [회차 수료 조건]
    // 입장 데이터 있는지(check_in_time 이 null 이 아니어야 함) 확인
    // 지각 아닌지(check_in_time <= start_time) 확인
    // 행사 끝까지 있었는지(퇴장 체크 시 현재 시각 >= end_time) 확인
    // start_time, end_time 은 매개변수로 받아온 scheduleId와 event_schedule 테이블의 scheduleId와 같은 데이터로 가져옴

    // 5. [행사 수료]
    // 마지막 회차 퇴장 체크 시 해당 행사의 attend_info 테이블 - attend_comp 가 모두 Y => 매개변수로 받아온 eventId와 event_app 테이블의 eventId와 같은 데이터의 event_comp 컬럼을 N->Y 변경
    @PutMapping("/qr-scan/{eventId}/{scheduleId}/{userId}")
    public ResponseEntity<Integer> qrScan(@PathVariable Long eventId, @PathVariable Long scheduleId, @PathVariable Long userId) {

        // QR 스캔 실패
        // 올바른 행사 정보인지 확인 (eventDate, scheduleId, userId 가 DB 정보와 맞지 않음)
        // 이미 입장/퇴장 체크를 다 한 QR 코드인지 확인 (check_in_time, check_out_time 둘 다 null 아님)
        if (!attendInfoService.validEvent(scheduleId, userId) || attendInfoService.usedQR(scheduleId, userId)) {
            return ResponseEntity.ok(1);
        }

        // QR 스캔 성공
        // 입장/퇴장 업데이트, 회차별 수료/미수료 업데이트, 행사 수료/미수료 업데이트
        attendInfoService.qrScan(eventId, scheduleId, userId);
        return ResponseEntity.ok(2);
    }

    // 수료증 발급
    // 참석증 볼 수 있는지 없는지 여부는 신청 내역 목록에서 수료 여부 상태(event_comp)를 인텐트로 들고 계속 들어가면 됨
    // 백엔드에서는 수료증에 들어갈 데이터만 보내주기
    // [매개변수] event id, user id
    // [반환] QR 이미지 저장(행사 신청)처럼 schedule id, 이벤트 날짜, 행사 제목, 유저 이름, 협회장 이름 보내주면 됨
    @GetMapping("/certificate/{eventId}/{userId}")
    public ResponseEntity<AppCertificateResponse> certificate(@PathVariable Long eventId, @PathVariable Long userId) {
        AppCertificateResponse certificateData = attendInfoService.generateCertificate(eventId, userId);
        return ResponseEntity.ok(certificateData);
    }
}