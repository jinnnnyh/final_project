package bitc.fullstack405.finalprojectspringboot.database.dto.event;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ViewUserDTO {
  private Long userId;
  private String name;
}
