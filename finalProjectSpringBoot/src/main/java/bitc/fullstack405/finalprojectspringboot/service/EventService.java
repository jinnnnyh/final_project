package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.dto.event.AddEventRequest;
import bitc.fullstack405.finalprojectspringboot.database.dto.event.UpdateEventRequest;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import bitc.fullstack405.finalprojectspringboot.utils.FileUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final FileUtils fileUtils;

    // 행사 글 등록
    public EventEntity save(AddEventRequest request, MultipartFile uploadFile) throws Exception {

//        // 현재 인증된 사용자 정보 가져오기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserEntity user = (UserEntity) authentication.getPrincipal();  // 인증된 UserEntity 가져오기

        // 스프링 시큐리티 합치면 삭제할 부분(userId가 1인 사람이 무조건 등록)
        UserEntity user = userRepository.findById((long)1).orElseThrow(() -> new IllegalArgumentException("not found id "));

        String eventPoster = null;

        if (uploadFile != null && !uploadFile.isEmpty()) {
            eventPoster = fileUtils.parseFileInfo(uploadFile);
        }

        EventEntity event = request.toEntity(user)
                .toBuilder()
                .eventPoster(eventPoster)
                .build();

        return eventRepository.save(event);
    }

    // 전체 목록 가져오기
    public List<EventEntity> findAllSortedByEventIdDesc() {
        return eventRepository.findAllByOrderByEventIdDesc();
    }

    // 지정한 행사 글 상세 내용 가져오기
    public EventEntity findById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    // 행사 글 삭제
    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    // 행사 글 수정
    @Transactional
    public EventEntity update(Long id, UpdateEventRequest request, MultipartFile uploadFile) throws Exception {
        EventEntity event = eventRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        String eventPoster = null;

        if (uploadFile != null && !uploadFile.isEmpty()) {
            eventPoster = fileUtils.parseFileInfo(uploadFile);
        }

        event.updateEvent(request.getEventTitle(), request.getEventContent(), request.getEventDate(), eventPoster);

        return event;
    }

    // 행사 승인 처리 (N -> Y)
    public EventEntity acceptEvent(Long eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id " + eventId));

        // 승인 상태 변경
        event.acceptEvent();

        return eventRepository.save(event); // 변경된 이벤트 저장
    }

    // 행사 승인 취소 처리 (Y -> N)
    public EventEntity rejectEvent(Long eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id " + eventId));

        // 승인 상태 변경
        event.rejectEvent();

        return eventRepository.save(event); // 변경된 이벤트 저장
    }
}
