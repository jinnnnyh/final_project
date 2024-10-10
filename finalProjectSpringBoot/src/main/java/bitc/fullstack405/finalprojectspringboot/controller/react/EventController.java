package bitc.fullstack405.finalprojectspringboot.controller.react;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventScheduleEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import bitc.fullstack405.finalprojectspringboot.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RequestMapping("/event")
@RestController
@RequiredArgsConstructor
public class EventController {

  private final EventService eventService;
  private final UserRepository userRepository;

  @PostMapping("/write")
  public ResponseEntity<EventEntity> writeEvent(@RequestBody Map<String, String> eventData) throws ParseException {

    LocalDate eventStartDate = LocalDate.parse(eventData.get("eventStartDate"));
    LocalDate eventEndDate = LocalDate.parse(eventData.get("eventEndDate"));
    LocalTime startTime = LocalTime.parse(eventData.get("startTime"));
    LocalTime endTime = LocalTime.parse(eventData.get("endTime"));

    Long userId = Long.parseLong(eventData.get("userId"));
    UserEntity userEntity = userRepository.findById(userId).orElse(null);

    int calcDate = (int) (ChronoUnit.DAYS.between(eventStartDate, eventEndDate) + 1);
    LocalDate invisibleDate = eventStartDate.minusWeeks(1);
    LocalDate visibleDate = eventStartDate.minusWeeks(2);

    int maxPeople = Integer.parseInt(eventData.get("maxPeople"));

    EventEntity eventEntity = new EventEntity();
    eventEntity.setEventContent(eventData.get("eventContent"));
    eventEntity.setEventTitle(eventData.get("eventTitle"));
    eventEntity.setVisibleDate(visibleDate);
    eventEntity.setInvisibleDate(invisibleDate);
    eventEntity.setPosterUser(userEntity);
    eventEntity.setMaxPeople(maxPeople);
    eventEntity.setIsRegistrationOpen('N');
//    if (!Objects.equals(eventData.get("maxPeople"), "")) {
//      eventEntity.setMaxPeople(Integer.parseInt(eventData.get("maxpeople")));
//    }

    List<EventScheduleEntity> esEntities = new ArrayList<>();

    for (int i = 0; i < calcDate; i++) {
      LocalDate sDate = eventStartDate.plusDays(i);

      EventScheduleEntity esEntity = new EventScheduleEntity();
      esEntity.setEvent(eventEntity);
      esEntity.setStartTime(startTime);
      esEntity.setEndTime(endTime);
      esEntity.setEventDate(sDate);

      esEntities.add(esEntity);
    }
    eventEntity.setScheduleList(esEntities);



    eventService.writeEvent(eventEntity);


    return ResponseEntity.ok(eventEntity);
  }
}
