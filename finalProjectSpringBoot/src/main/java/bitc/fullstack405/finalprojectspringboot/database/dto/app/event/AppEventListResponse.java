package bitc.fullstack405.finalprojectspringboot.database.dto.app.event;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class AppEventListResponse {

    // 앱 - 회원이 볼 행사 목록

    // 서비스에서 쓸 데이터 : 행사 id, event_accept(2인지 확인), is_registration_open(Y인지 확인)
    // visible_date <= 현재 날짜 <= invisible_date

    // event 테이블 : event_id, max_people, event_title, event_content, event_poster, upload_date, user_id
    // event schedule 테이블(event_id로 가져오기) : schedule_id, event_date, start_time, end_time

    // 행사 시작 시간, 종료시간, 작성자(이름)
    private final Long eventId; // 행사 id
    private final String eventTitle; // 제목
    private final String uploadDate; // 등록일
    private final Character isRegistrationOpen; // 신청 마감 여부

    public AppEventListResponse(EventEntity event) {
        this.eventId = event.getEventId();
        this.eventTitle = event.getEventTitle();
        this.isRegistrationOpen = event.getIsRegistrationOpen();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        this.uploadDate = event.getUploadDate().format(formatter);
    }
}
