package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventAppEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventAppRepository extends JpaRepository<EventAppEntity, Long> {
  EventAppEntity findByUser(UserEntity user);
  // eventId와 userId로 중복 신청 여부 확인
  boolean existsByEvent_EventIdAndUser_UserId(Long eventId, Long userId);

  // 특정 유저가 신청한 행사 조회
  List<EventAppEntity> findByUser_UserIdOrderByEvent_EventIdDesc(Long userId);

  // 특정 유저가 신청한 행사 조회, eventId로 내림차순 정렬 및 eventComp가 'N'인 경우만 필터링
  List<EventAppEntity> findByUser_UserIdAndEventCompOrderByEvent_EventIdDesc(Long userId, Character eventComp);
}
