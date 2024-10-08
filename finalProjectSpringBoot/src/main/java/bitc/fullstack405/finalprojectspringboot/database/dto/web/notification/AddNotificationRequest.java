package bitc.fullstack405.finalprojectspringboot.database.dto.web.notification;

import bitc.fullstack405.finalprojectspringboot.database.entity.NotificationEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 클라이언트가 서버로 전달하는 데이터용 DTO 클래스
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddNotificationRequest {

    private String notiTitle;
    private String notiContent;

    // DTO 클래스를 입력받은 데이터를 기준으로 Entity 클래스로 변환
    public NotificationEntity toEntity(UserEntity user) {

        return NotificationEntity.builder()
                .user(user)  // UserEntity를 직접 넣음
                .notiTitle(notiTitle)
                .notiContent(notiContent)
                .build();
    }
}