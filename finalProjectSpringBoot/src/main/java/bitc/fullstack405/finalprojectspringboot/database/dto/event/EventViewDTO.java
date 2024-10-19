package bitc.fullstack405.finalprojectspringboot.database.dto.event;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventScheduleEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class EventViewDTO {
  private Long eventId;
  private String eventTitle;
  private String eventContent;
  private String eventPoster;
  private int eventAccept;
  private int maxPeople;
  private Character isRegistrationOpen;
  private LocalDate acceptedDate;
  private LocalDate visibleDate;
  private LocalDate invisibleDate;
  private LocalDateTime uploadDate;

  private List<EventScheduleViewDTO> eventSchedule;

  private ViewUserDTO posterUser;
  private ViewUserDTO approver;
}
