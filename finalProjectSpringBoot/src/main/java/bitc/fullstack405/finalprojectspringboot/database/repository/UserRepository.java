package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    ///////////////////////////
    ////////// <APP> //////////
    ///////////////////////////

    // <APP> 회원 아이디(userAccount)로 회원 조회
    UserEntity findByUserAccount(String userAccount);

    UserEntity findUserByUserAccount(String userAccount);

    UserEntity findByUserId(Long userId);


    ///////////////////////////
    ////////// <WEB> //////////
    ///////////////////////////

  UserEntity findByUserAccountAndPassword(String userAccount, String userPw);

  @Query("SELECT u FROM UserEntity u " +
      "WHERE u.role IN (bitc.fullstack405.finalprojectspringboot.database.entity.Role.ROLE_PRESIDENT, " +
      "bitc.fullstack405.finalprojectspringboot.database.entity.Role.ROLE_SECRETARY, " +
      "bitc.fullstack405.finalprojectspringboot.database.entity.Role.ROLE_ASSOCIATE, " +
      "bitc.fullstack405.finalprojectspringboot.database.entity.Role.ROLE_REGULAR, " +
      "bitc.fullstack405.finalprojectspringboot.database.entity.Role.ROLE_DELETE) " +
      "ORDER BY CASE " +
      "WHEN u.role = bitc.fullstack405.finalprojectspringboot.database.entity.Role.ROLE_PRESIDENT THEN 1 " +
      "WHEN u.role = bitc.fullstack405.finalprojectspringboot.database.entity.Role.ROLE_SECRETARY THEN 2 " +
      "WHEN u.role = bitc.fullstack405.finalprojectspringboot.database.entity.Role.ROLE_ASSOCIATE THEN 3 " +
      "WHEN u.role = bitc.fullstack405.finalprojectspringboot.database.entity.Role.ROLE_REGULAR THEN 4 " +
      "WHEN u.role = bitc.fullstack405.finalprojectspringboot.database.entity.Role.ROLE_DELETE THEN 5 " +
      "END")
  List<UserEntity> findUsersForManage();
}
