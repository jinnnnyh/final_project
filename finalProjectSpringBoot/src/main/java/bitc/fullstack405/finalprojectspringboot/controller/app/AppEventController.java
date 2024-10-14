package bitc.fullstack405.finalprojectspringboot.controller.app;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.AppAdminUpcomingEventResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.AppEventDetailResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.AppEventListResponse;
import bitc.fullstack405.finalprojectspringboot.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class AppEventController {

    private final EventService eventService;

    // 회원, 관리자에게 보일 행사 목록
    // 제목, 게시일, 신청 마감 여부
    // 조건 : 행사 시작일 2주 전(visible_date) <= 오늘 날짜, 승인(event_accept: 2)
    @GetMapping("/accepted-events")
    public ResponseEntity<List<AppEventListResponse>> findAppEvents() {
        List<AppEventListResponse> eventList = eventService.findAcceptedEvents();
        return ResponseEntity.ok().body(eventList);
    }

    // 행사 상세 화면
    // 게시일, 제목, 내용, 이미지, 작성자(이름만), 해당 행사의 schedule 정보(schedule_id, event_date) 리스트(schedule id 기준 오름차순)
    @GetMapping("/accepted-events/{eventId}")
    public ResponseEntity<AppEventDetailResponse> findAppEventDetail(@PathVariable Long eventId) {
        AppEventDetailResponse eventDetail = eventService.findById(eventId);
        return ResponseEntity.ok().body(eventDetail);
    }

    // 회원 - 신청 내역 중 곧 시작하는 행사 1개
    // 조건 : 수료 여부 N, 행사 첫 번째 날, 시간 체크((현재 시각 <= start_time), 이미 해당 회차의 행사가 시작하면 안 뜨게)
    // event id, event title, 조건에 맞는 행사 날짜(eventDate), 수료 여부(eventComp), 해당 회차의 시작(start_time)/종료(end_time) 시간(HH:MM)
    // 이거는 신청 마감 여부 필요 없는지 물어보기
//    @GetMapping("/upcoming-event/{userId}")
//    public ResponseEntity<AppEventAppListResponse> findMyUpcomingEvent(@PathVariable Long userId) {
//        AppMyUpcomingEventResponse upcomingEvent = eventAppService.findMyUpcomingEvent(userId);
//        return ResponseEntity.ok().body(upcomingEvent);
//    }

    // 관리자 - 예정 행사 1개
    // 매개변수 없음
    // 조건 : 오늘 기준으로 가장 가까운 날짜, 시간 체크((현재 시각 <= end_time), 해당 회차의 행사가 종료할 때까지 보이게)
    // event id, event title, 신청 마감 여부(registration) - view 에 보이는 건 아니고 데이터를 쓰려고, 조건에 맞는 행사 날짜(eventDate), 해당 회차의 시작(start_time)/종료(end_time) 시간(HH:MM)
//    @GetMapping("/upcoming-event/admin")
//    public ResponseEntity<AppAdminUpcomingEventResponse> findAdminUpcomingEvent() {
//        Optional<AppAdminUpcomingEventResponse> upcomingEvent = eventService.findAdminUpcomingEvent();
//
//        return upcomingEvent.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

    // 관리자 - 예정 행사 1개
    // 예정 행사 없으면 error
    @GetMapping("/upcoming-event/admin")
    public ResponseEntity<AppAdminUpcomingEventResponse> findAdminUpcomingEvent() {
        AppAdminUpcomingEventResponse upcomingEvent = eventService.findAdminUpcomingEvent();
        return ResponseEntity.ok().body(upcomingEvent);
    }
}
