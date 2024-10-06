package bitc.fullstack405.finalprojectspringboot.database.dto.AttendInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

// 클라이언트가 서버로 전달하는 데이터용 DTO 클래스
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddAttendRequest {
    private LocalDate attendDate; // 참석일
    private LocalTime checkInTime; // 출석 시간
}
