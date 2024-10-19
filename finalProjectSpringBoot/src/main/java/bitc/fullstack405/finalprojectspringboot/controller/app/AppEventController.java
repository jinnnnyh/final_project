package bitc.fullstack405.finalprojectspringboot.controller.app;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.event.AppAdminUpcomingEventResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.event.AppEventDetailResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.event.AppEventListResponse;
import bitc.fullstack405.finalprojectspringboot.service.EventAppService;
import bitc.fullstack405.finalprojectspringboot.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class AppEventController {

    private final EventService eventService;
    private final EventAppService eventAppService;

    // 회원, 관리자에게 보일 행사 목록
    // 제목, 게시일, 신청 마감 여부
    // 조건 : 행사 시작일 2주 전(visible_date) <= 오늘 날짜, 승인(event_accept: 2)
    @GetMapping("/accepted-events")
    public ResponseEntity<List<AppEventListResponse>> findAppEvents() {
        List<AppEventListResponse> eventList = eventService.findAcceptedEvents();
        return ResponseEntity.ok().body(eventList);
    }

    // 행사 안내 - 행사 상세 화면
    // 게시일, 제목, 내용, 이미지, 작성자(이름만), 해당 행사의 schedule 정보(schedule_id, event_date) 리스트(schedule id 기준 오름차순), 행사 수료 여부(userId가 있는 경우)
    // userId가 없는 경우 ex) http://localhost:8080/app/accepted-events/1
    // userId가 있는 경우 ex) http://localhost:8080/app/accepted-events/1?userId=3
    @PostMapping("/accepted-events/{eventId}")
    public ResponseEntity<AppEventDetailResponse> findAppEventDetail(@PathVariable Long eventId, @RequestParam(required = false) Long userId) {

        AppEventDetailResponse eventDetail;

        if(userId == null) {
            eventDetail = eventService.findEventDetail(eventId);
        } else {
            eventDetail = eventService.findAppEventDetail(eventId, userId);
        }

        return ResponseEntity.ok().body(eventDetail);
    }

    // 관리자 - 예정 행사 1개
    // 예정 행사 없으면 error, 앱에서 예정 행사 없음 처리
    // [조건] 오늘 기준으로 가장 가까운 날짜, 시간 체크((현재 시각 <= end_time), 해당 회차의 행사가 종료할 때까지 보이게)
    // [반환] event id, event title, 신청 마감 여부(registration), 조건에 맞는 행사 날짜(eventDate), 해당 회차의 시작(start_time)/종료(end_time) 시간(HH:MM)
    @GetMapping("/upcoming-event/admin")
    public ResponseEntity<AppAdminUpcomingEventResponse> findUpcomingEventForAdmin() {
        AppAdminUpcomingEventResponse upcomingEvent = eventService.findUpcomingEventForAdmin();
        return ResponseEntity.ok().body(upcomingEvent);
    }
}
