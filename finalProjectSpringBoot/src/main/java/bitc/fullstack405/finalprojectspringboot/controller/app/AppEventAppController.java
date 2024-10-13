package bitc.fullstack405.finalprojectspringboot.controller.app;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.AppEventAppListResponse;
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
    public ResponseEntity<Integer> addApplication(@PathVariable Long eventId, @PathVariable Long userId) throws Exception {

        // 중복 신청 확인(1)
        if (eventAppService.isApplicationExists(eventId, userId)) {
            return ResponseEntity.ok(1);
        }

        // app 테이블에 데이터 한 개 저장, attend_info 테이블에 해당 행사 회차만큼 데이터 저장(2)
        eventAppService.registerEventApplication(eventId, userId);
        return ResponseEntity.ok(2);
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
}