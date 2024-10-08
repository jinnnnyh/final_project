package bitc.fullstack405.finalprojectspringboot.database.dto.web.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

// 클라이언트가 서버로 전달하는 데이터용 DTO 클래스
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddEventRequest {
    private String eventTitle;
    private String eventContent;
    private LocalDate eventDate;
    private String eventPoster;
    private LocalTime startTime;
    private LocalTime endTime;
    private int maxPeople;

    // DTO 클래스를 입력받은 데이터를 기준으로 Entity 클래스로 변환
//    public EventEntity toEntity(UserEntity user) {
//
//        return EventEntity.builder()
//                .posterUser(user)   // 행사 등록자
//                .approver(null) // 행사 승인자는 null로 설정 (나중에 설정됨)
//                .acceptedDate(null) // 행사 승인일자 null로 설정 (나중에 설정됨)
//                .eventTitle(eventTitle)
//                .eventContent(eventContent)
//                .eventDate(eventDate)
//                .eventAccept('N')
//                .eventPoster(eventPoster)
//                .startTime(startTime)
//                .endTime(endTime)
//                .maxPeople(maxPeople)
//                .build();
//    }
}