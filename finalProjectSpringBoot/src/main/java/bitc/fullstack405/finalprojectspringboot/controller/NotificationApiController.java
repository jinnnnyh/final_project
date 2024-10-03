package bitc.fullstack405.finalprojectspringboot.controller;

import bitc.fullstack405.finalprojectspringboot.database.dto.notification.AddNotificationRequest;
import bitc.fullstack405.finalprojectspringboot.database.dto.notification.NotificationResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.notification.UpdateNotificationRequest;
import bitc.fullstack405.finalprojectspringboot.database.entity.NotificationEntity;
import bitc.fullstack405.finalprojectspringboot.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // 스프링 시큐리티 무시하려고 잠시 추가함, 나중엔 삭제하기
@RestController
@RequiredArgsConstructor
public class NotificationApiController {

    private final NotificationService notificationService;

    // 공지 글 등록
    @PostMapping("/notifications")
    public ResponseEntity<NotificationEntity> addArticle(@RequestBody AddNotificationRequest request) {
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
    public ResponseEntity<NotificationResponse> findArticle(@PathVariable Long notiId) {
        NotificationEntity notification = notificationService.findById(notiId);

        return ResponseEntity.ok(new NotificationResponse(notification));
    }

    // 공지 글 수정
    @PutMapping("/notifications/{notiId}")
    public ResponseEntity<NotificationEntity> updateArticle(@PathVariable Long notiId, @RequestBody UpdateNotificationRequest request) {
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