package bitc.fullstack405.finalprojectspringboot.utils;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {
  private final EventRepository eventRepository;

  @Scheduled(cron = "0 0 0 * * *")
  @Transactional
  public void checkDateRegistrationClose() {
    LocalDate today = LocalDate.now();

    List<EventEntity> eventEntities = eventRepository.findByInvisibleDate(today);

    eventEntities.replaceAll(eventEntity -> eventEntity.toBuilder()
        .isRegistrationOpen('Y')
        .build());
    eventRepository.saveAll(eventEntities);
  }
}
