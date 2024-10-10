package bitc.fullstack405.finalprojectspringboot.database.dto.app.qr;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import lombok.Getter;

@Getter
public class AppQRScanResponse {

    private final Long eventId; // 행사 id
    private final String userName;
    private final String userPhone;

    public AppQRScanResponse(EventEntity event, UserEntity user) {
        this.eventId = event.getEventId();
        this.userName = user.getName();
        this.userPhone = user.getUserPhone();
    }
}
