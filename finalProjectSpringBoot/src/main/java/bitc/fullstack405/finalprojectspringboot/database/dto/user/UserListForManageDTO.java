package bitc.fullstack405.finalprojectspringboot.database.dto.user;

import bitc.fullstack405.finalprojectspringboot.database.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserListForManageDTO {
  private Long userId;
  private String userAccount;
  private String name;
  private String userPhone;
  private String userDepart;
  private Role role;
}
