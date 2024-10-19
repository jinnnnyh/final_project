package bitc.fullstack405.finalprojectspringboot.database.dto.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AttendUserDTO {
  private Long userId;
  private String userAccount;
  private String name;
  private String userPhone;
  private String userDepart;
  private String role;
}