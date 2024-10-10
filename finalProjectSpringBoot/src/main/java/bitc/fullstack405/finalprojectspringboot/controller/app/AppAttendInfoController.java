package bitc.fullstack405.finalprojectspringboot.controller.app;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.qr.AppQRScanResponse;
import bitc.fullstack405.finalprojectspringboot.service.AttendInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class AppAttendInfoController {

    private final AttendInfoService attendInfoService;

    // QR 코드 이미지 스캔
    // 매개변수로 event id, schedule id, user id 들어옴
    // [반환] 올바른 QR 코드일 시 행사 제목, 회원 이름, 회원 전화번호 값 반환해야 함
    // event_schedule, event_app, attend_info, user 테이블 사용

    // 1. 올바른 QR 코드인지 확인
    // 매개변수로 받아온 schedule_id와 user_id에 맞는 데이터가 attend_info 테이블에 있는지 확인 => 데이터가 없으면 잘못된 QR 코드
    // attend_info 테이블의 check_in_time 이랑 check_out_time 컬럼에 데이터가 둘 다 null 이 아니면 => 이미 사용한 QR 코드

    // 2. 입장인지 퇴장인지 체크(attend_info 테이블의 check_in_time 이 null 이면 입장, null 이 아니면 퇴장)

    // 3. [입장일 경우]
    // attend_info 테이블의 attend_date 에 현재 날짜, check_in_time 에 현재 시각 넣어서 db 업데이트

    // 4. [퇴장일 경우]
    // attend_info 테이블의 attend_date 에 현재 날짜, check_out_time 에 현재 시각 넣어서 db 업데이트
    // 회차별 수료 조건 확인 후 데이터 업데이트(attend_info 테이블의 attend_comp 를 N->Y 변경)
    // [회차별 수료 조건]
    // 입장 데이터 있는지(check_in_time 이 null 이 아니어야 함) 확인
    // 지각 아닌지(check_in_time <= start_time) 확인
    // 행사 끝까지 있었는지(check_out_time >= end_time) 확인
    // start_time, end_time 은 매개변수로 받아온 scheduleId와 event_schedule 테이블의 scheduleId와 같은 데이터로 가져옴

    // 5. [행사 수료]
    // 마지막 회차 퇴장 체크 시 attend_info 테이블의 attend_comp 가 모두 Y면 매개변수로 받아온 eventId와 event_app 테이블의 eventId와 같은 데이터의 event_comp 컬럼을 N->Y 변경
    @GetMapping("/qr-scan/{eventId}/{scheduleId}/{userId}")
    public ResponseEntity<AppQRScanResponse> qrScan(@PathVariable Long eventId, @PathVariable Long scheduleId, @PathVariable Long userId) {
        // 서비스 레이어에서 QR 코드 확인 및 처리
        AppQRScanResponse response = attendInfoService.qrScan(eventId, scheduleId, userId);
        return ResponseEntity.ok(response);
    }

    // 수료증 발급
}
