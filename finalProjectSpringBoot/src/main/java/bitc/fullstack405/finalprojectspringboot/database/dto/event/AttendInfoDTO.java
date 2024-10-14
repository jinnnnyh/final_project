package bitc.fullstack405.finalprojectspringboot.database.dto.event;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
public class AttendInfoDTO {
  private Long attendId;
  private LocalDate attendDate;
  private LocalTime checkInTime;
  private LocalTime checkOutTime;
  private Character attendComp;
  private Long userId;
}
