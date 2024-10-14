package bitc.fullstack405.finalprojectspringboot.database.dto.app.event;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventScheduleEntity;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 서버에서 클라이언트로 지정한 행사 게시물 정보를 전달하기 위한 DTO 클래스
// 게시일, 제목, 내용, 이미지, 작성자(이름만)
@Getter
public class AppEventDetailResponse {

    private final Long eventId; // 행사 id
    private final String eventTitle; // 행사 제목
    private final String eventContent; // 행사 내용
    private final String eventPoster; // 행사 이미지
    private final String posterUserName; // 작성자 이름
    private final String visibleDate; // 게시일
    private final List<Map<String, Object>> schedules; // 행사 세부 일정 리스트 (scheduleId, eventDate)

    public AppEventDetailResponse(EventEntity event) {
        this.eventId = event.getEventId();
        this.eventTitle = event.getEventTitle();
        this.eventContent = event.getEventContent();
        this.eventPoster = event.getEventPoster();
        this.posterUserName = event.getPosterUser().getName();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 10/14 날짜포맷변경
        this.visibleDate = event.getVisibleDate().format(formatter);

        // 스케줄 리스트를 scheduleId 기준으로 오름차순 정렬하고, scheduleId와 eventDate를 맵핑하여 리스트로 변환
        this.schedules = event.getScheduleList().stream()
                .sorted(Comparator.comparingLong(EventScheduleEntity::getScheduleId)) // scheduleId 기준 정렬
                .map(schedule -> {
                    Map<String, Object> scheduleMap = new HashMap<>();
                    scheduleMap.put("scheduleId", schedule.getScheduleId());
                    scheduleMap.put("eventDate", schedule.getEventDate());
                    return scheduleMap;
                })
                .collect(Collectors.toList());
    }
}