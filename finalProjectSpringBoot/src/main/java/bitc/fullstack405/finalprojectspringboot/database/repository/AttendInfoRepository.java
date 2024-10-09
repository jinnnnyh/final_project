package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.AttendInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendInfoRepository extends JpaRepository<AttendInfoEntity, Long> {
//    // 유저 ID로 참석 정보 가져오기 (event controller 에서 사용)
//    List<AttendInfoEntity> findByUser_UserId(Long userId);

    // eventId와 userId로 attend_info 테이블에서 QR 이미지 조회
    @Query("SELECT a.qrImage FROM AttendInfoEntity a WHERE a.user.userId = :userId AND a.eventSchedule.event.eventId = :eventId")
    List<String> findQrImagesByEventIdAndUserId(@Param("eventId") Long eventId, @Param("userId") Long userId);

}
