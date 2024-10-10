package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.repository.EventScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventScheduleService {

    private final EventScheduleRepository eventScheduleRepository;

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

}
