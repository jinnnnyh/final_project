package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.event.AppAdminUpcomingEventResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.event.AppEventDetailResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.event.AppEventListResponse;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventScheduleEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventScheduleRepository eventScheduleRepository;

    ///////////////////////////
    ////////// <APP> //////////
    ///////////////////////////

    // <APP> 회원에게 보일 행사 목록
    public List<AppEventListResponse> findAcceptedEvents() {
        List<EventEntity> events = eventRepository.findAcceptedEventsWithCapacity();
        return events.stream()
                .map(AppEventListResponse::new)
                .collect(Collectors.toList());
    }

    // <APP> 회원에게 보일 행사 상세 화면
    public AppEventDetailResponse findById(Long eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + eventId));

        return new AppEventDetailResponse(event);
    }

    // <APP> 관리자 - 예정 행사 1개
    public AppAdminUpcomingEventResponse findUpcomingEventForAdmin() {
        List<EventScheduleEntity> eventSchedules = eventScheduleRepository.findUpcomingEventSchedules();

        EventScheduleEntity eventSchedule = eventSchedules.get(0); // 첫 번째 결과 선택

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 10/14 날짜 포맷 수정
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return AppAdminUpcomingEventResponse.builder()
                .eventId(eventSchedule.getEvent().getEventId())
                .eventTitle(eventSchedule.getEvent().getEventTitle())
                .isRegistrationOpen(eventSchedule.getEvent().getIsRegistrationOpen())
                .eventDate(eventSchedule.getEventDate().format(dateFormatter))
                .startTime(eventSchedule.getStartTime().format(timeFormatter))
                .endTime(eventSchedule.getEndTime().format(timeFormatter))
                .build();
    }


    ///////////////////////////
    ////////// <WEB> //////////
    ///////////////////////////
}
