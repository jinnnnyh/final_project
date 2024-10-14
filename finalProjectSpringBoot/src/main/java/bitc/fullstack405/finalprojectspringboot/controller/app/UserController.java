package bitc.fullstack405.finalprojectspringboot.controller.app;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.user.InsertUserRequest;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.user.LoginResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.user.UpdateAppUserRequest;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

//  @GetMapping("/temp")
//  public List<TempEntity> temp() {
//    System.out.println("/temp 접속");
//    return tempService.findAll();
//  }

  // 로그인 회원정보 찾기
  @PostMapping("/login")
  public ResponseEntity<LoginResponse> findLoginUser(@RequestBody UserEntity user){
    LoginResponse res = userService.findLoginUser(user.getUserAccount(),user.getPassword());
    return ResponseEntity.ok().body(res);
  }

  // 회원가입
  @PostMapping("/signup")
  public ResponseEntity<UserEntity> insertUser(@RequestBody InsertUserRequest req){
    System.out.println(req);
    UserEntity save = userService.save(req);

    return ResponseEntity.ok().body(save);
  }

  // 아이디 중복확인
  @PostMapping("/signup/{userAccount}")
  public ResponseEntity<Boolean> checkUserAccount(@PathVariable("userAccount") String userAccount){
    UserEntity user = userService.findUserAccount(userAccount);

    if(user == null){ // 같은 계정이 없으면 false
      return ResponseEntity.ok(false);
    }
    return ResponseEntity.ok(true); // 있으면 true
  }

  // 회원 1명 정보
  @PostMapping("/app/user/{userId}")
  public ResponseEntity<LoginResponse> findByUserId(@PathVariable("userId") Long userId){
    LoginResponse user = userService.findByUserId(userId);
    return ResponseEntity.ok(user);
  }

  // 회원 정보 업데이트
  @PutMapping("/app/user/{userId}")
  public ResponseEntity<Void> updateAppUser(@PathVariable("userId") Long userId, @RequestBody UpdateAppUserRequest req){
    userService.updateAppUser(userId, req);

    return ResponseEntity.ok().build();
  }

  // 회원 탈퇴
  @PutMapping("/app/user/delete/{userId}")
  public ResponseEntity<Void> deleteAppUser(@PathVariable("userId") Long userId){
    userService.deleteAppUser(userId);

    return ResponseEntity.ok().build();
  }
}
