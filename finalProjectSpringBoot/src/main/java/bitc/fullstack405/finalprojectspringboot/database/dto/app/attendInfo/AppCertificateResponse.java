package bitc.fullstack405.finalprojectspringboot.database.dto.app.attendInfo;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventScheduleEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class AppCertificateResponse {

    // 앱 - 교육 참석증
    private final String eventTitle; // 행사 제목
    private final String appUserName; // 앱에 로그인한 유저의 이름
    private final String presidentName; // 협회장 이름
    private final List<Map<String, Object>> schedules; // 행사 세부 일정 리스트 (scheduleId, eventDate)

    public AppCertificateResponse(EventEntity event, UserEntity user, String presidentName) {
        this.eventTitle = event.getEventTitle();
        this.appUserName = user.getName();
        this.presidentName = presidentName;

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
