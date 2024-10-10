package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.AttendInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendInfoRepository extends JpaRepository<AttendInfoEntity, Long> {
//    // 유저 ID로 참석 정보 가져오기 (event controller 에서 사용)
//    List<AttendInfoEntity> findByUser_UserId(Long userId);
}