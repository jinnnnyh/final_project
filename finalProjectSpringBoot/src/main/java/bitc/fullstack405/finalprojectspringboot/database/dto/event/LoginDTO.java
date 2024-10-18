package bitc.fullstack405.finalprojectspringboot.database.dto.event;

import bitc.fullstack405.finalprojectspringboot.database.entity.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginDTO {
  private String name;
  private String userAccount;
  private Long userId;
  private Role role;
  private String userPhone;
  private String userDepart;
}
