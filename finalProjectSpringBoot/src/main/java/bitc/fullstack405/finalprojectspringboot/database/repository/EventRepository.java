package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

}