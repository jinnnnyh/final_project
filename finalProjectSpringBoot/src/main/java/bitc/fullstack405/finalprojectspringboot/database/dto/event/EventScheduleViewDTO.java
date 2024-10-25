package bitc.fullstack405.finalprojectspringboot.database.dto.event;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
public class EventScheduleViewDTO {
  private Long scheduleId;
  private LocalTime startTime;
  private LocalTime endTime;
  private LocalDate eventDate;
}
