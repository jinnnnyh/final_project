package bitc.fullstack405.finalprojectspringboot.controller;

import bitc.fullstack405.finalprojectspringboot.database.dto.EventAppInsertRequest;
import bitc.fullstack405.finalprojectspringboot.database.dto.LoginResponse;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventAppEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.TempEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.service.TempService;
import bitc.fullstack405.finalprojectspringboot.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TempController {

  private final UserService userService;

//  @GetMapping("/temp")
//  public List<TempEntity> temp() {
//    System.out.println("/temp 접속");
//    return tempService.findAll();
//  }

  // 회원정보 찾기
  @PostMapping("/login")
  public ResponseEntity<LoginResponse> findLoginUser(@RequestBody UserEntity user){
    LoginResponse res = userService.findLoginUser(user.getUserAccount(),user.getPassword());
    return ResponseEntity.ok().body(res);
  }

  // 행사 신청자 추가
  // 같은 이벤트id 추가 안되게 하기
//  @PostMapping("/app/insert")
//  public ResponseEntity<EventAppEntity> insertAppUser(@RequestBody EventAppInsertRequest req){
//    EventAppEntity insertApp = userService.insertApp(req);
//    return ResponseEntity.ok().body(insertApp);
//  }


}
