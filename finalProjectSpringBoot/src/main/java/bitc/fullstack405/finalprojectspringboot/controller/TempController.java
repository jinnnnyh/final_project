package bitc.fullstack405.finalprojectspringboot.controller;

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
  public UserEntity findLoginUser(@RequestBody UserEntity user) {
    String userAccount = user.getUserAccount();
    String password = user.getPassword();
    return userService.findLoginUser(userAccount, password);
  }

}
