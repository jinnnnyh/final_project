package bitc.fullstack405.finalprojectspringboot.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

// firebase 초기화
@Configuration
public class FirebaseConfig {

  @Bean
  public FirebaseApp initializeFirebase() {
    // FirebaseApp이 이미 초기화되어 있는지 확인
    if (FirebaseApp.getApps().isEmpty()) {
      try {
        FileInputStream serviceAccount = new FileInputStream("C:\\Users\\jyh");
        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

        return FirebaseApp.initializeApp(options);
      } catch (Exception e) {
        throw new RuntimeException("Failed to initialize Firebase", e);
      }
    } else {
      // 이미 초기화된 경우 기존 인스턴스를 반환
      return FirebaseApp.getInstance();
    }
  }
}
