package bitc.fullstack405.finalprojectspringboot.controller.app;

import bitc.fullstack405.finalprojectspringboot.database.dto.app.application.AppEventAppListResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.app.event.AppEventListResponse;
import bitc.fullstack405.finalprojectspringboot.database.dto.web.notification.NotificationResponse;
import bitc.fullstack405.finalprojectspringboot.service.EventAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class AppEventAppController {

    private final EventAppService eventAppService;

    // 추가(신청)

    // userId 신청 목록 조회 < 이거로 제목까지 끌어와지는지 확인
    // 서버에서 제목이랑 날짜 같이 넘겨주는 걸 이걸로 받아지나
    // 신청 내역 전체 목록
    // 제목, 신청일, 수료/미수료 여부
//    @GetMapping("/application-list")
//    public ResponseEntity<List<AppEventAppListResponse>> findMyApplication() {
////        List<AppEventAppListResponse> eventAppList = eventAppService.findAllSortedByNotiIdDesc()
////                .stream()
////                .map(AppEventAppListResponse::new)
////                .toList();
////
////        return ResponseEntity.ok().body(eventAppList);
//
//        List<AppEventAppListResponse> eventAppList = eventAppService.findMyEvents();
//        return ResponseEntity.ok().body(eventAppList);
//    }


    // 유저id, 참석여부 'Y'인 목록 조회(수료 목록)

    // 유저id, 참석여부 'N'인 목록 조회(미수료 목록)

    // 이벤트 id 스케줄 리스트 불러오기 >> 필요 없음


    // 내가 정리한 거
//    <회원이 신청한 목록>
//    교육일말고 신청일 표시
//
//    회원이 신청한 모든 행사 불러오기
//    회원이 신청한 행사 중 이벤트 테이블의 수료 여부가 Y인 것
//    회원이 신청한 행사 중 이벤트 테이블의 수료 여부가 N인 것
//
//    <행사 id로 스케줄 리스트 불러오기는 필요 없음>
//
//    <행사 id 누르면 해당 행사 상세 내용 불러오기>
//
//
//<신청>
//    큐알코드 이미지 생성, app 테이블 insert

}
