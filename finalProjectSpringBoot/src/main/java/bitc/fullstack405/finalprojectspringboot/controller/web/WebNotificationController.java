package bitc.fullstack405.finalprojectspringboot.controller.web;

import bitc.fullstack405.finalprojectspringboot.database.dto.web.notification.AddNotificationRequest;
import bitc.fullstack405.finalprojectspringboot.database.dto.web.notification.NotificationResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.web.notification.UpdateNotificationRequest;
import bitc.fullstack405.finalprojectspringboot.database.entity.NotificationEntity;
import bitc.fullstack405.finalprojectspringboot.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WebNotificationController {

    private final NotificationService notificationService;

    // 공지 글 등록
    @PostMapping("/notifications")
    public ResponseEntity<NotificationEntity> addNotification(@RequestBody AddNotificationRequest request) {
        NotificationEntity savedNotification = notificationService.save(request);

        return ResponseEntity.ok().body(savedNotification);
    }

    // 공지 글 목록 출력
    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationResponse>> findAllNotification() {
        List<NotificationResponse> notificationList = notificationService.findAllSortedByNotiIdDesc()
                .stream()
                .map(NotificationResponse::new)
                .toList();

        return ResponseEntity.ok().body(notificationList);
    }

    // 공지 글 상세보기
    @GetMapping("/notifications/{notiId}")
    public ResponseEntity<NotificationResponse> findNotification(@PathVariable Long notiId) {
        NotificationEntity notification = notificationService.findById(notiId);

        return ResponseEntity.ok(new NotificationResponse(notification));
    }

    // 공지 글 수정
    @PutMapping("/notifications/{notiId}")
    public ResponseEntity<NotificationEntity> updateNotification(@PathVariable Long notiId, @RequestBody UpdateNotificationRequest request) {
        NotificationEntity updatedNotification = notificationService.update(notiId, request);

        return ResponseEntity.ok(updatedNotification);
    }

    // 공지 글 삭제
    @DeleteMapping("/notifications/{notiId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notiId) {
        notificationService.delete(notiId);

        return ResponseEntity.ok().build();
    }
}