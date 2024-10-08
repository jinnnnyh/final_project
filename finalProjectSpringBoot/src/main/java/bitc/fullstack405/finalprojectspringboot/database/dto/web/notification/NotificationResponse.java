package bitc.fullstack405.finalprojectspringboot.database.dto.web.notification;

import bitc.fullstack405.finalprojectspringboot.database.entity.NotificationEntity;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

// 서버에서 클라이언트로 지정한 공지사항 게시물 정보를 전달하기 위한 DTO 클래스
@Getter
public class NotificationResponse {

    private final Long notiId;
    private final String name;
    private final String notiTitle;
    private final String notiContent;
    private final String notiDate;

    public NotificationResponse(NotificationEntity notification) {
        this.notiId = notification.getNotiId();
        this.name= notification.getUser().getName();
        this.notiTitle = notification.getNotiTitle();
        this.notiContent = notification.getNotiContent();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.notiDate = notification.getNotiDate().format(formatter);
    }
}