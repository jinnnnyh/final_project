package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
