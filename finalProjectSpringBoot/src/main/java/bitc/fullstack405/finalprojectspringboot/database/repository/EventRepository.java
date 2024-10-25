package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
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

    // <APP> 승인 완료(2), 행사 시작일 2주 전(visible_date) <= 오늘 날짜
    @Query("SELECT e FROM EventEntity e " +
            "WHERE e.eventAccept = 2 " +
            "AND e.visibleDate <= CURRENT_DATE " +
            "ORDER BY e.eventId DESC")
    List<EventEntity> findAcceptedEventsWithCapacity();

    // <APP> 행사 신청 시 최대 인원과 같은지 확인
    @Query("SELECT CASE " +
            "WHEN e.maxPeople = 0 THEN false " +  // maxPeople 이 0일 때는 제한이 없으므로 false 반환
            "WHEN COUNT(ea) + 1 > e.maxPeople THEN true " +  // 제한된 인원 초과 시 true 반환
            "ELSE false END " +
            "FROM EventAppEntity ea " +
            "JOIN ea.event e " +
            "WHERE e.eventId = :eventId")
    boolean checkMaxPeople(@Param("eventId") Long eventId);

    // 행사 참여 인원이 최대 인원과 같은지 확인
    @Query("SELECT CASE " +
            "WHEN e.maxPeople = 0 THEN false " +  // maxPeople 이 0일 때는 제한이 없으므로 false 반환
            "WHEN COUNT(ea) = e.maxPeople THEN true " +
            "ELSE false END " +
            "FROM EventAppEntity ea " +
            "JOIN ea.event e " +
            "WHERE e.eventId = :eventId")
    boolean listCheckMaxPeople(@Param("eventId") Long eventId);

    ///////////////////////////
    ////////// <WEB> //////////
    ///////////////////////////

    // eventId 기준 내림차순으로 모든 공지사항을 조회
    List<EventEntity> findAllByOrderByEventIdDesc();

    @Query("SELECT e FROM EventEntity e WHERE e.invisibleDate <= :date")
    List<EventEntity> findByInvisibleDateBeforeOrEqual(@Param("date") LocalDate date); // 스케줄러용. 매일자정에 마감여부 확인
}