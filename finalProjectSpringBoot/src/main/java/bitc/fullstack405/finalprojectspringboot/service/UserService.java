package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.entity.Role;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

//  로그인
  public UserEntity login(String userAccount, String userPw) {
    return userRepository.findByUserAccountAndPassword(userAccount, userPw);
  }

//  회원가입(테스터용)
  public void signup(UserEntity userEntity) {
    userRepository.save(userEntity);
  }

//  유저목록출력
  public List<UserEntity> userListForManage() {
    return userRepository.findAll();
  }


//  회원가입승인
  @Transactional
  public void signAccept(Long userId) {
    UserEntity userEntity = userRepository.findById(userId).orElse(null);

    UserEntity updateUser = userEntity.toBuilder()
        .role(Role.ROLE_REGULAR)
        .userAccount(userEntity.getUserAccount())
        .name(userEntity.getName())
        .userDepart(userEntity.getUserDepart())
        .userPhone(userEntity.getUserPhone())
        .approvedEventList(userEntity.getApprovedEventList())
        .password(userEntity.getPassword())
        .attendAppList(userEntity.getAttendAppList())
        .postedEventList(userEntity.getPostedEventList())
        .attendInfoList(userEntity.getAttendInfoList())
        .build();

    userRepository.save(updateUser);
  }
}
