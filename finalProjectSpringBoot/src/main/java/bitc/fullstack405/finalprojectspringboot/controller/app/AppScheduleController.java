package bitc.fullstack405.finalprojectspringboot.controller.app;

import bitc.fullstack405.finalprojectspringboot.service.EventScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class AppScheduleController {

    private final EventScheduleService eventScheduleService;

    // QR 코드 이미지 보기
    // schedule_id, 여러 회차의 행사 일자, QR 코드 리스트 반환(schedule_id 기준 오름차순)
    // 일주일 전 QR 코드 보기 버튼 활성화 => 앱에서 처리
    @GetMapping("/qr-image/{eventId}/{userId}")
    public ResponseEntity<List<Map<String, Object>>> findQRImages(@PathVariable Long eventId, @PathVariable Long userId) {
        List<Map<String, Object>> qrImages = eventScheduleService.findQrImages(eventId, userId);
        return ResponseEntity.ok(qrImages);
    }
}
