package bitc.fullstack405.finalprojectspringboot.controller.react;

import bitc.fullstack405.finalprojectspringboot.database.entity.Role;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

//  Web 로그인
  @PostMapping("/login")
  public ResponseEntity<UserEntity> login(@RequestBody Map<String, String> loginData) {
    String userAccount = loginData.get("userAccount");
    String userPw = loginData.get("userPw");

    UserEntity userEntity = userService.login(userAccount, userPw);

    if (userEntity != null) {
      return ResponseEntity.ok(userEntity);
    }
    else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

//  Web 데이터베이스 추가용 회원가입
  @PutMapping("/signup")
  public ResponseEntity<UserEntity> signup(@RequestBody Map<String, String> signupData) {
    String userAccount = signupData.get("userAccount");
    String userPw = signupData.get("userPw");
    String name = signupData.get("name");
    String userDepart = signupData.get("userDepart");
    String userPermission = signupData.get("userPermission");
    String userPhone = signupData.get("userPhone");

    UserEntity userEntity = new UserEntity();

    if (Objects.equals(userPermission, "협회장")) {
      userEntity.setRole(Role.ROLE_PRESIDENT);
    }
    else if (Objects.equals(userPermission, "총무")) {
      userEntity.setRole(Role.ROLE_SECRETARY);
    }
    else if (Objects.equals(userPermission, "정회원")) {
      userEntity.setRole(Role.ROLE_REGULAR);
    }
    else if (Objects.equals(userPermission, "준회원")) {
      userEntity.setRole(Role.ROLE_ASSOCIATE);
    }

    userEntity.setUserAccount(userAccount);
    userEntity.setName(name);
    userEntity.setUserDepart(userDepart);
    userEntity.setUserPhone(userPhone);
    userEntity.setPassword(userPw);

    userService.signup(userEntity);

    return ResponseEntity.ok(userEntity);
  }
}
