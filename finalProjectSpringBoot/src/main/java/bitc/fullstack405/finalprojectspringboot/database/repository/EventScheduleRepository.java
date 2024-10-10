package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventScheduleRepository extends JpaRepository<EventScheduleEntity, Long> {

    ///////////////////////////
    ////////// <APP> //////////
    ///////////////////////////

    List<EventScheduleEntity> findByEvent(EventEntity event);

    ///////////////////////////
    ////////// <WEB> //////////
    ///////////////////////////
}
