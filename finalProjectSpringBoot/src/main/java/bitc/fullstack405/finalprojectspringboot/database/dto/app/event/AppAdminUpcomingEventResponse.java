package bitc.fullstack405.finalprojectspringboot.database.dto.app.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppAdminUpcomingEventResponse {

    // 앱 - [관리자] 곧 시작하는 행사
    private Long eventId; // 행사 id
    private String eventTitle; // 행사 제목
    private Character isRegistrationOpen; // 신청 마감 여부
    private String eventDate; // 행사일자
    private String startTime; // 시작 시간
    private String endTime; // 종료 시간
}