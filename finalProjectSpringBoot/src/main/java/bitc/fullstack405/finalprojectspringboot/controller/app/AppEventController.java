package bitc.fullstack405.finalprojectspringboot.controller.app;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.event.AppEventDetailResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.event.AppEventListResponse;
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

    // 회원에게 보일 행사 상세 화면
    // 게시일, 제목, 내용, 이미지, 작성자(이름만), 해당 행사의 schedule 리스트(오름차순)
    @GetMapping("/accepted-events/{eventId}")
    public ResponseEntity<AppEventDetailResponse> findAppEventDetail(@PathVariable Long eventId) {
        AppEventDetailResponse eventDetail = eventService.findById(eventId);
        return ResponseEntity.ok().body(eventDetail);
    }
}
