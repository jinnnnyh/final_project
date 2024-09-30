package bitc.fullstack405.finalprojectspringboot.controller;

import bitc.fullstack405.finalprojectspringboot.database.entity.TempEntity;
import bitc.fullstack405.finalprojectspringboot.service.TempService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://localhost:5173")
@RestController
@RequiredArgsConstructor
public class TempController {

  private final TempService tempService;

  @RequestMapping("/temp")
  public List<TempEntity> temp() {
    return tempService.findAll();
  }
}
