package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventScheduleRepository extends JpaRepository<EventScheduleEntity, Long> {

    ///////////////////////////
    ////////// <APP> //////////
    ///////////////////////////

    // <APP> 행사 스케줄 정보 조회
    List<EventScheduleEntity> findByEvent(EventEntity event);

    // <APP> 행사일자, QR 이미지 조회
    @Query("SELECT e.scheduleId, e.eventDate, a.qrImage FROM EventScheduleEntity e JOIN AttendInfoEntity a ON e.scheduleId = a.eventSchedule.scheduleId WHERE e.event.eventId = :eventId AND a.user.userId = :userId ORDER BY e.scheduleId ASC")
    List<Object[]> findQrImagesByEventIdAndUserId(@Param("eventId") Long eventId, @Param("userId") Long userId);

    // <APP> eventId로 해당 행사에 속한 모든 스케줄 조회(schedule id 기준 내림차순)
    List<EventScheduleEntity> findByEvent_EventIdOrderByScheduleIdDesc(Long eventId);

    // <APP> 관리자 - 곧 시작하는 행사
    @Query("SELECT s FROM EventScheduleEntity s " +
            "JOIN s.event e " +
            "WHERE ((CURRENT_DATE = s.eventDate AND CURRENT_TIME <= s.endTime) OR CURRENT_DATE < s.eventDate) " +
            "ORDER BY " +
            "CASE " +
            "  WHEN CURRENT_DATE = s.eventDate AND CURRENT_TIME <= s.endTime THEN 0 " +
            "  WHEN CURRENT_DATE < s.eventDate THEN 1 " +
            "END ASC, " +  // CASE 결과에 따라 우선 정렬
            "CASE " +
            "  WHEN CURRENT_DATE = s.eventDate AND CURRENT_TIME <= s.endTime THEN s.startTime " +  // 0인 경우 start_time으로 정렬
            "  WHEN CURRENT_DATE < s.eventDate THEN s.scheduleId " +  // 1인 경우 scheduleId로 정렬
            "END ASC")
    List<EventScheduleEntity> findUpcomingEventSchedules();

    // <APP> 해당 행사의 첫 번째 회차 일정만 가져오기 (가장 첫 번째 회차)
    EventScheduleEntity findFirstByEvent_EventIdOrderByScheduleIdAsc(Long eventId);


    ///////////////////////////
    ////////// <WEB> //////////
    ///////////////////////////

    void deleteByEvent(EventEntity eventEntity);
}
