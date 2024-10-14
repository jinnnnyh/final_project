package bitc.fullstack405.finalprojectspringboot.database.dto.app.user;

import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import lombok.Getter;

@Getter
public class CheckedIdResponse {
  String userAccount;
  String name;
  String userPhone;

  public CheckedIdResponse(UserEntity user) {
    this.userAccount = user.getUserAccount();
    this.name = user.getName();
    this.userPhone = user.getUserPhone();
  }
}
