package bitc.fullstack405.finalprojectspringboot.service;

import bitc.fullstack405.finalprojectspringboot.database.dto.web.notification.AddNotificationRequest;
import bitc.fullstack405.finalprojectspringboot.database.dto.web.notification.UpdateNotificationRequest;
import bitc.fullstack405.finalprojectspringboot.database.entity.NotificationEntity;
import bitc.fullstack405.finalprojectspringboot.database.entity.UserEntity;
import bitc.fullstack405.finalprojectspringboot.database.repository.NotificationRepository;
import bitc.fullstack405.finalprojectspringboot.database.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    // 공지 글 등록
    public NotificationEntity save(AddNotificationRequest request) {

//        // 현재 인증된 사용자 정보 가져오기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserEntity user = (UserEntity) authentication.getPrincipal();  // 인증된 UserEntity 가져오기

        // 스프링 시큐리티 합치면 삭제할 부분(userId가 1인 사람이 무조건 등록)
        UserEntity user = userRepository.findById((long)1).orElseThrow(() -> new IllegalArgumentException("not found id "));

        NotificationEntity notification = request.toEntity(user); // user 정보를 넣음

        return notificationRepository.save(notification);
    }

    // 전체 목록 가져오기
    public List<NotificationEntity> findAllSortedByNotiIdDesc() {
        return notificationRepository.findAllByOrderByNotiIdDesc();
    }

    // 지정한 공지글 상세 내용 가져오기
    public NotificationEntity findById(Long id) {
        return notificationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    // 공지글 삭제
    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }

    // 공지글 수정
    @Transactional
    public NotificationEntity update(long id, UpdateNotificationRequest request) {
        NotificationEntity notification = notificationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        notification.updateNotification(request.getNotiTitle(), request.getNotiContent());

        return notification;
    }
}