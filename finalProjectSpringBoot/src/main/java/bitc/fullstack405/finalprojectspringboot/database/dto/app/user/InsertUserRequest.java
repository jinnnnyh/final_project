package bitc.fullstack405.finalprojectspringboot.database.dto.app.user;

import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static bitc.fullstack405.finalprojectspringboot.database.entity.Role.ROLE_ASSOCIATE;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsertUserRequest {
  // 유저id, 아이디, 비밀번호, 이름, 전화번호, 소속, 권한
//  private Long userId; // 자동생성이라 받을 필요 없음
  private String name;
  private String userPhone;
//  private String role;
  private String userAccount;
  private String password;
  private String userDepart;

  // dto를 엔티티로 바꿔야햄
  public UserEntity toUserEntity() {
    return UserEntity.builder()
        .name(name)
        .userPhone(userPhone)
        .userAccount(userAccount)
        .password(password)
        .role(ROLE_ASSOCIATE)
        .userDepart(userDepart)
        .build();
  }

}
