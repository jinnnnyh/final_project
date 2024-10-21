package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.eventApp.AppEventAppListResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.eventApp.AppUserUpcomingEventResponse;
import bitc.fullstack405.finalprojectspringboot.database.entity.*;
import bitc.fullstack405.finalprojectspringboot.database.repository.*;
import bitc.fullstack405.finalprojectspringboot.utils.FileUtils;
import bitc.fullstack405.finalprojectspringboot.utils.QRCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventAppService {

    private final EventAppRepository eventAppRepository;
    private final EventRepository eventRepository;
    private final EventScheduleRepository eventScheduleRepository;
    private final AttendInfoRepository attendInfoRepository;
    private final UserRepository userRepository;
    private final QRCodeGenerator qrCodeGenerator;

    ///////////////////////////
    ////////// <APP> //////////
    ///////////////////////////

    // <APP> 신청하기 - 중복 신청 확인
    public boolean isApplicationExists(Long eventId, Long userId) {
        return eventAppRepository.existsByEvent_EventIdAndUser_UserId(eventId, userId);
    }

    // <APP> 신청하기 - event_app 테이블 데이터 저장
    @Transactional
    public void registerEventApplication(Long eventId, Long userId) throws Exception {
        // 행사 정보와 사용자 정보 가져오기
        EventEntity event = eventRepository.findById(eventId)
            .orElseThrow(() -> new IllegalArgumentException("Event not found: " + eventId));
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        // event_app 테이블에 데이터 저장
        EventAppEntity eventApp = EventAppEntity.builder()
            .event(event)
            .user(user)
            .eventComp('N')
            .appDate(LocalDate.now())
            .build();
        eventAppRepository.save(eventApp);

        // 행사 스케줄 정보 가져오기
        List<EventScheduleEntity> scheduleList = eventScheduleRepository.findByEvent(event);

        // 각 스케줄에 대해 attend_info 데이터 생성
        for (EventScheduleEntity schedule : scheduleList) {
            //String qrCodeImage = qrCodeGenerator.generateQrCode(eventId, schedule.getScheduleId(), userId);
            // QR 코드 생성 로직에서 상대 경로로 이미지 저장
            String qrCodeImage = generateQrCodeImage(eventId, schedule.getScheduleId(), userId);

            AttendInfoEntity attendInfo = AttendInfoEntity.builder()
                .eventSchedule(schedule)
                .attendDate(null)
                .checkInTime(null)
                .checkOutTime(null)
                .user(user)
                .qrImage(qrCodeImage)  // 생성된 QR 코드 이미지 경로 저장
                .attendComp('N')  // 출석 여부 기본값 설정
                .build();

            attendInfoRepository.save(attendInfo);
        }
    }

    // <APP> QR 코드 생성 후 저장
    private String generateQrCodeImage(Long eventId, Long scheduleId, Long userId) throws Exception {

        // QR 코드 이미지 경로 설정 (상대 경로로 저장)
        String path = "../qrImg/";

        // File 클래스를 통해서 파일 객체 생성, 위에서 생성한 파일이 저장될 폴더를 가지고 File 클래스 객체 생성
        File file = new File(path);

        // 위에서 지정한 경로가 실제로 존재하는지 여부 확인
        if (file.exists() == false) {
            // 위에서 지정한 경로가 없을 경우 해당 폴더를 생성
            file.mkdirs();
        }

        // QR 코드 이미지를 저장할 파일 이름 생성
        String qrFileName = System.nanoTime() + ".png";
        file = new File(file.getCanonicalPath() + "/" + qrFileName);

        // QR 코드 생성 로직 (생성된 QR 코드 이미지를 해당 경로에 저장)
        qrCodeGenerator.generate(eventId, scheduleId, userId, file);

        // 저장된 QR 코드 이미지 파일 이름 반환
        return qrFileName;
    }

    // <APP> 특정 유저의 행사 신청 내역 목록 (전체, 행사 id 기준 내림차순)
    public List<AppEventAppListResponse> findMyEvents(Long userId) {
        return eventAppRepository.findByUser_UserIdOrderByEvent_EventIdDesc(userId)
            .stream()
            .map(eventApp -> new AppEventAppListResponse(eventApp.getEvent(), eventApp))
            .toList();
    }

    // <APP> 특정 유저의 행사 신청 내역 (수료, 행사 id 기준 내림차순)
    public List<AppEventAppListResponse> findMyCompleteEvents(Long userId) {
        // eventComp가 'Y'인 값만 가져오기
        return eventAppRepository.findByUser_UserIdAndEventCompOrderByEvent_EventIdDesc(userId, 'Y')
            .stream()
            .map(eventApp -> new AppEventAppListResponse(eventApp.getEvent(), eventApp))
            .toList();
    }

    // <APP> 특정 유저의 행사 신청 내역 (미수료, 행사 id 기준 내림차순)
    public List<AppEventAppListResponse> findMyIncompleteEvents(Long userId) {
        // eventComp가 'N'인 값만 가져오기
        return eventAppRepository.findByUser_UserIdAndEventCompOrderByEvent_EventIdDesc(userId, 'N')
            .stream()
            .map(eventApp -> new AppEventAppListResponse(eventApp.getEvent(), eventApp))
            .toList();
    }

    // <APP> 회원 - 신청 내역 중 곧 시작하는 행사 1개
    // 조건 : 수료 여부 N, 행사 첫 번째 날, 시간 체크((현재 시각 <= start_time), 이미 해당 회차의 행사가 시작하면 안 뜨게)
    // event id, event title, 조건에 맞는 행사 날짜(eventDate), 수료 여부(eventComp), 해당 회차의 시작(start_time)/종료(end_time) 시간(HH:MM)
    public AppUserUpcomingEventResponse findUpcomingEventForUser(Long userId) {
        List<Object[]> results = eventAppRepository.findUpcomingEventForUser(userId);

        Object[] result = results.get(0);
        EventAppEntity eventApp = (EventAppEntity) result[0];
        EventEntity event = (EventEntity) result[1];
        EventScheduleEntity schedule = (EventScheduleEntity) result[2];

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 10/15 날짜포맷수정
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return AppUserUpcomingEventResponse.builder()
                .eventId(event.getEventId())
                .eventTitle(event.getEventTitle())
                .eventDate(schedule.getEventDate().format(dateFormatter))
                .eventComp(eventApp.getEventComp())
                .startTime(schedule.getStartTime().format(timeFormatter))
                .endTime(schedule.getEndTime().format(timeFormatter))
                .build();
    }

    // <APP> 행사 신청 취소 - 오늘이 해당 행사의 첫 번째 회차 이후인지 확인
    public boolean beforeEvent(Long eventId) {
        // 가장 첫 번째 회차 일정 가져오기
        EventScheduleEntity eventSchedule = eventScheduleRepository.findFirstByEvent_EventIdOrderByScheduleIdAsc(eventId);

        // 첫 번째 일정의 날짜가 오늘 이후인지를 확인
        return eventSchedule != null && !eventSchedule.getEventDate().isAfter(LocalDate.now());
    }

    // <APP> 행사 신청 취소 - 데이터 삭제
    // event_app 테이블, attend_info 테이블에 있는 신청 데이터 삭제
    @Transactional
    public void delete(Long eventId, Long userId) throws Exception {
        EventAppEntity eventApp = eventAppRepository.findByEvent_EventIdAndUser_UserId(eventId, userId);
        List<EventScheduleEntity> scheduleList = eventScheduleRepository.findByEvent(eventApp.getEvent());

        for (EventScheduleEntity schedule : scheduleList) {
            Optional<AttendInfoEntity> attendInfo = attendInfoRepository.findByEventSchedule_ScheduleIdAndUser_UserId(schedule.getScheduleId(), userId);
            if (attendInfo.isPresent()) {
                FileUtils fileUtils = new FileUtils();

                fileUtils.deleteFile("../qrImg/", attendInfo.get().getQrImage());

                attendInfoRepository.deleteById(attendInfo.get().getAttendId());
            }
        }

        eventAppRepository.delete(eventApp);
    }
}