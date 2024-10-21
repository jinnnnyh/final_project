package bitc.fullstack405.finalprojectspringboot.database.dto.app.eventApp;

import bitc.fullstack405.finalprojectspringboot.database.entity.EventAppEntity;
import lombok.Getter;

// 행사 1개 신청자 리스트
@Getter
public class AppUserListResponse {
  private Long app_id;
  private Long event_id;
  private Long user_id;

  public AppUserListResponse(EventAppEntity eventAppEntity) {
    this.app_id = eventAppEntity.getAppId();
    this.event_id = eventAppEntity.getEvent().getEventId();
    this.user_id = eventAppEntity.getUser().getUserId();
  }
}
