package bitc.fullstack405.finalprojectspringboot.service.firebase;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.event.AppAdminUpcomingEventResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.eventApp.AppUserListResponse;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventAppEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventScheduleEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventAppRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventScheduleRepository;
import bitc.fullstack405.finalprojectspringboot.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class NotificationScheduler {
  private final NotificationService notificationService; // 알림 보내는
  private final EventAppRepository  eventAppRepository; // 신청자 리스트 받아오는
  private final EventScheduleRepository eventScheduleRepository; // 행사 1개 받아오는
  private final EventService eventService;

  // 매일 오전 10시에 실행
  // 초, 분, 시, 일, 월, 요일
  @Scheduled(cron ="0 0 16 * * ?")
  public void sendNotification() {
    String topic = "notice";



    // 시작 1주일 전인 행사 리스트를 받고
    List<EventScheduleEntity> eventList = eventService.findEventsOneWeekBefore();
    System.out.println("eventList : "+eventList);

    // 반복문, eventList에서 EventScheduleEntity 형식으로 한개씩 뽑고
    for(EventScheduleEntity event : eventList) {
      Map<String, String> data = new HashMap<>();
      data.put("title", "행사 알림");
      data.put("body", event.getEvent().getEventTitle() + " 행사가 1주일 전입니다!");

      notificationService.sendNotification(topic, data);
    }

  }

}
//      // 신청자 리스트 추출
//      List<EventAppEntity> appList = eventAppRepository.findByEvent(event.getEvent());
//      System.out.println("appList : "+appList);
//
//      for(EventAppEntity app : appList) {
//        notificationService.sendNotification(topic,"행사 알림",event.getEvent().getEventTitle()+" 행사 1주일 전입니다. 자세한 사항은 신청 내역을 참고해주세요!");
//      }
//    }