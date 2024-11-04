package bitc.fullstack405.finalprojectspringboot.controller.react;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class WebController implements ErrorController {
  @GetMapping({"/", "/error"})
  public String index() {
    return "index.html";
  }
}
