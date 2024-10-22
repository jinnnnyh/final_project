package com.fullstack405.bitcfinalprojectkotlin.interfaces

import com.fullstack405.bitcfinalprojectkotlin.data.AdminUpcomingEventData
import com.fullstack405.bitcfinalprojectkotlin.data.AppDetailData
import com.fullstack405.bitcfinalprojectkotlin.data.CertificateData
import com.fullstack405.bitcfinalprojectkotlin.data.CheckedIdData
import com.fullstack405.bitcfinalprojectkotlin.data.EventAppData
import com.fullstack405.bitcfinalprojectkotlin.data.EventDetailData
import com.fullstack405.bitcfinalprojectkotlin.data.EventListData
import com.fullstack405.bitcfinalprojectkotlin.data.InsertUserData
import com.fullstack405.bitcfinalprojectkotlin.data.QRscanData
import com.fullstack405.bitcfinalprojectkotlin.data.UserData
import com.fullstack405.bitcfinalprojectkotlin.data.UpdateData
import com.fullstack405.bitcfinalprojectkotlin.data.UserUpcomingEventData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface Interface {

  ///////////// user

  // 로그인 유저 정보 확인
  // 로그인 아이디, 비번 넣어서 보내기
  @POST("/login")
  fun loginUser(@Body data: UserData):Call<UserData>

  // 유저 1명 정보
  @GET("/app/user/{userId}")
  fun findUserId(@Path("userId") userId:Long):Call<UserData>

  // 회원가입
  @POST("/signup")
  fun insertUser(@Body data:InsertUserData):Call<InsertUserData>

  // 수정
  @PUT("/app/user/{userId}")
  fun updateUser(@Path("userId")id: Long, @Body data: UpdateData):Call<Void>

  // ID중복 여부/ 중복 있으면 null
  @GET("/signup/{userAccount}")
  fun CheckedId(@Path("userAccount") userAccount:String):Call<CheckedIdData>

  // 회원 탈퇴
  @PUT("/app/user/delete/{userId}")
  fun deleteUser(@Path("userId")userId:Long):Call<Void>



  //// 관리자 예정된 행사 1개
  @GET("/app/upcoming-event/admin")
  fun findAdminUpcomingEvent():Call<AdminUpcomingEventData>

  //// 회원 신청 현황 1개
  @GET("/app/upcoming-event/{userId}")
  fun findUpcomingEventForUser(@Path("userId")userId:Long):Call<UserUpcomingEventData>


  /////////// event
  // 승인된 이벤트 리스트
  @GET("/app/accepted-events")
  fun findEventList():Call<List<EventListData>>

  // 이벤트 상세보기 userId = null
  // userId가 없는 경우 ex) http://localhost:8080/app/accepted-events/1
  // 신청 상세보기 userId not null
  // userId가 있는 경우 ex) http://localhost:8080/app/accepted-events/1?userId=3
  // 이벤트, 신청 항목 1개 불러오기
  @GET("/app/accepted-events/{eventId}")
  fun findEventId(@Path("eventId") eventId:Long, @Query("userId") userId: Long?):Call<EventDetailData>

  // 추가 신청 받기
  //  1: 중복 신청, 2: 추가 완료, 3:초과
  @POST("/app/application-direct/{eventId}/{userAccount}")
  fun adminAppDirect(@Path("eventId")eventId: Long, @Path("userAccount")userAccount: String):Call<Int>

  /////////// attend
  // 신청
  // 1: 중복 신청, 2: 신청 완료,  3: 초과
  @POST("/app/application/{eventId}/{userId}")
  fun insertEventApp(@Path("eventId") eventId:Long, @Path("userId") userId:Long):Call<Int>

  // 신청취소
  @DELETE("/app/application-cancel/{eventId}/{userId}")
  fun deleteApplication(@Path("eventId")eventId: Long, @Path("userId")userId: Long):Call<Int>

  // 해당 유저의 신청 목록
  @GET("/app/application-list/{userId}")
  fun findAttendList(@Path("userId") userId:Long):Call<List<EventAppData>>

  // 유저id, 수료 목록
  @GET("/app/complete-application-list/{userId}")
  fun findMyCompleteApplicationList(@Path("userId") id:Long):Call<List<EventAppData>>

  // 유저id, 미수료 목록
  @GET("/app/incomplete-application-list/{userId}")
  fun findMyIncompleteApplicationList(@Path("userId") id:Long):Call<List<EventAppData>>

  // 유저 1명 참석증
  @GET("/app/certificate/{eventId}/{userId}")
  fun findCertificateData(@Path("eventId")eventId: Long, @Path("userId")userId: Long):Call<CertificateData>

  // QR 스캔 후 성공:data, 실패:null
  @PUT("/app/qr-scan/{eventId}/{scheduleId}/{userId}")
  fun insertQRCheck(@Path("eventId")eventId:Long, @Path("scheduleId")scheduleId:Long, @Path("userId")userId:Long):Call<QRscanData>

  // qr 이미지
  // 이벤트id, 회원id >  스케쥴id, QR 이미지 주소, 행사날짜
  @GET("/app/qr-image/{eventId}/{userId}")
  fun findQRImageList(@Path("eventId")eventId:Long, @Path("userId")userId:Long):Call<List<Map<String, Any>>>
}