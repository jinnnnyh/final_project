package bitc.fullstack405.finalprojectspringboot.controller.react;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventScheduleEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import bitc.fullstack405.finalprojectspringboot.service.EventService;
import bitc.fullstack405.finalprojectspringboot.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
  private final FileUtils fileUtils;

  @PostMapping("/write")
  public ResponseEntity<EventEntity> writeEvent(
      @RequestParam("eventTitle") String eventTitle,
      @RequestParam("eventContent") String eventContent,
      @RequestParam("eventStartDate") String eventStartDate,
      @RequestParam("eventEndDate") String eventEndDate,
      @RequestParam("startTime") String startTime,
      @RequestParam("endTime") String endTime,
      @RequestParam("maxPeople") String maxPeople,
      @RequestParam("userId") String userId,
      @RequestParam("file") MultipartFile file
  ) throws Exception {

    LocalDate startEventDate = LocalDate.parse(eventStartDate);
    LocalDate endEventDate = LocalDate.parse(eventEndDate);
    LocalTime startLocalTime = LocalTime.parse(startTime);
    LocalTime endLocalTime = LocalTime.parse(endTime);

    Long parsedUserId = Long.parseLong(userId);
    UserEntity userEntity = userRepository.findById(parsedUserId).orElse(null);

    int calcDate = (int) (ChronoUnit.DAYS.between(startEventDate, endEventDate) + 1);
    LocalDate invisibleDate = startEventDate.minusWeeks(1);
    LocalDate visibleDate = startEventDate.minusWeeks(2);

    int parsedMaxPeople = Integer.parseInt(maxPeople);

    String savedFileName = null;
    if (file != null && !file.isEmpty()) {
      savedFileName = fileUtils.parseFileInfo(file);
    }

    EventEntity eventEntity = new EventEntity();
    eventEntity.setEventContent(eventContent);
    eventEntity.setEventTitle(eventTitle);
    eventEntity.setVisibleDate(visibleDate);
    eventEntity.setInvisibleDate(invisibleDate);
    eventEntity.setPosterUser(userEntity);
    eventEntity.setMaxPeople(parsedMaxPeople);
    eventEntity.setIsRegistrationOpen('N');
    eventEntity.setEventAccept(1);
    eventEntity.setEventPoster(savedFileName);

    List<EventScheduleEntity> esEntities = new ArrayList<>();
    for (int i = 0; i < calcDate; i++) {
      LocalDate sDate = startEventDate.plusDays(i);

      EventScheduleEntity esEntity = new EventScheduleEntity();
      esEntity.setEvent(eventEntity);
      esEntity.setStartTime(startLocalTime);
      esEntity.setEndTime(endLocalTime);
      esEntity.setEventDate(sDate);

      esEntities.add(esEntity);
    }
    eventEntity.setScheduleList(esEntities);

    eventService.writeEvent(eventEntity);

    return ResponseEntity.ok(eventEntity);
  }
}
