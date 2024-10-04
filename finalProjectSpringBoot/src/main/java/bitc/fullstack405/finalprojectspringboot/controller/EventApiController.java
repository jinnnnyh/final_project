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

    // 행사 글 등록
    @PostMapping(value = "/events", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventEntity> addEvent(@RequestPart("request") AddEventRequest request,
                                                @RequestPart(value = "eventPoster", required = false) MultipartFile eventPoster) throws Exception {
        EventEntity savedEvent = eventService.save(request, eventPoster);
        return ResponseEntity.ok().body(savedEvent);
    }

    // 행사 글 목록 출력
    @GetMapping("/events")
    public ResponseEntity<List<EventResponse>> findAllEvent() {
        List<EventResponse> eventList = eventService.findAllSortedByEventIdDesc()
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
    
    // 행사 승인 처리 (N -> Y)
    @PostMapping("/events/accept/{eventId}")
    public ResponseEntity<EventEntity> acceptEvent(@PathVariable Long eventId) {
        EventEntity updatedEvent = eventService.acceptEvent(eventId);

        return ResponseEntity.ok(updatedEvent);
    }

    // 행사 승인 취소 처리 (Y -> N)
    @PostMapping("/events/reject/{eventId}")
    public ResponseEntity<EventEntity> rejectEvent(@PathVariable Long eventId) {
        EventEntity updatedEvent = eventService.rejectEvent(eventId);

        return ResponseEntity.ok(updatedEvent);
    }

    // 자신이 신청한 행사 목록 보기
//    @GetMapping("/events/{userId}")
//    public ResponseEntity<List<EventResponse>> findEventsByUserId(@PathVariable Long userId) {
//        // 자신이 신청한 행사 목록 보기
//
//
//        // 자신이 신청한 내역 보기
//    }

    // 자신이 신청한 행사 상세 내용 보기(행사 정보, 참석 여부)

}
