package bitc.fullstack405.finalprojectspringboot.controller.app;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.eventApp.AppEventAppListResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.eventApp.AppUserUpcomingEventResponse;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
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
    private final UserRepository userRepository;

    // 행사 신청 & QR 코드 이미지(eventId-scheduleId-userId) 생성 후 저장
    // 신청 누르면 eventId랑 현재 로그인한 userId 보내줌
    // app 테이블에 데이터 한 개 저장
    // 스케줄 테이블에 해당 행사 id에 해당하는 스케줄 아이디 뽑아오기
    // 스케줄 아이디 그 개수만큼 attend_info 에 저장 & 그 정보(eventId, scheduleId, userId) 이용해서 큐알 이미지 생성 후 저장
    @PostMapping("/application/{eventId}/{userId}")
    public ResponseEntity<Integer> addApplication(@PathVariable Long eventId, @PathVariable Long userId) throws Exception {

        // 중복 신청 확인(1)
        if (eventAppService.isApplicationExists(eventId, userId)) {
            return ResponseEntity.ok(1);
        }

        // 최대 인원 제한 확인(3)
        if(eventAppService.maxPeople(eventId)) {
            return ResponseEntity.ok(3);
        }

        // app 테이블에 데이터 한 개 저장, attend_info 테이블에 해당 행사 회차만큼 데이터 저장(2)
        eventAppService.registerEventApplication(eventId, userId);

        return ResponseEntity.ok(2);
    }

    // 행사 당일 관리자가 신청자 직접 추가
    // 매개변수 : event, userAccount
    @PostMapping("/application-direct/{eventId}/{userAccount}")
    public ResponseEntity<Integer> addApplication(@PathVariable Long eventId, @PathVariable String userAccount) throws Exception {

        UserEntity user = userRepository.findByUserAccount(userAccount);

        // 중복 신청 확인(1)
        if (eventAppService.isApplicationExists(eventId, user.getUserId())) {
            return ResponseEntity.ok(1);
        }

        // 최대 인원 제한 확인(3)
        if(eventAppService.maxPeople(eventId)) {
            return ResponseEntity.ok(3);
        }

        // app 테이블에 데이터 한 개 저장, attend_info 테이블에 해당 행사 회차만큼 데이터 저장(2)
        eventAppService.registerEventApplication(eventId, user.getUserId());
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

    // 회원 - 신청 내역 중 곧 시작하는 행사 1개
    // 예정 행사 없으면 error, 앱에서 예정 행사 없음 처리
    // [조건] 신청 행사 중 오늘 기준으로 가장 가까운 날짜, 시간 체크((현재 시각 <= end_time), 해당 회차의 행사가 종료할 때까지 보이게)
    // [반환] event id, event title, 조건에 맞는 행사 날짜(eventDate), 수료 여부(eventComp), 해당 회차의 시작(start_time)/종료(end_time) 시간(HH:MM)
    @GetMapping("/upcoming-event/{userId}")
    public ResponseEntity<AppUserUpcomingEventResponse> findUpcomingEventForUser(@PathVariable Long userId) {
        AppUserUpcomingEventResponse upcomingEvent = eventAppService.findUpcomingEventForUser(userId);
        return ResponseEntity.ok().body(upcomingEvent);
    }

    // 행사 신청 취소
    // [조건] 행사 첫 번째 회차의 일자가 지나지 않은 거만 취소 가능
    // [반환] 실패 시 1, 성공 시 2
    @DeleteMapping("/application-cancel/{eventId}/{userId}")
    public ResponseEntity<Integer> deleteApplication(@PathVariable Long eventId, @PathVariable Long userId) throws Exception {

        // 실패(1)
        if (eventAppService.beforeEvent(eventId)) {
            return ResponseEntity.ok(1);
        }

        // 성공(2)
        eventAppService.delete(eventId, userId);
        return ResponseEntity.ok(2);
    }
}