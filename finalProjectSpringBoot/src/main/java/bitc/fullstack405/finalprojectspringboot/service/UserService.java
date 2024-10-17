package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.user.CheckedIdResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.user.InsertUserRequest;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.user.LoginResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.user.UpdateAppUserRequest;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventAppRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static bitc.fullstack405.finalprojectspringboot.database.entity.Role.ROLE_DELETE;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final EventAppRepository eventAppRepository;
  private final EventRepository eventRepository;

  ///////////////////////////
  ////////// <APP> //////////
  ///////////////////////////

  public LoginResponse findLoginUser(String userAccount, String password) {
    UserEntity user = userRepository.findUserByUserAccount(userAccount);// 아이디가 존재하는 user 정보 가져옴
    if(user!=null && user.getPassword().equals(password)) {
      return new LoginResponse(user);
    }

    return null;
  }

  public UserEntity save(InsertUserRequest req) {
    return userRepository.save(req.toUserEntity());
  }

  public CheckedIdResponse findUserAccount(String userAccount) {
    UserEntity user = userRepository.findUserByUserAccount(userAccount);
    return new CheckedIdResponse(Objects.requireNonNull(user));
  }

  public LoginResponse findByUserId(Long userId) {
    UserEntity user = userRepository.findByUserId(userId);
    return new LoginResponse(user);
  }

  @Transactional
  public void updateAppUser(Long userId, UpdateAppUserRequest req) {
    UserEntity user = userRepository.findByUserId(userId);
    user.updateAppUser(req.getPassword(), req.getUserPhone(), req.getUserDepart());
  }

  @Transactional
  public void deleteAppUser(Long userId) {
    UserEntity user = userRepository.findByUserId(userId);
    user.deleteAppUser();
  }


  ///////////////////////////
  ////////// <WEB> //////////
  ///////////////////////////

}
