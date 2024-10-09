package bitc.fullstack405.finalprojectspringboot.database.dto.app.event;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

// 서버에서 클라이언트로 지정한 행사 게시물 정보를 전달하기 위한 DTO 클래스
// 게시일, 제목, 내용, 이미지, 작성자(이름만)
@Getter
public class AppEventDetailResponse {

    private final Long eventId;
    private final String eventTitle;
    private final String eventContent;
    private final String eventPoster;
    private final String posterUserName;
    private final String visibleDate;
    private final int scheduleCount;

    public AppEventDetailResponse(EventEntity event, int scheduleCount) {
        this.eventId = event.getEventId();
        this.eventTitle = event.getEventTitle();
        this.eventContent = event.getEventContent();
        this.eventPoster = event.getEventPoster();
        this.posterUserName = event.getPosterUser().getName();
        this.scheduleCount = scheduleCount;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        this.visibleDate = event.getVisibleDate().format(formatter);
    }
}