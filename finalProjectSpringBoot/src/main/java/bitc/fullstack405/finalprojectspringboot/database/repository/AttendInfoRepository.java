package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.AttendInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AttendInfoRepository extends JpaRepository<AttendInfoEntity, Long> {

    ///////////////////////////
    ////////// <APP> //////////
    ///////////////////////////

    // <APP> QR 스캔 시 QR의 데이터에 해당하는 데이터가 DB에 있는지 확인
    @Query("SELECT COUNT(a) > 0 FROM AttendInfoEntity a " +
            "WHERE a.eventSchedule.scheduleId = :scheduleId " +
            "AND a.user.userId = :userId " +
            "AND a.eventSchedule.eventDate = CURRENT_DATE")
    boolean isEventValid(@Param("scheduleId") Long scheduleId, @Param("userId") Long userId);

    // <APP> 이미 사용한 QR 코드인지 확인
    boolean existsByEventSchedule_ScheduleIdAndUser_UserIdAndCheckInTimeIsNotNullAndCheckOutTimeIsNotNull(Long scheduleId, Long userId);

    // <APP> check_in_time 이 null 인지 확인
    boolean existsByEventSchedule_ScheduleIdAndUser_UserIdAndCheckInTimeIsNull(Long scheduleId, Long userId);

    // <APP> scheduleId, userId로 attendInfoEntity 조회
    Optional<AttendInfoEntity> findByEventSchedule_ScheduleIdAndUser_UserId(Long scheduleId, Long userId);

    // <APP> 회차별 attendComp 가 모두 Y인지 확인
    @Query("SELECT CASE WHEN COUNT(a) = 0 THEN true ELSE false END " +
            "FROM EventScheduleEntity e " +
            "INNER JOIN AttendInfoEntity a " +
            "ON e.scheduleId = a.eventSchedule.scheduleId " +
            "WHERE e.event.eventId = :eventId " +
            "AND a.user.userId = :userId " +
            "AND a.attendComp = 'N'")
    boolean isAllCompleted(@Param("eventId") Long eventId, @Param("userId") Long userId);


    ///////////////////////////
    ////////// <WEB> //////////
    ///////////////////////////
}