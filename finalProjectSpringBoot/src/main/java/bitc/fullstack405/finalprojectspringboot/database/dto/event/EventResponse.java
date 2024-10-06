package bitc.fullstack405.finalprojectspringboot.database.dto.event;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

// 서버에서 클라이언트로 지정한 행사 게시물 정보를 전달하기 위한 DTO 클래스(목록, 상세 내용)
@Getter
public class EventResponse {

    private final Long eventId;
    private final String posterUserName;
    private final String approverName;
    private final String eventTitle;
    private final String eventContent;
    private final String eventDate;
    private final String uploadDate;
    private final String eventPoster;
    private final String acceptedDate;
    private final String startTime;
    private final String endTime;
    private final int maxPeople;

    public EventResponse(EventEntity event) {
        this.eventId = event.getEventId();
        this.posterUserName = event.getPosterUser().getName();
        this.approverName = event.getApprover() != null ? event.getApprover().getName() : null;
        this.eventTitle = event.getEventTitle();
        this.eventContent = event.getEventContent();
        this.eventDate = event.getEventDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.uploadDate = event.getUploadDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.eventPoster = event.getEventPoster();
        this.acceptedDate = event.getAcceptedDate() != null ? event.getAcceptedDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")) : null;
        this.startTime = event.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.endTime = event.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.maxPeople = event.getMaxPeople();
    }
}