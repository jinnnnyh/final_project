package bitc.fullstack405.finalprojectspringboot.controller.react;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import bitc.fullstack405.finalprojectspringboot.service.EventService;
import bitc.fullstack405.finalprojectspringboot.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequestMapping("/event")
@RestController
@RequiredArgsConstructor
public class EventController {

  private final EventService eventService;
  private final UserRepository userRepository;
  private final FileUtils fileUtils;

//  행사 등록
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
      @RequestParam(value = "file", required = false) MultipartFile file
  ) throws Exception {

    LocalDate startEventDate = LocalDate.parse(eventStartDate);
    LocalDate endEventDate = LocalDate.parse(eventEndDate);
    LocalTime startEventTime = LocalTime.parse(startTime);
    LocalTime endEventTime = LocalTime.parse(endTime);

    Long parsedUserId = Long.parseLong(userId);
    UserEntity userEntity = userRepository.findById(parsedUserId).orElse(null);

    int parsedMaxPeople = Integer.parseInt(maxPeople);

    return ResponseEntity.ok(eventService.writeEvent(
        eventContent,
        eventTitle,
        startEventDate,
        endEventDate,
        startEventTime,
        endEventTime,
        userEntity,
        parsedMaxPeople,
        file
    ));
  }

//  이벤트 상세보기
  @GetMapping("/{eventId}")
  public ResponseEntity<EventEntity> eventView(@PathVariable Long eventId) {
    Optional<EventEntity> eventEntity = eventService.eventView(eventId);

    EventEntity event = eventEntity.orElse(new EventEntity());

    return ResponseEntity.ok(event);
  }
}
