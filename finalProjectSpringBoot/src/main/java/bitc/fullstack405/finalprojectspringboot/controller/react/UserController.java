package bitc.fullstack405.finalprojectspringboot.controller.react;

import bitc.fullstack405.finalprojectspringboot.database.entity.Role;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

//  유저 관리 리스트 출력
  @GetMapping("/userManage")
//  public ResponseEntity<List<UserEntity>> userManage() {
  public String userManage() {

    return "성공";
  }

//  가입대기 유저 승인
  @PutMapping("/signAccept/{userId}")
//  public String signAccept(@RequestBody Map<String, String> loginData) {
  public String signAccept(@PathVariable Long userId) {

    return "성공";
  }

//  회원삭제(관리자권한)
  @DeleteMapping("/signOut/{userId}")
//  public ResponseEntity<UserEntity> signOut(@RequestBody UserEntity userEntity) {
  public String signOut(@PathVariable Long userId) {

    return "성공";
  }

//  회원정보 수정 승인
  @PutMapping("/updateUser/{userId}")
//  public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity userEntity) {
  public String updateUser(@PathVariable Long userId) {

    return "성공";
  }



//  Web 데이터베이스 추가용 회원가입
  @PutMapping("/signup")
  public ResponseEntity<UserEntity> signup(@RequestBody Map<String, String> signupData) {
    String userPermission = signupData.get("userPermission");
    Role userPerm = null;

    if (Objects.equals(userPermission, "협회장")) {
      userPerm = Role.ROLE_PRESIDENT;
    }
    else if (Objects.equals(userPermission, "총무")) {
      userPerm = Role.ROLE_SECRETARY;
    }
    else if (Objects.equals(userPermission, "정회원")) {
      userPerm = Role.ROLE_REGULAR;
    }
    else if (Objects.equals(userPermission, "준회원")) {
      userPerm = Role.ROLE_ASSOCIATE;
    }
    UserEntity userEntity = UserEntity.builder()
        .userAccount(signupData.get("userAccount"))
        .userDepart(signupData.get("userDepart"))
        .userPhone(signupData.get("userPhone"))
        .password(signupData.get("userPw"))
        .name(signupData.get("name"))
        .role(userPerm)
        .build();


    userService.signup(userEntity);

    return ResponseEntity.ok(userEntity);
  }
}
