package bitc.fullstack405.finalprojectspringboot.database.dto;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventAppEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventAppRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import bitc.fullstack405.finalprojectspringboot.service.EventService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 신청자 정보 엔티티로 변환해서 저장
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventAppInsertRequest {

  Long appId;
  char eventComp;
  Long eventId;
  Long userId;


  // DTO 클래스를 입력받은 데이터를 기준으로 Entity 클래스로 변환
  public EventAppEntity toEntity(EventRepository eventRepository, UserRepository userRepository) {

    EventEntity event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found"));
    UserEntity user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

    return EventAppEntity.builder()
        .appId(appId)
        .eventComp(eventComp)
        .event(event)
        .user(user)
        .build();
  }
}
