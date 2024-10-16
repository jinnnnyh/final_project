package bitc.fullstack405.finalprojectspringboot.database.dto.event;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventScheduleEntity;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class AttendListDTO {
  private String eventTitle;
  private LocalDate startDate;
  private LocalDate endDate;
  private LocalTime startTime;
  private LocalTime endTime;
  private int maxPeople;
  private List<EventAppDTO> attendUserList;
  private List<EventScheduleDTO> eventScheduleDTOList;
}
