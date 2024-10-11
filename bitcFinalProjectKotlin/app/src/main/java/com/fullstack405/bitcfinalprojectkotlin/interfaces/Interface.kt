package com.fullstack405.bitcfinalprojectkotlin.interfaces

import com.fullstack405.bitcfinalprojectkotlin.data.EventAppData
import com.fullstack405.bitcfinalprojectkotlin.data.EventDetailData
import com.fullstack405.bitcfinalprojectkotlin.data.EventListData
import com.fullstack405.bitcfinalprojectkotlin.data.NoticeData
import com.fullstack405.bitcfinalprojectkotlin.data.InsertUserData
import com.fullstack405.bitcfinalprojectkotlin.data.UserData
import com.fullstack405.bitcfinalprojectkotlin.data.UpdateData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Interface {

  ///////////// user
  // 유저 리스트
  fun findUserList():Call<List<UserData>>

  // 로그인 유저 정보 확인
  // 로그인 아이디, 비번 넣어서 보내기
  @POST("/login")
  fun loginUser(@Body data: UserData):Call<UserData>

  // 유저 1명 정보
  @POST("/app/user/{userId}")
  fun findUserId(@Path("userId") userId:Long):Call<UserData>

  // 회원가입
  @POST("/signup")
  fun insertUser(@Body data:InsertUserData):Call<InsertUserData>

  // 수정
  @PUT("/app/user/{userId}")
  fun updateUser(@Path("userId")id: Long, @Body data: UpdateData):Call<Void>

  // ID중복 여부/ 중복 있으면 T
  @POST("/signup/{userAccount}")
  fun CheckedId(@Path("userAccount") userAccount:String):Call<Boolean>


  /////////// event
  // 승인된 이벤트 리스트
  @GET("/app/accepted-events")
  fun findEventList():Call<List<EventListData>>

  // 이벤트 항목 1개 불러오기
  @POST("/app/accepted-events/{eventId}")
  fun findEventId(@Path("eventId") eventId:Long):Call<EventDetailData>

  /////////// attend
  // 신청
  @POST("/app/application/{eventId}/{userId}")
  fun insertEventApp(@Path("eventId") eventId:Long, @Path("userId") userId:Long):Call<Int>

  // 해당 유저의 신청 목록
  @POST("/app/application-list/{userId}")
  fun findAttendList(@Path("userId") userId:Long):Call<List<EventAppData>>

  // 유저id, 수료 목록
  @POST("/app/complete-application-list/{userId}")
  fun findMyCompleteApplication(@Path("userId") id:Long):Call<List<EventAppData>>

  // 유저id, 미수료 목록
  @POST("/app/incomplete-application-list/{userId}")
  fun findMyIncompleteApplication(@Path("userId") id:Long):Call<List<EventAppData>>

  // 이벤트id, 회원id >  스케쥴id, QR 이미지 주소, 행사날짜
  @POST("/app/qr-image/{eventId}/{userId}")
  fun findQRImageList(@Path("eventId")eventId:Long, @Path("userId")userId:Long):Call<List<Map<String, Any>>>

  // 유저id > 신청한 행사 미수료 내역 중 오늘 기준 행사 첫 번째 날이 가장 빠른 것

  //

  // QR 스캔 후 정보 전달 성공:2, 실패:1
  fun insertQRCheck(eventId:Long, scheduleId:Long, userId:Long):Call<Int>



  ////////// notice
  // 공지사항만 연결해서 레트로핏 되는지 확인해보기
  fun findNoticeList():Call<List<NoticeData>>


}