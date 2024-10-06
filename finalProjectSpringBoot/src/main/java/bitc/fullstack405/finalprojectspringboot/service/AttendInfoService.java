package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.dto.AttendInfo.AddAttendRequest;
import bitc.fullstack405.finalprojectspringboot.database.dto.notification.AddNotificationRequest;
import bitc.fullstack405.finalprojectspringboot.database.entity.AttendInfoEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.NotificationEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.AttendInfoRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.NotificationRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor
@Service
public class AttendInfoService {

    private final AttendInfoRepository attendInfoRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    // 행사 참석
    public AttendInfoEntity save(Long eventId, Long userId) {

//        // 현재 인증된 사용자 정보 가져오기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserEntity user = (UserEntity) authentication.getPrincipal();  // 인증된 UserEntity 가져오기

        // 스프링 시큐리티 합치면 삭제할 부분(userId가 1인 사람이 무조건 등록)
        UserEntity user = userRepository.findById((long)1).orElseThrow(() -> new IllegalArgumentException("not found id "));

        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        // 지각 여부 설정 (현재 시간이 행사 시작 시간을 넘었는지 여부)
        Character late = (currentTime.isAfter(event.getStartTime())) ? 'Y' : 'N';

        AttendInfoEntity attendInfo = AttendInfoEntity.builder()
                .user(user)
                .event(event)
                .attend('N')
                .attendDate(currentDate)
                .checkInTime(currentTime)
                .checkOutTime(null)
                .lateOrNot(late)
                .build();

        return attendInfoRepository.save(attendInfo);
    }


}
