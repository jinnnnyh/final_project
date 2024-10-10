package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventScheduleRepository extends JpaRepository<EventScheduleEntity, Long> {

    ///////////////////////////
    ////////// <APP> //////////
    ///////////////////////////

    // 행사 스케줄 정보 조회
    List<EventScheduleEntity> findByEvent(EventEntity event);

    // 행사일자, QR 이미지 조회
    @Query("SELECT e.scheduleId, e.eventDate, a.qrImage FROM EventScheduleEntity e JOIN AttendInfoEntity a ON e.scheduleId = a.eventSchedule.scheduleId WHERE e.event.eventId = :eventId AND a.user.userId = :userId ORDER BY e.scheduleId ASC")
    List<Object[]> findQrImagesByEventIdAndUserId(@Param("eventId") Long eventId, @Param("userId") Long userId);

    ///////////////////////////
    ////////// <WEB> //////////
    ///////////////////////////
}
