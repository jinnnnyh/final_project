package bitc.fullstack405.finalprojectspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FinalProjectSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(FinalProjectSpringBootApplication.class, args);
  }

}
