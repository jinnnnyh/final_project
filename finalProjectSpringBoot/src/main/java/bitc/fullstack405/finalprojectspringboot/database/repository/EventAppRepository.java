package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventAppEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventAppRepository extends JpaRepository<EventAppEntity, Long> {

    ///////////////////////////
    ////////// <APP> //////////
    ///////////////////////////

    // <APP> eventId와 userId로 중복 신청 여부 확인
    boolean existsByEvent_EventIdAndUser_UserId(Long eventId, Long userId);

    // <APP> 특정 유저가 신청한 행사 조회
    List<EventAppEntity> findByUser_UserIdOrderByEvent_EventIdDesc(Long userId);

    // <APP> 특정 유저가 신청한 행사 조회, eventId로 내림차순 정렬 및 eventComp가 'N'인 경우만 필터링
    List<EventAppEntity> findByUser_UserIdAndEventCompOrderByEvent_EventIdDesc(Long userId, Character eventComp);

    // <APP> QR 스캔 - eventId와 userId로 eventAppEntity 조회
    EventAppEntity findByEvent_EventIdAndUser_UserId(Long eventId, Long userId);

    // <APP> 회원 - 신청 내역 중 곧 시작하는 행사
    @Query("SELECT ea, e, s " +
            "FROM EventAppEntity ea " +
            "JOIN ea.event e " +
            "JOIN e.scheduleList s " +
            "WHERE ea.user.userId = :userId " +
            "AND ((CURRENT_DATE = s.eventDate AND CURRENT_TIME <= s.endTime) OR CURRENT_DATE < s.eventDate) " +
            "ORDER BY " +
            "CASE " +
            "  WHEN CURRENT_DATE = s.eventDate AND CURRENT_TIME <= s.endTime THEN 0 " +
            "  WHEN CURRENT_DATE < s.eventDate THEN 1 " +
            "END, s.scheduleId ASC")
    List<Object[]> findUpcomingEventForUser(Long userId);


    ///////////////////////////
    ////////// <WEB> //////////
    ///////////////////////////

    int countByEventAndEventComp(EventEntity eventEntity, char eventComp);

    List<EventAppEntity> findByEvent(EventEntity event);
}
