package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.AttendInfoEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository  extends JpaRepository<EventEntity, Long> {

    // eventId 기준 내림차순으로 모든 공지사항을 조회
    List<EventEntity> findAllByOrderByEventIdDesc();

    // 현재 날짜가 visibleDate와 같거나 더 큰 행사 목록을 최신 순으로 가져오기
    List<EventEntity> findByVisibleDateLessThanEqualOrderByEventIdDesc(LocalDate now);
}