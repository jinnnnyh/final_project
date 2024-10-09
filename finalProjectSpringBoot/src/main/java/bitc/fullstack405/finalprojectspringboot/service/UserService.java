package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.dto.EventAppInsertRequest;
import bitc.fullstack405.finalprojectspringboot.database.dto.LoginResponse;
import bitc.fullstack405.finalprojectspringboot.database.entity.EventAppEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventAppRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.EventRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final EventAppRepository eventAppRepository;
  private final EventRepository eventRepository;

  public LoginResponse findLoginUser(String userAccount, String password) {
    UserEntity user = userRepository.findUserByUserAccount(userAccount);// 아이디가 존재하는 user 정보 가져옴
    if(user!=null && user.getPassword().equals(password)) {
      return new LoginResponse(user);
    }

    return null;
  }

  public EventAppEntity insertApp(EventAppInsertRequest req){

    EventAppEntity savedApp = req.toEntity(eventRepository,userRepository);
    EventAppEntity app = eventAppRepository.findByUser(savedApp.getUser());
    // 신청자 테이블에 유저id 없을 때
    if(app==null) {
      return eventAppRepository.save(savedApp);
    }
    // 유저id 있을 떄
    else{
      // 이미 신청한 이벤트일 경우
      if(app.getEvent().getEventId() == req.getEventId()){
        return null;
      }
      // 아닐 경우
      return eventAppRepository.save(savedApp);
    }
  }


} // class
