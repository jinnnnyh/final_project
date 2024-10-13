package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.dto.event.AttendInfoDTO;
import bitc.fullstack405.finalprojectspringboot.database.dto.event.AttendListDTO;
import bitc.fullstack405.finalprojectspringboot.database.dto.event.AttendUserDTO;
import bitc.fullstack405.finalprojectspringboot.database.dto.event.EventListDTO;
import bitc.fullstack405.finalprojectspringboot.database.entity.*;
import bitc.fullstack405.finalprojectspringboot.database.repository.*;
import bitc.fullstack405.finalprojectspringboot.utils.FileUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventService {

  private final EventRepository eventRepository;
  private final UserRepository userRepository;
  private final AttendInfoRepository attendInfoRepository;
  private final EventAppRepository eventAppRepository;
  private final EventScheduleRepository scheduleRepository;

  private final FileUtils fileUtils;
  private final EventScheduleRepository eventScheduleRepository;

  //    // 행사 글 등록
//    public EventEntity save(AddEventRequest request, MultipartFile uploadFile) throws Exception {
//
////        // 현재 인증된 사용자 정보 가져오기
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        UserEntity user = (UserEntity) authentication.getPrincipal();  // 인증된 UserEntity 가져오기
//
//        // 스프링 시큐리티 합치면 삭제할 부분(userId가 1인 사람이 무조건 등록)
//        UserEntity user = userRepository.findById((long)1).orElseThrow(() -> new IllegalArgumentException("not found id "));
//
//        String eventPoster = null;
//
//        if (uploadFile != null && !uploadFile.isEmpty()) {
//            eventPoster = fileUtils.parseFileInfo(uploadFile);
//        }
//
//        LocalDate visibleDate = request.getEventDate().minusWeeks(2);
//
//        EventEntity event = request.toEntity(user)
//                .toBuilder()
//                .eventPoster(eventPoster)
//                .visibleDate(visibleDate)
//                .build();
//
//        return eventRepository.save(event);
//    }
//
  // 행사 글 목록 - 모두 출력
  public List<EventEntity> findAllSortedByEventIdDesc() {
    return eventRepository.findAllByOrderByEventIdDesc();
  }
//
//    // 행사 글 목록 - 행사일 기준 2주 전부터 출력 - 다시 만들어야 함 종료일 이전거만 보여주려면
//    public List<EventEntity> findEventsFromVisibleDate() {
//        LocalDate now = LocalDate.now(); // 현재 날짜
//        LocalDate twoWeeksAgo = now.minusWeeks(2); // 현재 날짜로부터 2주 전
//
//        // 현재 날짜가 visibleDate와 같거나 더 큰 행사들만 가져오기
//        return eventRepository.findByVisibleDateLessThanEqualOrderByEventIdDesc(now);
//    }
//
//    // 지정한 행사 글 상세 내용 가져오기
//    public EventEntity findById(Long id) {
//        return eventRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found : " + id));
//    }
//
//    // 행사 글 삭제
//    public void delete(Long id) {
//        eventRepository.deleteById(id);
//    }
//
//    // 행사 글 수정
//    @Transactional
//    public EventEntity update(Long id, UpdateEventRequest request, MultipartFile uploadFile) throws Exception {
//        EventEntity event = eventRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found : " + id));
//
//        String eventPoster = null;
//
//        if (uploadFile != null && !uploadFile.isEmpty()) {
//            eventPoster = fileUtils.parseFileInfo(uploadFile);
//        }
//
//        event.updateEvent(request.getEventTitle(), request.getEventContent(), request.getEventDate(), eventPoster);
//
//        return event;
//    }
//
//    // 행사 승인 처리 (N -> Y, 승인 일자 추가, 승인자 추가)
//    public EventEntity acceptEvent(Long eventId, Long userId) {
//        EventEntity event = eventRepository.findById(eventId)
//                .orElseThrow(() -> new EntityNotFoundException("Event not found with id " + eventId));
//
//        UserEntity approver = userRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));
//
//        // 승인 상태 변경
//        event.acceptEvent(approver);
//
//        return eventRepository.save(event); // 변경된 이벤트 저장
//    }
//
//    // 행사 승인 취소 처리 (Y -> N, 승인 일자 null, 승인자 null)
//    public EventEntity rejectEvent(Long eventId) {
//        EventEntity event = eventRepository.findById(eventId)
//                .orElseThrow(() -> new EntityNotFoundException("Event not found with id " + eventId));
//
//        // 승인 상태 변경
//        event.rejectEvent();
//
//        return eventRepository.save(event); // 변경된 이벤트 저장
//    }
//
//    // 승인 여부 확인 (event_accept 값 - Y/N)
//    public Character approvalStatus(Long eventId) {
//        EventEntity event = eventRepository.findById(eventId)
//                .orElseThrow(() -> new EntityNotFoundException("Event not found with id " + eventId));
//
//        return event.getEventAccept();
//    }
//
//    // 자신이 신청한 행사 목록 보기
//    public List<EventResponse> findEventsByUserId(Long userId) {
//        // userId로 참가한 행사 정보 가져오기
//        List<AttendInfoEntity> attendInfoList = attendInfoRepository.findByUser_UserId(userId);
//
//        // 행사 목록을 가져오기
//        List<EventEntity> events = attendInfoList.stream()
//                .map(AttendInfoEntity::getEvent)
//                .collect(Collectors.toList());
//
//        // EventEntity를 EventResponse로 변환하여 반환
//        return events.stream()
//                .map(EventResponse::new)
//                .collect(Collectors.toList());
//    }


  ////////////////////////////////////////react web///////////////////////////////////////////


//  행사 등록
  @Transactional
  public EventEntity writeEvent(String eventContent,
                         String eventTitle,
                         LocalDate startEventDate,
                         LocalDate endEventDate,
                         LocalTime startEventTime,
                         LocalTime endEventTime,
                         UserEntity userEntity,
                         int parsedMaxPeople,
                         MultipartFile file
  ) throws Exception {

    String savedFileName = null;
    if (file != null && !file.isEmpty()) {
      savedFileName = fileUtils.parseFileInfo(file);
    }

      int calcDate = (int) (ChronoUnit.DAYS.between(startEventDate, endEventDate) + 1);
      LocalDate invisibleDate = startEventDate.minusWeeks(1);
      LocalDate visibleDate = startEventDate.minusWeeks(2);

    EventEntity eventEntity = EventEntity.builder()
        .eventContent(eventContent)
        .eventTitle(eventTitle)
        .visibleDate(visibleDate)
        .invisibleDate(invisibleDate)
        .posterUser(userEntity)
        .maxPeople(parsedMaxPeople)
        .isRegistrationOpen('Y')
        .eventAccept(1)
        .eventPoster(savedFileName)
        .build();

    EventEntity savedEvent = eventRepository.save(eventEntity);

      List<EventScheduleEntity> esEntities = new ArrayList<>();
      for (int i = 0; i < calcDate; i++) {
        LocalDate sDate = startEventDate.plusDays(i);

        EventScheduleEntity esEntity = EventScheduleEntity.builder()
            .event(savedEvent)
            .startTime(startEventTime)
            .endTime(endEventTime)
            .eventDate(sDate)
            .build();

        esEntities.add(esEntity);
      }

      eventScheduleRepository.saveAll(esEntities);

      return eventEntity;
  }

//  이벤트 상세보기
  public Optional<EventEntity> eventView(Long eventId) {
    return eventRepository.findById(eventId);
  }

//  이벤트 리스트 전체 출력
  public List<EventListDTO> getEventList() {
    List<EventEntity> events = eventRepository.findAll();
    List<EventListDTO> eventListDTO = new ArrayList<>();

    for (EventEntity event : events) {
      List<EventScheduleEntity> schedules = eventScheduleRepository.findByEvent(event);
      LocalDate startDate = schedules.get(0).getEventDate();
      LocalDate endDate = schedules.get(schedules.size() - 1).getEventDate();
      LocalTime startTime = schedules.get(0).getStartTime();
      LocalTime endTime = schedules.get(0).getEndTime();

      int appliedPeople = eventAppRepository.countByEventAndEventComp(event, 'N');
      int completedPeople = eventAppRepository.countByEventAndEventComp(event, 'Y');

      EventListDTO eventListDTO2 = EventListDTO.builder()
          .eventPoster(event.getEventPoster())
          .eventTitle(event.getEventTitle())
          .uploadDate(LocalDate.from(event.getUploadDate()))
          .maxPeople(event.getMaxPeople())
          .eventAccept(event.getEventAccept())
          .isRegistrationOpen(event.getIsRegistrationOpen())
          .startDate(startDate)
          .endDate(endDate)
          .startTime(startTime)
          .endTime(endTime)
          .totalAppliedPeople(appliedPeople + completedPeople)
          .completedPeople(completedPeople)
          .build();

      eventListDTO.add(eventListDTO2);
    }

    return eventListDTO;
  }

//  이벤트 참석자 정보 조회
  public AttendListDTO getAttendeeList(Long eventId) {

    EventEntity event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found"));

    List<EventScheduleEntity> schedules = eventScheduleRepository.findByEvent(event);

    List<EventAppEntity> eventAppList = eventAppRepository.findByEvent(event);

    List<AttendInfoDTO> attendInfoDTOList = eventAppList.stream()
        .map(eventApp -> {
          UserEntity user = userRepository.findById(eventApp.getUser().getUserId()).orElse(null);
          return AttendInfoDTO.builder()
              .userId(user.getUserId())
              .userAccount(user.getUserAccount())
              .name(user.getName())
              .userPhone(user.getUserPhone())
              .userDepart(user.getUserDepart())
              .role(user.getRole())
              .build();
        })
        .collect(Collectors.toList());
    LocalDate startDate = schedules.get(0).getEventDate();
    LocalDate endDate = schedules.get(schedules.size() - 1).getEventDate();
    LocalTime startTime = schedules.get(0).getStartTime();
    LocalTime endTime = schedules.get(0).getEndTime();

    return AttendListDTO.builder()
        .eventTitle(event.getEventTitle())
        .startDate(startDate)
        .endDate(endDate)
        .startTime(startTime)
        .endTime(endTime)
        .attendUserList(attendInfoDTOList)
        .build();
  }

  public void deleteEvent(Long eventId) {
    eventRepository.deleteById(eventId);
  }
}
