package bitc.fullstack405.finalprojectspringboot.database.dto.app.event;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import lombok.Getter;

// 서버에서 클라이언트로 지정한 행사 게시물 정보를 전달하기 위한 DTO 클래스(목록, 상세 내용)
// 웹은 여기에 accepted_date랑 approver만 추가하기


// 게시일, 제목, 내용, 이미지, 작성자(이름만)
@Getter
public class AppEventDetailResponse {

    private final Long eventId;
    private final String eventTitle;
    private final String eventContent;
    private final String eventPoster;
    private final String posterUserName;

    public AppEventDetailResponse(EventEntity event) {
        this.eventId = event.getEventId();
        this.eventTitle = event.getEventTitle();
        this.eventContent = event.getEventContent();
        this.eventPoster = event.getEventPoster();
        this.posterUserName = event.getPosterUser().getName();
    }


    // event 테이블 : event_id, max_people, event_title, event_content, event_poster, upload_date, user_id
    // event schedule 테이블(event_id로 가져오기) : schedule_id, event_date, start_time, end_time

    // 행사 시작 시간, 종료시간, 작성자(이름)
    // event 테이블
//    private final Long eventId;
//    private final int eventAccept;
//    private final String eventTitle;
//    private final String eventContent;
//    private final String eventPoster;
//    private final String visibleDate;
//    private final String invisibleDate;
//    private final String posterUserName;
//    private final Character isRegistrationOpen;
//    private final int maxPeople;
//    private final String uploadDate;
//
//    // 행사 스케줄 리스트 추가
//    private final List<EventScheduleDTO> scheduleList;
//
//    // 생성자
//    public AppEventDetailResponse(EventEntity event, List<EventScheduleDTO> scheduleList) {
//        this.eventId = event.getEventId();
//        this.eventAccept = event.getEventAccept();
//        this.eventTitle = event.getEventTitle();
//        this.eventContent = event.getEventContent();
//        this.eventPoster = event.getEventPoster();
//        this.visibleDate = event.getVisibleDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        this.invisibleDate = event.getInvisibleDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        this.posterUserName = event.getPosterUser().getName();
//        this.isRegistrationOpen = event.getIsRegistrationOpen();
//        this.maxPeople = event.getMaxPeople();
//        this.uploadDate = event.getUploadDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//
//        // scheduleList를 DTO로 변환한 결과를 저장
//        this.scheduleList = scheduleList;
//    }
//
//
//
//    // event_schedule 테이블
//    private final Long scheduleId;
//    private final String eventDate;
//    private final String startTime;
//    private final String endTime;
//
//    public EventResponse(EventEntity event, EventsScheduleEntity eventSchedule) {
//        this.eventId = event.getEventId();
//        this.eventAccept = event.getEventAccept();
//        this.eventTitle = event.getEventTitle();
//        this.eventContent = event.getEventContent();
//        this.eventPoster = event.getEventPoster();
//        this.visibleDate
//
//        this.posterUserName = event.getPosterUser().getName();
//        this.posterUserRole = String.valueOf(event.getPosterUser().getRole());
//        this.approverName = event.getApprover() != null ? event.getApprover().getName() : null;
//        this.eventDate = event.getEventDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        this.uploadDate = event.getUploadDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        this.acceptedDate = event.getAcceptedDate() != null ? event.getAcceptedDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")) : null;
//        this.visibleDate = event.getVisibleDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        this.maxPeople = event.getMaxPeople();
//        this.accept = event.getEventAccept();
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        this.startTime = event.getStartTime().format(formatter);
//        this.endTime = event.getEndTime().format(formatter);
//    }
}