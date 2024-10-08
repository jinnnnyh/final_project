package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository  extends JpaRepository<EventEntity, Long> {

    // <APP>

    // 이벤트 신청 인원 < 최대 인원
    // 행사 시작일 2주 전 <= 현재 날짜 <= 행사 시작일 1주 전
    @Query("SELECT e FROM EventEntity e " +
            "LEFT JOIN e.eventAppList app " +
            "WHERE e.visibleDate <= CURRENT_DATE " +
            "AND e.invisibleDate > CURRENT_DATE " +
            "AND e.eventAccept = 2 " +
            "GROUP BY e.eventId " +
            "HAVING COUNT(app.appId) < e.maxPeople")
    List<EventEntity> findAcceptedEventsWithCapacity();

    // eventId 기준 내림차순으로 모든 공지사항을 조회
    List<EventEntity> findAllByOrderByEventIdDesc();

    // 현재 날짜가 visibleDate와 같거나 더 큰 행사 목록을 최신 순으로 가져오기
    List<EventEntity> findByVisibleDateLessThanEqualOrderByEventIdDesc(LocalDate now);
}