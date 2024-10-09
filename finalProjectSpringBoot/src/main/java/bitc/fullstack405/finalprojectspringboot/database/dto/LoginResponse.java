package bitc.fullstack405.finalprojectspringboot.database.dto;

import bitc.fullstack405.finalprojectspringboot.database.entity.Role;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import lombok.Getter;

@Getter
public class LoginResponse {
  Long userId;
  String userAccount;
  String password;
  String name;
  String userPhone;
  String userDepart;
  Role role;

  public LoginResponse(UserEntity user) {
    this.userId = user.getUserId();
    this.userAccount = user.getUserAccount();
    this.password = user.getPassword();
    this.name = user.getName();
    this.userPhone = user.getUserPhone();
    this.userDepart = user.getUserDepart();
    this.role = user.getRole();
  }
}
