package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.application.AppEventAppListResponse;
import bitc.fullstack405.finalprojectspringboot.database.entity.*;
import bitc.fullstack405.finalprojectspringboot.database.repository.*;
import bitc.fullstack405.finalprojectspringboot.utils.FileUtils;
import bitc.fullstack405.finalprojectspringboot.utils.QRCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    // 신청하기 - 중복 신청 확인
    public boolean isApplicationExists(Long eventId, Long userId) {
        return eventAppRepository.existsByEvent_EventIdAndUser_UserId(eventId, userId);
    }

    // 신청하기 - event_app 테이블 데이터 저장
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

    // QR 코드 생성 후 저장
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

    // 특정 유저의 행사 신청 내역 목록 (전체, 행사 id 기준 내림차순)
    public List<AppEventAppListResponse> findMyEvents(Long userId) {
        return eventAppRepository.findByUser_UserIdOrderByEvent_EventIdDesc(userId)
                .stream()
                .map(eventApp -> new AppEventAppListResponse(eventApp.getEvent(), eventApp))
                .toList();
    }

    // 특정 유저의 행사 신청 내역 (수료, 행사 id 기준 내림차순)
    public List<AppEventAppListResponse> findMyCompleteEvents(Long userId) {
        // eventComp가 'Y'인 값만 가져오기
        return eventAppRepository.findByUser_UserIdAndEventCompOrderByEvent_EventIdDesc(userId, 'Y')
                .stream()
                .map(eventApp -> new AppEventAppListResponse(eventApp.getEvent(), eventApp))
                .toList();
    }

    // 특정 유저의 행사 신청 내역 (미수료, 행사 id 기준 내림차순)
    public List<AppEventAppListResponse> findMyIncompleteEvents(Long userId) {
        // eventComp가 'N'인 값만 가져오기
        return eventAppRepository.findByUser_UserIdAndEventCompOrderByEvent_EventIdDesc(userId, 'N')
                .stream()
                .map(eventApp -> new AppEventAppListResponse(eventApp.getEvent(), eventApp))
                .toList();
    }

    // eventId와 userId로 attend_info 테이블의 QR 이미지 조회(다시 만들어야 함, 오늘 날짜까지 받아와야 해서)
    public List<String> findQrImages(Long eventId, Long userId) {
        return attendInfoRepository.findQrImagesByEventIdAndUserId(eventId, userId);
    }


    ///////////////////////////
    ////////// <WEB> //////////
    ///////////////////////////
}
