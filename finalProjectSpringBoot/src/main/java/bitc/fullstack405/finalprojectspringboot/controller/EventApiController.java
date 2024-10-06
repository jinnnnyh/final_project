package bitc.fullstack405.finalprojectspringboot.controller;

import bitc.fullstack405.finalprojectspringboot.database.dto.event.AddEventRequest;
import bitc.fullstack405.finalprojectspringboot.database.dto.event.EventResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.event.UpdateEventRequest;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.service.EventService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventApiController {

    private final EventService eventService;

    // 행사 글 등록 - 행사 테이블 두 개로 분리할 경우 다시 수정
    @PostMapping(value = "/events", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventEntity> addEvent(@RequestPart("request") AddEventRequest request,
                                                @RequestPart(value = "eventPoster", required = false) MultipartFile eventPoster) throws Exception {
        EventEntity savedEvent = eventService.save(request, eventPoster);
        return ResponseEntity.ok().body(savedEvent);
    }

    // 행사 글 목록 - 모두 출력
    @GetMapping("/events/all")
    public ResponseEntity<List<EventResponse>> findAllEvent() {
        List<EventResponse> eventList = eventService.findAllSortedByEventIdDesc()
                .stream()
                .map(EventResponse::new)
                .toList();

        return ResponseEntity.ok().body(eventList);
    }

    // 행사 글 목록 - 행사일 기준 2주 전부터 출력
    @GetMapping("/events/from-visible-date")
    public ResponseEntity<List<EventResponse>> findEventsFromVisibleDate() {
        List<EventResponse> eventList = eventService.findEventsFromVisibleDate()
                .stream()
                .map(EventResponse::new)
                .toList();

        return ResponseEntity.ok().body(eventList);
    }

    // 행사 글 상세보기
    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventResponse> findEvent(@PathVariable Long eventId) {
        EventEntity event = eventService.findById(eventId);

        return ResponseEntity.ok(new EventResponse(event));
    }
    
    // 행사 글 수정
    @PutMapping("/events/{eventId}")
    public ResponseEntity<EventEntity> updateEvent(@PathVariable Long eventId,
                                                    @RequestPart("request") UpdateEventRequest request,
                                                    @RequestPart(value = "eventPoster", required = false) MultipartFile eventPoster) throws Exception {
        EventEntity updatedEvent = eventService.update(eventId, request, eventPoster);

        return ResponseEntity.ok(updatedEvent);
    }

    // 행사 글 삭제
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        eventService.delete(eventId);

        return ResponseEntity.ok().build();
    }
    
    // 행사 승인 처리 (N -> Y, 승인 일자 추가, 승인자 추가)
    @PostMapping("/events/accept/{eventId}/{userId}")
    public ResponseEntity<EventEntity> acceptEvent(@PathVariable Long eventId, @PathVariable Long userId) {
        EventEntity updatedEvent = eventService.acceptEvent(eventId, userId);

        return ResponseEntity.ok(updatedEvent);
    }

    // 행사 승인 취소 처리 (Y -> N, 승인 일자 null, 승인자 null)
    @PostMapping("/events/reject/{eventId}")
    public ResponseEntity<EventEntity> rejectEvent(@PathVariable Long eventId) {
        EventEntity updatedEvent = eventService.rejectEvent(eventId);

        return ResponseEntity.ok(updatedEvent);
    }

    // 승인 여부 확인 (event_accept 값 - Y/N)
    @GetMapping("/events/approval-status/{eventId}")
    public Character approvalStatus(@PathVariable Long eventId) {
        Character status = eventService.approvalStatus(eventId);

        return status;
    }

    // 자신이 신청한 행사 목록 보기(전체)
    @GetMapping("/events/my/all/{userId}")
    public ResponseEntity<List<EventResponse>> findEventsByUserId(@PathVariable Long userId) {
        // userId로 신청한 행사 목록 가져오기
        List<EventResponse> events = eventService.findEventsByUserId(userId);

        return ResponseEntity.ok(events); // 조회한 목록 반환
    }

    // 자신이 신청한 행사 목록 보기(수료)

    // 자신이 신청한 행사 목록 보기(미수료)


    // 자신이 신청한 행사 상세 내용 보기(행사 정보, 참석 여부)

}
