package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
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

    @Query("SELECT e FROM EventEntity e WHERE e.invisibleDate <= :date")
    List<EventEntity> findByInvisibleDateBeforeOrEqual(@Param("date") LocalDate date); // 스케줄러용. 매일자정에 마감여부 확인
}