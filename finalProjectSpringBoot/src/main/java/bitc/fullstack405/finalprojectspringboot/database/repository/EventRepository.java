package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository  extends JpaRepository<EventEntity, Long> {

    // eventId 기준 내림차순으로 모든 공지사항을 조회
    List<EventEntity> findAllByOrderByEventIdDesc();
}
