package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.AttendInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AttendInfoRepository extends JpaRepository<AttendInfoEntity, Long> {
//    // 유저 ID로 참석 정보 가져오기 (event controller 에서 사용)
//    List<AttendInfoEntity> findByUser_UserId(Long userId);

    ///////////////////////////
    ////////// <APP> //////////
    ///////////////////////////

    Optional<AttendInfoEntity> findByEventSchedule_ScheduleIdAndUser_UserId(Long scheduleId, Long userId);

    @Query("SELECT COUNT(a) = COUNT(CASE WHEN a.attendComp = 'Y' THEN 1 END) " +
            "FROM AttendInfoEntity a WHERE a.user.userId = :userId AND a.eventSchedule.event.eventId = :eventId")
    boolean allSessionsCompleted(@Param("userId") Long userId, @Param("eventId") Long eventId);
}