package bitc.fullstack405.finalprojectspringboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// 영속성 사용 설정
@EnableJpaAuditing
@Configuration
public class AuditingConfig {
}