package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.AppAdminUpcomingEventResponse;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventAppEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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


    ///////////////////////////
    ////////// <WEB> //////////
    ///////////////////////////
}
