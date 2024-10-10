package bitc.fullstack405.finalprojectspringboot.controller.app;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.application.AppEventAppListResponse;
import bitc.fullstack405.finalprojectspringboot.service.EventAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class AppEventAppController {

    private final EventAppService eventAppService;

    // 행사 신청 & QR 코드 이미지(eventId-scheduleId-userId) 생성 후 저장
    // 신청 누르면 eventId랑 현재 로그인한 userId 보내줌
    // app 테이블에 데이터 한 개 저장
    // 스케줄 테이블에 해당 행사 id에 해당하는 스케줄 아이디 뽑아오기
    // 스케줄 아이디 그 개수만큼 attend_info에 저장 & 그 정보(eventId, scheduleId, userId) 이용해서 큐알 이미지 생성 후 저장
    @PostMapping("/application/{eventId}/{userId}")
    public ResponseEntity<String> addApplication(@PathVariable Long eventId, @PathVariable Long userId) throws Exception {

        // 중복 신청 확인
        if (eventAppService.isApplicationExists(eventId, userId)) {
            return ResponseEntity.ok("이미 신청한 행사입니다.");
        }

        // app 테이블에 데이터 한 개 저장, attend_info 테이블에 해당 행사 회차만큼 데이터 저장
        eventAppService.registerEventApplication(eventId, userId);
        return ResponseEntity.ok("신청이 완료되었습니다.");
    }

    // 특정 유저의 행사 신청 전체 내역 (행사 id 기준 내림차순)
    // event id, app id, 제목, 신청일, 수료/미수료(Y/N)
    @GetMapping("/application-list/{userId}")
    public ResponseEntity<List<AppEventAppListResponse>> findMyApplication(@PathVariable Long userId) {
        List<AppEventAppListResponse> eventAppList = eventAppService.findMyEvents(userId);
        return ResponseEntity.ok().body(eventAppList);
    }

    // 특정 유저의 행사 수료 내역 (행사 id 기준 내림차순)
    // event id, app id, 제목, 신청일, 행사 수료(Y)
    @GetMapping("/complete-application-list/{userId}")
    public ResponseEntity<List<AppEventAppListResponse>> findMyCompleteApplication(@PathVariable Long userId) {
        List<AppEventAppListResponse> eventAppList = eventAppService.findMyCompleteEvents(userId);
        return ResponseEntity.ok().body(eventAppList);
    }

    // 특정 유저의 행사 미수료 내역 (행사 id 기준 내림차순)
    // event id, app id, 제목, 신청일, 행사 미수료(N)
    @GetMapping("/incomplete-application-list/{userId}")
    public ResponseEntity<List<AppEventAppListResponse>> findMyIncompleteApplication(@PathVariable Long userId) {
        List<AppEventAppListResponse> eventAppList = eventAppService.findMyIncompleteEvents(userId);
        return ResponseEntity.ok().body(eventAppList);
    }

    // QR 코드 이미지 보기 (여러 회차의 QR 코드 이미지 리스트 반환)
    // 여기에 날짜까지 추가해야 함 url에 오늘 날짜 받아와서 해당 qr코드가 있는지 확인해야 함
    // event_id랑 event_schedule 테이블의 event_id와 같아야 함
    // 현재 날짜랑 event_schedule 테이블에 있는 event_date랑 같아야 함
    // 해당 날짜가 아니면 큐알 코드 보기 버튼 비활성화
//    @GetMapping("/application/{eventId}/{userId}")
//    public ResponseEntity<List<String>> findQRImages(@PathVariable Long eventId, @PathVariable Long userId) {
//        List<String> qrImages = eventAppService.findQrImages(eventId, userId);
//        return ResponseEntity.ok(qrImages);
//    }

    ////////////////
    // 특정 회차의 QR 코드 이미지 조회
//    @GetMapping("/qr-image/{scheduleId}/{userId}")
//    public ResponseEntity<String> getQRCodeImage(@PathVariable Long scheduleId, @PathVariable Long userId) {
//        String qrImage = eventService.findQRCodeByScheduleIdAndUserId(scheduleId, userId);
//        return ResponseEntity.ok(qrImage);
//    }
//
//    // 서비스 부분임
//    // 특정 회차의 QR 코드 이미지 조회
//    public String findQRCodeByScheduleIdAndUserId(Long scheduleId, Long userId) {
//        AttendInfoEntity attendInfo = attendInfoRepository.findByScheduleIdAndUserId(scheduleId, userId);
//        return attendInfo != null ? attendInfo.getQrImage() : null;
//    }
    //////////////////





    // 이벤트 id 스케줄 리스트 불러오기 >> 필요 없음



    // 큐알코드 스캔
    // 행사 id, 스케줄 id, 유저 id 들어옴





    // 참석증 볼 수 있는지 없는지 여부는 신청 내역 목록에서 수료 여부 상태(event_comp)를 인텐트로 들고 계속 들어가면 됨
    // 백엔드에서는 수료증에 들어갈 데이터만 보내주기
    // 행사 기간, 행사 마지막 날짜, 협회장 이름 => event 컨트롤러 부분임
}
