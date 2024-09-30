package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.entity.TempEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.TempRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TempService {

  private final TempRepository tempRepository;

  public List<TempEntity> findAll() {
    return tempRepository.findAll();
  }
}
