package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository  extends JpaRepository<EventEntity, Long> {

    ///////////////////////////
    ////////// <APP> //////////
    ///////////////////////////

    // <APP>
    // 승인 완료(2)
    @Query("SELECT e FROM EventEntity e " +
            "WHERE e.eventAccept = 2 " +
            "ORDER BY e.eventId DESC")
    List<EventEntity> findAcceptedEventsWithCapacity();


    ///////////////////////////
    ////////// <WEB> //////////
    ///////////////////////////

    // eventId 기준 내림차순으로 모든 공지사항을 조회
    List<EventEntity> findAllByOrderByEventIdDesc();

    // 현재 날짜가 visibleDate와 같거나 더 큰 행사 목록을 최신 순으로 가져오기
    List<EventEntity> findByVisibleDateLessThanEqualOrderByEventIdDesc(LocalDate now);
}