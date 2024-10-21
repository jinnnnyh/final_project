package bitc.fullstack405.finalprojectspringboot.service.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationService {

  public void sendNotification(String topic, Map<String, String> data) {
    try {
      Message msg = Message.builder()
          .setTopic(topic)
          .putAllData(data)
          .build();

      String response = FirebaseMessaging.getInstance().send(msg);
      System.out.println("Successfully sent message: " + response);
    } catch (Exception e) {
      System.err.println("Error sending message: " + e.getMessage());
    }
  }
}
