package bitc.fullstack405.finalprojectspringboot.database.dto.app.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAppUserRequest {
  private String password;
  private String userPhone;
}
