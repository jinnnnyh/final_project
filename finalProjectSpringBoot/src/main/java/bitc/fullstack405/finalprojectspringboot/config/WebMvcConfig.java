package bitc.fullstack405.finalprojectspringboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry reg) {
    reg.addMapping("/**")
        .allowedOrigins("http://localhost:5173")
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedHeaders("*");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    System.out.println(System.getProperty("user.dir"));
    // 지정한 외부폴더를 스프링의 내부 폴더로 추가(행사 이미지)
    registry.addResourceHandler("/eventImg/**").addResourceLocations("file:../eventImg/");
  }
}
