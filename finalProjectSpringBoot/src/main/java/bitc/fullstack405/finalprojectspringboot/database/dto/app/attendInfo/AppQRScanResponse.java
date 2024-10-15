package bitc.fullstack405.finalprojectspringboot.database.dto.app.attendInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppQRScanResponse {

    // 앱 - QR 스캔 성공 시 반환
    private final String eventTitle; // 행사 제목
    private final String eventDate; // 행사 일자
    private final String startTime; // 행사 시작 시간
    private final String endTime; // 행사 종료 시간
    private final String name; // 회원 이름
    private final String userPhone; // 회원 전화번호
    private final String checkInTime; // 회원 입장 시간
    private final String checkoutTime; // 회원 퇴장 시간
}