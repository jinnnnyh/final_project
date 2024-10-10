package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.qr.AppQRScanResponse;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.AttendInfoRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventScheduleRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventScheduleService {

    private final AttendInfoRepository attendInfoRepository;
    private final EventScheduleRepository eventScheduleRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    // QR 코드 이미지 보기
    // eventId와 userId로 scheduleId, eventDate, qrImage 조회
    public List<Map<String, Object>> findQrImages(Long eventId, Long userId) {
        List<Object[]> results = eventScheduleRepository.findQrImagesByEventIdAndUserId(eventId, userId);

        return results.stream()
                .map(result -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("scheduleId", Long.valueOf(result[0].toString())); // scheduleId는 Long 타입으로 변환
                    map.put("eventDate", result[1].toString()); // eventDate는 LocalDate이므로 toString()으로 변환
                    map.put("qrImage", result[2].toString());
                    return map;
                })
                .collect(Collectors.toList());
    }

//    public AppQRScanResponse qrScan(Long eventId, Long scheduleId, Long userId) {
//
//
//
//
//
//
//
//        // check_in_time 에 현재 시각 넣기
//        attendInfoRepository.save()
//
//
//
//        EventEntity event = eventRepository.findById(eventId)
//                .orElseThrow(() -> new IllegalArgumentException("not found : " + eventId));
//        UserEntity user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("not found : " + userId));
//
//        return new AppQRScanResponse(event, user);
//    }
}
