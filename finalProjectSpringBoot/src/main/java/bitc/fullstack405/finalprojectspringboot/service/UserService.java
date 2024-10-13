package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserEntity login(String userAccount, String userPw) {
    return userRepository.findByUserAccountAndPassword(userAccount, userPw);
  }

  public void signup(UserEntity userEntity) {
    userRepository.save(userEntity);
  }

  public List<UserEntity> userListForManage() {
    return userRepository.findAll();
  }
}
