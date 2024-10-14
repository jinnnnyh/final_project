package bitc.fullstack405.finalprojectspringboot.database.dto.app.eventApp;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppUserUpcomingEventResponse {

    // 앱 - [회원] 신청 내역 중 곧 시작하는 행사
    private Long eventId; // 행사 id
    private String eventTitle; // 행사 제목
    private String eventDate; // 행사일자
    private Character eventComp; // 행사 회차 수료 여부
    private String startTime; // 시작 시간
    private String endTime; // 종료 시간
}