package bitc.fullstack405.finalprojectspringboot.database.dto.event;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class MyEventResponse {

//    private final Long eventId;
//    private final String posterUserName;
//    private final String eventTitle;
//    private final String eventContent;
//    private final String eventDate;
//    private final Character eventAccept;
//    private final String uploadDate;
//    private final String eventPoster;
//
//    public MyEventResponse(EventEntity event) {
//        this.eventId = event.getEventId();
//        this.posterUserName = event.getPosterUser().getName();
//        this.eventTitle = event.getEventTitle();
//        this.eventContent = event.getEventContent();
//        this.eventDate = event.getEventDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        this.eventAccept = event.getEventAccept();
//        this.uploadDate = event.getUploadDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        this.eventPoster = event.getEventPoster();
//    }
}
