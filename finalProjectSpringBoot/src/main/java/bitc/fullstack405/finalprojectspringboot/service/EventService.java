package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.AppAdminUpcomingEventResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.AppEventDetailResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.AppEventListResponse;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventScheduleEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.AttendInfoRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventScheduleRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import bitc.fullstack405.finalprojectspringboot.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventScheduleRepository eventScheduleRepository;
    private final UserRepository userRepository;
    private final AttendInfoRepository attendInfoRepository;
    private final FileUtils fileUtils;

    ///////////////////////////
    ////////// <APP> //////////
    ///////////////////////////

    // <APP> 회원에게 보일 행사 목록
    public List<AppEventListResponse> findAcceptedEvents() {
        List<EventEntity> events = eventRepository.findAcceptedEventsWithCapacity();
        return events.stream()
                .map(AppEventListResponse::new)
                .collect(Collectors.toList());
    }

    // <APP> 회원에게 보일 행사 상세 화면
    public AppEventDetailResponse findById(Long eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + eventId));

        return new AppEventDetailResponse(event);
    }

//    // <APP> 회원 - 신청 내역 중 곧 시작하는 행사 1개
//    public AppUpcomingEventResponse findMyUpcomingEvent(Long userId) {
//        AppUpcomingEventResponse upcomingEvent = eventAppRepository.findMyUpcomingEvent(userId);
//    }

    // <APP> 관리자 - 예정 행사 1개
//    public Optional<AppAdminUpcomingEventResponse> findAdminUpcomingEvent() {
//        List<EventScheduleEntity> eventSchedules = eventScheduleRepository.findUpcomingEventSchedules();
//
//        if (!eventSchedules.isEmpty()) {
//            EventScheduleEntity eventSchedule = eventSchedules.get(0); // 첫 번째 결과 선택
//
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
//
//            return Optional.of(AppAdminUpcomingEventResponse.builder()
//                    .eventId(eventSchedule.getEvent().getEventId())
//                    .eventTitle(eventSchedule.getEvent().getEventTitle())
//                    .isRegistrationOpen(eventSchedule.getEvent().getIsRegistrationOpen())
//                    .eventDate(eventSchedule.getEventDate().format(dateFormatter))
//                    .startTime(eventSchedule.getStartTime().format(timeFormatter))
//                    .endTime(eventSchedule.getEndTime().format(timeFormatter))
//                    .build());
//        } else {
//            return Optional.empty(); // 결과가 없을 경우
//        }
//    }

    // <APP> 관리자 - 예정 행사 1개
    public AppAdminUpcomingEventResponse findAdminUpcomingEvent() {
        List<EventScheduleEntity> eventSchedules = eventScheduleRepository.findUpcomingEventSchedules();

        EventScheduleEntity eventSchedule = eventSchedules.get(0); // 첫 번째 결과 선택

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return AppAdminUpcomingEventResponse.builder()
                .eventId(eventSchedule.getEvent().getEventId())
                .eventTitle(eventSchedule.getEvent().getEventTitle())
                .isRegistrationOpen(eventSchedule.getEvent().getIsRegistrationOpen())
                .eventDate(eventSchedule.getEventDate().format(dateFormatter))
                .startTime(eventSchedule.getStartTime().format(timeFormatter))
                .endTime(eventSchedule.getEndTime().format(timeFormatter))
                .build();
    }


    ///////////////////////////
    ////////// <WEB> //////////
    ///////////////////////////
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
//    // 행사 글 목록 - 모두 출력
//    public List<EventEntity> findAllSortedByEventIdDesc() {
//        return eventRepository.findAllByOrderByEventIdDesc();
//    }
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
}
