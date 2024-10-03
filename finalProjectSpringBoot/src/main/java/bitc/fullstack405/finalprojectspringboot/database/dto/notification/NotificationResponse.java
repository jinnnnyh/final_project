package bitc.fullstack405.finalprojectspringboot.database.dto.notification;

import bitc.fullstack405.finalprojectspringboot.database.entity.NotificationEntity;
import lombok.Getter;

import java.time.LocalDateTime;

// 서버에서 클라이언트로 지정한 공지사항 게시물 정보를 전달하기 위한 DTO 클래스
@Getter
public class NotificationResponse {

    private final Long notiId;
    private final String userName;
    private final String notiTitle;
    private final String notiContent;
    private final LocalDateTime notiDate;

    public NotificationResponse(NotificationEntity notification) {
        this.notiId = notification.getNotiId();
        this.userName= notification.getUser().getUsername();
        this.notiTitle = notification.getNotiTitle();
        this.notiContent = notification.getNotiContent();
        this.notiDate = notification.getNotiDate();
    }
}