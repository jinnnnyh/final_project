package bitc.fullstack405.finalprojectspringboot.database.dto.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EventUpdateDTO {
  private Long eventId;
  private Long approverId;
  private LocalDate acceptedDate;
  private int eventAccept;
}