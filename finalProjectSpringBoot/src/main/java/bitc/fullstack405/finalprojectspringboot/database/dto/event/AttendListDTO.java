package bitc.fullstack405.finalprojectspringboot.database.dto.event;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventScheduleEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
  private int eventAccept;
  private LocalDate acceptedDate;
  private LocalDateTime uploadDate;

  private ViewUserDTO uploader;
  private ViewUserDTO approver;

  private List<EventAppDTO> attendUserList;
  private List<EventScheduleDTO> eventScheduleDTOList;
}
