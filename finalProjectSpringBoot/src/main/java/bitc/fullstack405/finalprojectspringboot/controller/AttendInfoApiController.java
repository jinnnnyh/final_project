package bitc.fullstack405.finalprojectspringboot.controller;

import bitc.fullstack405.finalprojectspringboot.database.entity.AttendInfoEntity;
import bitc.fullstack405.finalprojectspringboot.service.AttendInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AttendInfoApiController {

    private final AttendInfoService attendInfoService;

    // 행사 참석 신청
//    @PostMapping(value = "/attend/{eventId}/{userId}")
//    public ResponseEntity<AttendInfoEntity> addAttend(
//            @PathVariable Long eventId,
//            @PathVariable Long userId) {
//
//        // 참석 정보 저장
//
//
//        // 회원이 가져온 qr에 있는 행사 id랑 관리자가 qr 찍을 때 가진 행사 id랑 같은지 다른지 확인
//
//
//    }

    // 행사 참석자 수

    // 수료증 반환

    // QR 데이터 생성

    // QR 데이터 반환

    // 입실

    // 퇴실
}
