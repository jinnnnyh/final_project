package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  public UserEntity findLoginUser(String userAccount, String password) {
    UserEntity user = userRepository.findUserByUserAccount(userAccount);// 아이디가 존재하는 user 정보 가져옴
    if (user != null) {
      if(user.getPassword().equals(password)) {
        return user;
      }
      else{
        return null;
      }
    } else{
      return null;
    }

//    if(user == null) {
//      return null;
//    }
//    else if(password.equals(user.getPassword())) {
//      return user;
//    }
//    else{
//      return null;
//    }
  }
}
