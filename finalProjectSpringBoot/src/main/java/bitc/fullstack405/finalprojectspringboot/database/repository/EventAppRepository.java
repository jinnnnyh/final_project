package bitc.fullstack405.finalprojectspringboot.database.repository;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventAppEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventAppRepository extends JpaRepository<EventAppEntity, Long> {
}
