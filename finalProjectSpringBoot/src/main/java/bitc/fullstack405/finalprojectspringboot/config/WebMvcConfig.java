package bitc.fullstack405.finalprojectspringboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry reg) {
    reg.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedHeaders("*");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // 지정한 외부폴더를 스프링의 내부 폴더로 추가(행사 이미지)
    registry.addResourceHandler("/eventImg/**").addResourceLocations("file:/home/ec2-user/eventImg/");

    // 지정한 외부폴더를 스프링의 내부 폴더로 추가(QR 코드 이미지)
    registry.addResourceHandler("/qrImg/**").addResourceLocations("file:/home/ec2-user/qrImg/");
  }

//  @Override
//  public void addViewControllers(ViewControllerRegistry registry) {
//    registry.addViewController("/**").setViewName("forward:/");
//  }
}
