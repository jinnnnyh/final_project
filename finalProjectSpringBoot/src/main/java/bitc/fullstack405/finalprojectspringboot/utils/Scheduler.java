package bitc.fullstack405.finalprojectspringboot.utils;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Scheduler {
  private final EventRepository eventRepository;

  @Scheduled(cron = "0 0 0 * * *")
  @Transactional
  public void checkDateRegistrationClose() {
    LocalDate today = LocalDate.now();

    List<EventEntity> eventEntities = eventRepository.findByInvisibleDateBeforeOrEqual(today);

    List<EventEntity> updatedEvents = eventEntities.stream()
        .map(eventEntity -> eventEntity.toBuilder()
            .isRegistrationOpen('N')
            .eventTitle(eventEntity.getEventTitle())
            .eventContent(eventEntity.getEventContent())
            .maxPeople(eventEntity.getMaxPeople())
            .posterUser(eventEntity.getPosterUser())
            .eventAccept(eventEntity.getEventAccept())
            .uploadDate(eventEntity.getUploadDate())
            .acceptedDate(eventEntity.getAcceptedDate())
            .approver(eventEntity.getApprover())
            .eventAppList(eventEntity.getEventAppList())
            .scheduleList(eventEntity.getScheduleList())
            .eventPoster(eventEntity.getEventPoster())
            .visibleDate(eventEntity.getVisibleDate())
            .invisibleDate(eventEntity.getInvisibleDate())
            .build())
        .collect(Collectors.toList());

    eventRepository.saveAll(updatedEvents);
  }
}
