package bitc.fullstack405.finalprojectspringboot.database.dto.event;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

// 서버에서 클라이언트로 지정한 행사 게시물 정보를 전달하기 위한 DTO 클래스(목록, 상세 내용)
@Getter
public class EventResponse {

    private final Long eventId;
    private final String name;
    private final String eventTitle;
    private final String eventContent;
    private final String eventDate;
    private final Character eventAccept;
    private final String uploadDate;
    private final String eventPoster;

    public EventResponse(EventEntity event) {
        this.eventId = event.getEventId();
        this.name= event.getUser().getName();
        this.eventTitle = event.getEventTitle();
        this.eventContent = event.getEventContent();
        this.eventDate = event.getEventDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.eventAccept = event.getEventAccept();
        this.uploadDate = event.getUploadDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.eventPoster = event.getEventPoster();
    }
}