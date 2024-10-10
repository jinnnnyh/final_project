package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.qr.AppQRScanResponse;
import bitc.fullstack405.finalprojectspringboot.database.entity.*;
import bitc.fullstack405.finalprojectspringboot.database.repository.AttendInfoRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventAppRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AttendInfoService {

    private final AttendInfoRepository attendInfoRepository;
    private final EventScheduleRepository eventScheduleRepository;
    private final EventAppRepository eventAppRepository;


//    // QR 코드 스캔 처리 로직
//    public AppQRScanResponse qrScan(Long eventId, Long scheduleId, Long userId) {
//        // 1. attend_info 테이블에서 해당 유저와 스케줄에 대한 정보 조회
//        AttendInfoEntity attendInfo = attendInfoRepository.findByScheduleIdAndUserId(scheduleId, userId)
//                .orElseThrow(() -> new RuntimeException("잘못된 QR 코드입니다."));
//
//        // 2. QR 코드 이미 사용되었는지 확인 (check_in_time, check_out_time 둘 다 null 아님)
//        if (attendInfo.getCheckInTime() != null && attendInfo.getCheckOutTime() != null) {
//            throw new RuntimeException("이미 사용된 QR 코드입니다.");
//        }
//
//        // 3. 사용자 및 행사 정보 조회
//        UserEntity user = attendInfo.getUser();
//        EventScheduleEntity eventSchedule = attendInfo.getEventSchedule();
//        EventEntity event = eventSchedule.getEvent();
//
//        // 4. 입장인지 퇴장인지 확인 후 처리
//        if (attendInfo.getCheckInTime() == null) {
//            // 입장 처리
//            attendInfo = attendInfo.toBuilder()
//                    .attendDate(LocalDate.now())
//                    .checkInTime(LocalTime.now())
//                    .build();
//            attendInfoRepository.save(attendInfo);
//        } else {
//            // 퇴장 처리
//            attendInfo = attendInfo.toBuilder()
//                    .checkOutTime(LocalTime.now())
//                    .attendComp('Y') // 수료 여부 Y로 업데이트
//                    .build();
//            attendInfoRepository.save(attendInfo);
//
//            // 회차별 수료 조건 확인 (입장 및 퇴장 시간 확인)
//            if (attendInfo.getCheckInTime().isAfter(eventSchedule.getStartTime()) ||
//                    attendInfo.getCheckOutTime().isBefore(eventSchedule.getEndTime())) {
//                throw new RuntimeException("수료 조건을 만족하지 못했습니다.");
//            }
//
//            // 모든 회차 수료 확인 후, event_app의 수료 여부 업데이트
//            List<AttendInfoEntity> attendList = attendInfoRepository.findAllByEventAndUser(event, user);
//            if (attendList.stream().allMatch(info -> info.getAttendComp() == 'Y')) {
//                EventAppEntity eventApp = eventAppRepository.findByEventIdAndUserId(eventId, userId)
//                        .orElseThrow(() -> new RuntimeException("행사 신청 정보가 없습니다."));
//                eventApp = eventApp.toBuilder()
//                        .eventComp('Y')
//                        .build();
//                eventAppRepository.save(eventApp);
//            }
//        }
//
//        // QR 스캔 결과 반환
//        return AppQRScanResponse.builder()
//                .eventId(event.getEventId())
//                .userName(user.getName())
//                .userPhone(user.getUserPhone())
//                .build();
//    }
//
//
//
//
//
//        // 1. AttendInfoEntity에서 scheduleId와 userId로 데이터를 조회
//        AttendInfoEntity attendInfo = attendInfoRepository.findByEventSchedule_ScheduleIdAndUser_UserId(scheduleId, userId)
//                .orElseThrow(() -> new IllegalArgumentException("잘못된 QR 코드입니다."));
//
//        // 이미 입장한 QR 코드인지 확인
//        if (attendInfo.getCheckInTime() != null && attendInfo.getCheckOutTime() != null) {
//            throw new IllegalStateException("이미 사용된 QR 코드입니다.");
//        }
//
//        // 2. 입장/퇴장 여부 확인
//        if (attendInfo.getCheckInTime() == null) {
//            // 입장 처리
//            attendInfo = attendInfo.toBuilder()
//                    .attendDate(LocalDate.now())
//                    .checkInTime(LocalTime.now())
//                    .build();
//            attendInfoRepository.save(attendInfo);
//        } else {
//            // 퇴장 처리
//            attendInfo = attendInfo.toBuilder()
//                    .checkOutTime(LocalTime.now())
//                    .attendComp('Y') // 수료 여부 Y로 업데이트
//                    .build();
//            attendInfoRepository.save(attendInfo);
//
//            // 회차별 수료 조건 확인 (입장 및 퇴장 시간 확인)
//            if (attendInfo.getCheckInTime().isAfter(eventSchedule.getStartTime()) ||
//                    attendInfo.getCheckOutTime().isBefore(eventSchedule.getEndTime())) {
//                throw new RuntimeException("수료 조건을 만족하지 못했습니다.");
//            }
//
//            // 모든 회차 수료 확인 후, event_app의 수료 여부 업데이트
//            List<AttendInfoEntity> attendList = attendInfoRepository.findAllByEventAndUser(event, user);
//            if (attendList.stream().allMatch(info -> info.getAttendComp() == 'Y')) {
//                EventAppEntity eventApp = eventAppRepository.findByEventIdAndUserId(eventId, userId)
//                        .orElseThrow(() -> new RuntimeException("행사 신청 정보가 없습니다."));
//                eventApp = eventApp.toBuilder()
//                        .eventComp('Y')
//                        .build();
//                eventAppRepository.save(eventApp);
//            }
//        }
//
//        // 3. 마지막 회차인 경우 행사 수료 상태 업데이트
//        checkEventCompletion(eventId, userId);
//
//        // 4. 행사 제목, 회원 이름, 전화번호 반환
//        // QR 스캔 결과 반환
//        return AppQRScanResponse.builder()
//                .eventId(event.getEventId())
//                .userName(user.getName())
//                .userPhone(user.getUserPhone())
//                .build();
//    }
//
//    private void validateAttendanceCompletion(AttendInfoEntity attendInfo, EventScheduleEntity schedule) {
//        // 입장했는지 확인
//        if (attendInfo.getCheckInTime() == null) {
//            throw new IllegalStateException("입장 기록이 없습니다.");
//        }
//
//        // 지각 여부 확인 (입장 시간이 시작 시간보다 빠른지)
//        if (attendInfo.getCheckInTime().isAfter(schedule.getStartTime())) {
//            throw new IllegalStateException("지각했습니다.");
//        }
//
//        // 퇴장 시간이 행사 종료 시간 이후인지 확인
//        if (attendInfo.getCheckOutTime().isBefore(schedule.getEndTime())) {
//            throw new IllegalStateException("행사 끝까지 참석하지 않았습니다.");
//        }
//
//        // 당일 수료 여부 업데이트
//        attendInfo.setAttendComp('Y');
//    }
//
//    private void checkEventCompletion(Long eventId, Long userId) {
//        // 모든 회차가 수료됐는지 확인하고, 모두 수료되었으면 eventApp의 eventComp 업데이트
//        boolean allCompleted = attendInfoRepository.allSessionsCompleted(userId, eventId);
//        if (allCompleted) {
//            EventAppEntity eventApp = eventAppRepository.findByEvent_EventIdAndUser_UserId(eventId, userId)
//                    .orElseThrow(() -> new IllegalArgumentException("행사 신청 정보를 찾을 수 없습니다."));
//            eventApp.setEventComp('Y');
//        }
//    }
}
