package bitc.fullstack405.finalprojectspringboot.controller.react;

import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

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
}
