package bitc.fullstack405.finalprojectspringboot.database.dto.event;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

// 클라이언트가 서버로 전달하는 데이터용 DTO 클래스
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddEventRequest {
    private String eventTitle;
    private String eventContent;
    private LocalDate eventDate;
    private Character eventAccept;
    private String eventPoster;

    // DTO 클래스를 입력받은 데이터를 기준으로 Entity 클래스로 변환
    public EventEntity toEntity(UserEntity user) {

        return EventEntity.builder()
                .user(user)  // UserEntity를 직접 넣음
                .eventTitle(eventTitle)
                .eventContent(eventContent)
                .eventDate(eventDate)
                .eventAccept('N')
                .eventPoster(eventPoster)
                .build();
    }
}
