package bitc.fullstack405.finalprojectspringboot.database.dto.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EventUpdateDTO {
  private Long eventId;
  private String eventTitle;
  private String eventContent;
  private String eventStartDate;
  private String eventEndDate;
  private String startTime;
  private String endTime;
  private String maxPeople;
  private Long userId;
}