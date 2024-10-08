package bitc.fullstack405.finalprojectspringboot.database.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 게시물 수정 시 클라이언트에서 서버로 전달하는 데이터 전달용 DTO 클래스
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNotificationRequest {

    private String notiTitle;
    private String notiContent;
}
