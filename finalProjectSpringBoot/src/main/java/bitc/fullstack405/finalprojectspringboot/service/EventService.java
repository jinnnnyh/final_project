package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.event.AppAdminUpcomingEventResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.event.AppEventDetailResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.event.AppEventListResponse;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventScheduleEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventScheduleRepository;
import bitc.fullstack405.finalprojectspringboot.database.dto.event.*;
import bitc.fullstack405.finalprojectspringboot.database.entity.*;
import bitc.fullstack405.finalprojectspringboot.database.repository.*;
import bitc.fullstack405.finalprojectspringboot.utils.FileUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventScheduleRepository eventScheduleRepository;
    private final UserRepository userRepository;
    private final AttendInfoRepository attendInfoRepository;
    private final EventAppRepository eventAppRepository;
    private final EventScheduleRepository scheduleRepository;

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

    // <APP> 행사 상세 화면 - 행사 안내
    public AppEventDetailResponse findEventDetail(Long eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + eventId));

        // userId가 없으므로 EventAppEntity는 null로 전달
        return new AppEventDetailResponse(event, null);
    }

    // <APP> 행사 상세 화면 - 신청 내역
    // userId가 있는 경우 이벤트와 관련된 신청 정보까지 포함하여 반환
    public AppEventDetailResponse findAppEventDetail(Long eventId, Long userId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + eventId));

        EventAppEntity eventApp = eventAppRepository.findByEvent_EventIdAndUser_UserId(eventId, userId);

        return new AppEventDetailResponse(event, eventApp);
    }

    // <APP> 관리자 - 예정 행사 1개
    public AppAdminUpcomingEventResponse findUpcomingEventForAdmin() {
        List<EventScheduleEntity> eventSchedules = eventScheduleRepository.findUpcomingEventSchedules();

        if (eventSchedules.isEmpty()) {
            return null;
        }

        EventScheduleEntity eventSchedule = eventSchedules.get(0); // 첫 번째 결과 선택

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 10/14 날짜 포맷 수정
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

    public List<EventScheduleEntity> findEventsOneWeekBefore() {
        LocalDateTime oneWeekFromNow = LocalDateTime.now().plusWeeks(1); // 오늘 기준 일주일 후의 날짜
        System.out.println(oneWeekFromNow);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<EventScheduleEntity> upcomingEvents = new ArrayList<>();
        
        List<EventScheduleEntity> events = eventScheduleRepository.findAll(); // 스케쥴리스트 전부 불러와서
        
        for (EventScheduleEntity event : events) {
            // 행사 날짜 && 오늘 기준 일주일 후의 날짜인 것만 넣음
            if (event.getEventDate().format(dateFormatter).equals(ChronoLocalDate.from(oneWeekFromNow).format(dateFormatter))) {
                upcomingEvents.add(event);
            }
        }
        return upcomingEvents;
    }

    ///////////////////////////
    ////////// <WEB> //////////
    ///////////////////////////

    // 행사 글 목록 - 모두 출력
    public List<EventEntity> findAllSortedByEventIdDesc() {
        return eventRepository.findAllByOrderByEventIdDesc();
    }

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
    public EventViewDTO eventView(Long eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException("Event not found"));

        UserEntity uploader = event.getPosterUser();
        ViewUserDTO uploaderDTO = ViewUserDTO.builder()
                .name(uploader.getName())
                .userId(uploader.getUserId())
                .build();

        UserEntity approver = event.getApprover();
        ViewUserDTO approverDTO = null;

        if (approver != null) {
            approverDTO = ViewUserDTO.builder()
                    .userId(approver.getUserId())
                    .name(approver.getName())
                    .build();
        }

        List<EventScheduleEntity> eventScheduleList = event.getScheduleList();
        List<EventScheduleViewDTO> eventScheduleViewDTOList = new ArrayList<>();

        for (EventScheduleEntity eventScheduleEntity : eventScheduleList) {
            EventScheduleViewDTO eventScheduleViewDTO = EventScheduleViewDTO.builder()
                    .scheduleId(eventScheduleEntity.getScheduleId())
                    .eventDate(eventScheduleEntity.getEventDate())
                    .startTime(eventScheduleEntity.getStartTime())
                    .endTime(eventScheduleEntity.getEndTime())
                    .build();

            eventScheduleViewDTOList.add(eventScheduleViewDTO);
        }

        EventViewDTO eventViewDTO = EventViewDTO.builder()
                .eventId(event.getEventId())
                .eventTitle(event.getEventTitle())
                .eventContent(event.getEventContent())
                .eventPoster(event.getEventPoster())
                .eventAccept(event.getEventAccept())
                .maxPeople(event.getMaxPeople())
                .isRegistrationOpen(event.getIsRegistrationOpen())
                .acceptedDate(event.getAcceptedDate())
                .visibleDate(event.getVisibleDate())
                .invisibleDate(event.getInvisibleDate())
                .uploadDate(event.getUploadDate())
                .eventSchedule(eventScheduleViewDTOList)
                .posterUser(uploaderDTO)
                .approver(approverDTO)
                .build();

        return eventViewDTO;
    }

    //  이벤트 리스트 전체 출력
    public List<EventListDTO> getEventList() {
        List<EventEntity> events = eventRepository.findAllByOrderByEventIdDesc();
        List<EventListDTO> eventListDTO = new ArrayList<>();

        for (EventEntity event : events) {
            List<EventScheduleEntity> schedules = eventScheduleRepository.findByEvent(event);

            // schedules가 비어 있는 경우
            if (schedules.isEmpty()) {
                continue;
            }

            LocalDate startDate = schedules.get(0).getEventDate();
            LocalDate endDate = schedules.get(schedules.size() - 1).getEventDate();
            LocalTime startTime = schedules.get(0).getStartTime();
            LocalTime endTime = schedules.get(0).getEndTime();

            int appliedPeople = eventAppRepository.countByEventAndEventComp(event, 'N');
            int completedPeople = eventAppRepository.countByEventAndEventComp(event, 'Y');

            String eventUploaderName = event.getPosterUser().getName();

            String eventApproverName = event.getApprover() != null ? event.getApprover().getName() : "미승인";

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
                    .eventId(event.getEventId())
                    .totalAppliedPeople(appliedPeople + completedPeople)
                    .completedPeople(completedPeople)
                    .visibleDate(event.getVisibleDate())
                    .invisibleDate(event.getInvisibleDate())
                    .eventApproverName(eventApproverName) // approver name 추가
                    .eventUploaderName(eventUploaderName) // uploader name 추가
                    .isRegistrationOpen(event.getIsRegistrationOpen())
                    .build();

            eventListDTO.add(eventListDTO2);
        }

        return eventListDTO;
    }

    //  이벤트 참석자 정보 조회
    public AttendListDTO getAttendeeList(Long eventId) {

        EventEntity event = eventRepository.findById(eventId).get();

        List<EventScheduleEntity> schedules = eventScheduleRepository.findByEvent(event);

        List<AttendInfoDTO> attendInfoDTOList = schedules.stream()
                .flatMap(schedule -> schedule.getAttendInfoList().stream())
                .map(attendInfoEntity -> AttendInfoDTO.builder()
                        .attendId(attendInfoEntity.getAttendId())
                        .attendComp(attendInfoEntity.getAttendComp())
                        .attendDate(attendInfoEntity.getAttendDate())
                        .checkInTime(attendInfoEntity.getCheckInTime())
                        .checkOutTime(attendInfoEntity.getCheckOutTime())
                        .userId(attendInfoEntity.getUser().getUserId())
                        .build()
                )
                .toList();

        List<EventAppEntity> eventAppList = eventAppRepository.findByEvent(event);

        List<EventAppDTO> eventAppDTOList = eventAppList.stream()
                .map(eventApp -> {
                    UserEntity user = userRepository.findById(eventApp.getUser().getUserId()).orElse(null);

                    List<AttendInfoDTO> userAttendInfoList = attendInfoDTOList.stream()
                            .filter(attendInfoDTO -> attendInfoDTO.getUserId().equals(user.getUserId()))
                            .toList();

                    return EventAppDTO.builder()
                            .userId(user.getUserId())
                            .userAccount(user.getUserAccount())
                            .name(user.getName())
                            .userPhone(user.getUserPhone())
                            .userDepart(user.getUserDepart())
                            .role(user.getRole())
                            .attendInfoDTOList(userAttendInfoList)
                            .appId(eventApp.getAppId())
                            .eventComp(eventApp.getEventComp())
                            .build();
                })
                .collect(Collectors.toList());

        LocalDate startDate = schedules.get(0).getEventDate();
        LocalDate endDate = schedules.get(schedules.size() - 1).getEventDate();
        LocalTime startTime = schedules.get(0).getStartTime();
        LocalTime endTime = schedules.get(0).getEndTime();

        List<EventScheduleDTO> eventScheduleDTOList = schedules.stream()
                .map(schedule -> EventScheduleDTO.builder()
                        .scheduleId(schedule.getScheduleId())
                        .eventDate(schedule.getEventDate())
                        .build())
                .collect(Collectors.toList());

        UserEntity uploader = userRepository.findById(event.getPosterUser().getUserId()).orElse(null);

        UserEntity approver = (event.getApprover() != null)
                ? userRepository.findById(event.getApprover().getUserId()).orElse(null)
                : null;

        ViewUserDTO uploaderDTO = ViewUserDTO.builder()
                .userId(uploader.getUserId())
                .name(uploader.getName())
                .build();

        ViewUserDTO approverDTO = (approver != null)
                ? ViewUserDTO.builder()
                .userId(approver.getUserId())
                .name(approver.getName())
                .build()
                : null;

        return AttendListDTO.builder()
                .eventTitle(event.getEventTitle())
                .startDate(startDate)
                .endDate(endDate)
                .startTime(startTime)
                .endTime(endTime)
                .maxPeople(event.getMaxPeople())
                .attendUserList(eventAppDTOList)
                .eventScheduleDTOList(eventScheduleDTOList)
                .acceptedDate(event.getAcceptedDate())
                .eventAccept(event.getEventAccept())
                .approver(approverDTO)
                .uploader(uploaderDTO)
                .uploadDate(event.getUploadDate())
                .build();
    }

    //  행사 삭제
    public void deleteEvent(Long eventId) throws Exception {
        EventEntity event = eventRepository.findById(eventId).get();

        FileUtils fileUtils = new FileUtils();

        fileUtils.deleteFile("../eventImg/", event.getEventPoster());

        eventRepository.deleteById(eventId);
    }

    //  이벤트 승인
    @Transactional
    public void acceptEvent(Long eventId, Long userId) {
        EventEntity event = eventRepository.findById(eventId).get();
        UserEntity approver = userRepository.findById(userId).get();

        LocalDate acceptedDate = LocalDate.now();

        EventEntity updatedEvent = EventEntity.builder()
                .eventId(event.getEventId())
                .eventTitle(event.getEventTitle())
                .eventContent(event.getEventContent())
                .approver(approver)
                .posterUser(event.getPosterUser())
                .scheduleList(event.getScheduleList())
                .eventAppList(event.getEventAppList())
                .isRegistrationOpen(event.getIsRegistrationOpen())
                .acceptedDate(acceptedDate)
                .eventAccept(2)
                .uploadDate(event.getUploadDate())
                .eventPoster(event.getEventPoster())
                .maxPeople(event.getMaxPeople())
                .visibleDate(event.getVisibleDate())
                .invisibleDate(event.getInvisibleDate())
                .build();

        eventRepository.save(updatedEvent);
    }


    //  행사 수정
    @Transactional
    public void updateEvent(Long eventId, EventUpdateDTO eventUpdateDTO, MultipartFile file) throws Exception {

        EventEntity event = eventRepository.findById(eventId).get();

        eventScheduleRepository.deleteByEvent(event);

        LocalDate startDate = LocalDate.parse(eventUpdateDTO.getEventStartDate());
        LocalDate endDate = LocalDate.parse(eventUpdateDTO.getEventEndDate());
        LocalTime startTime = LocalTime.parse(eventUpdateDTO.getStartTime());
        LocalTime endTime = LocalTime.parse(eventUpdateDTO.getEndTime());

        LocalDate invisDate = startDate.minusWeeks(1);
        LocalDate visDate = startDate.minusWeeks(2);


        EventEntity updatedEvent = event.toBuilder()
                .eventTitle(eventUpdateDTO.getEventTitle())
                .eventContent(eventUpdateDTO.getEventContent())
                .maxPeople(Integer.parseInt(eventUpdateDTO.getMaxPeople()))
                .posterUser(userRepository.findById(eventUpdateDTO.getUserId()).get())
                .eventAccept(1)     // 승인/거부 상태여도 수정한 뒤에는 승인대기로 변경
                .isRegistrationOpen(event.getIsRegistrationOpen())
                .uploadDate(event.getUploadDate()) // 수정일로 바꿀지? 아니면 최초업로드일자 유지할지?
                .acceptedDate(null) // 승인했더라도 승인대기상태가 되므로 승인일자 공백
                .approver(null)    // 승인했더라도 승인대기상태가 되므로 승인자 공백
                .visibleDate(visDate)
                .invisibleDate(invisDate)
                .build();

        FileUtils fileUtil = new FileUtils();

        if (file != null && !file.isEmpty()) {
            if (event.getEventPoster() != null) {
                fileUtil.deleteFile("../eventImg/", event.getEventPoster());
            }
            String fileName = fileUtil.parseFileInfo(file);
            updatedEvent = updatedEvent.toBuilder()
                    .eventPoster(fileName)
                    .build();
        }

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            EventScheduleEntity newSchedule = EventScheduleEntity.builder()
                    .event(updatedEvent)
                    .eventDate(date)
                    .startTime(startTime)
                    .endTime(endTime)
                    .build();

            eventScheduleRepository.save(newSchedule);
        }

        eventRepository.save(updatedEvent);
    }

    //  이벤트 승인 거부
    @Transactional
    public void denyEvent(Long eventId) {
        EventEntity event = eventRepository.findById(eventId).get();

        EventEntity updatedEvent = event.toBuilder()
                .eventTitle(event.getEventTitle())
                .eventContent(event.getEventContent())
                .maxPeople(event.getMaxPeople())
                .posterUser(event.getPosterUser())
                .eventAccept(3)
                .isRegistrationOpen(event.getIsRegistrationOpen())
                .uploadDate(event.getUploadDate())
                .acceptedDate(null) // 승인 한 뒤에도 거부 시 공백이 되므로
                .approver(null)    // 승인 한 뒤에도 거부 시 공백이 되므로
                .eventAppList(event.getEventAppList())
                .scheduleList(event.getScheduleList())
                .eventPoster(event.getEventPoster())
                .visibleDate(event.getVisibleDate())
                .invisibleDate(event.getInvisibleDate())
                .build();

        eventRepository.save(updatedEvent);
    }

    //  이벤트 마감
    @Transactional
    public void endEvent(Long eventId) {
        EventEntity event = eventRepository.findById(eventId).get();

        EventEntity endEvent = event.toBuilder()
                .eventTitle(event.getEventTitle())
                .eventContent(event.getEventContent())
                .maxPeople(event.getMaxPeople())
                .posterUser(event.getPosterUser())
                .eventAccept(event.getEventAccept())
                .isRegistrationOpen('N')
                .uploadDate(event.getUploadDate())
                .acceptedDate(event.getAcceptedDate())
                .approver(event.getApprover())
                .eventAppList(event.getEventAppList())
                .scheduleList(event.getScheduleList())
                .eventPoster(event.getEventPoster())
                .visibleDate(event.getVisibleDate())
                .invisibleDate(event.getInvisibleDate())
                .build();

        eventRepository.save(endEvent);
    }

    //  이벤트 승인 후 취소
    @Transactional
    public void acceptCancel(Long eventId) {
        EventEntity event = eventRepository.findById(eventId).get();

        EventEntity acceptCancelEvent = event.toBuilder()
                .eventTitle(event.getEventTitle())
                .eventContent(event.getEventContent())
                .maxPeople(event.getMaxPeople())
                .posterUser(event.getPosterUser())
                .eventAccept(1)
                .isRegistrationOpen(event.getIsRegistrationOpen())
                .uploadDate(event.getUploadDate())
                .acceptedDate(event.getAcceptedDate())
                .approver(event.getApprover())
                .eventAppList(event.getEventAppList())
                .scheduleList(event.getScheduleList())
                .eventPoster(event.getEventPoster())
                .visibleDate(event.getVisibleDate())
                .invisibleDate(event.getInvisibleDate())
                .build();

        eventRepository.save(acceptCancelEvent);
    }

//    이벤트 수정 View
  public EventUpdateViewDTO eventUpdateView(Long eventId) {
    EventEntity eventEntity = eventRepository.findById(eventId)
        .orElseThrow(() -> new EntityNotFoundException("해당 이벤트를 찾을 수 없습니다."));

    List<EventScheduleEntity> eventScheduleList = eventScheduleRepository.findByEvent(eventEntity);

    LocalDate startDate = eventScheduleList.get(0).getEventDate();
    LocalDate endDate = eventScheduleList.get(eventScheduleList.size() - 1).getEventDate();
    LocalTime startTime = eventScheduleList.get(0).getStartTime();
    LocalTime endTime = eventScheduleList.get(0).getEndTime();


    return EventUpdateViewDTO.builder()
        .eventId(eventEntity.getEventId())
        .eventTitle(eventEntity.getEventTitle())
        .startDate(startDate)
        .endDate(endDate)
        .startTime(startTime)
        .endTime(endTime)
        .maxPeople(eventEntity.getMaxPeople())
        .eventContent(eventEntity.getEventContent())
        .build();
  }
}
