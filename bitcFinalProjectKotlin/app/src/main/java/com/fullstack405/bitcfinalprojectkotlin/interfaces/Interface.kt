package com.fullstack405.bitcfinalprojectkotlin.interfaces

import com.fullstack405.bitcfinalprojectkotlin.data.AttendInfoData
import com.fullstack405.bitcfinalprojectkotlin.data.EventAppData
import com.fullstack405.bitcfinalprojectkotlin.data.EventDetailData
import com.fullstack405.bitcfinalprojectkotlin.data.EventListData
import com.fullstack405.bitcfinalprojectkotlin.data.EventScheduleData
import com.fullstack405.bitcfinalprojectkotlin.data.NoticeData
import com.fullstack405.bitcfinalprojectkotlin.data.QRdata
import com.fullstack405.bitcfinalprojectkotlin.data.TempData
import com.fullstack405.bitcfinalprojectkotlin.data.UserAttendData
import com.fullstack405.bitcfinalprojectkotlin.data.UserData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Interface {

  @GET("/temp")
  fun tempDatas(): Call<List<TempData>>

  ///////////// user
  // 유저 리스트
  fun findUserList():Call<List<UserData>>

  // 로그인 유저 정보 확인
  // 로그인 아이디, 비번 넣어서 보내기
  @POST("/login")
  fun loginUser(@Body data:UserData):Call<UserData>

  // 유저 1명 정보
  fun findUserId(id:Long):Call<UserData>

  // 추가
  fun insetUser(data:UserData):Call<UserData>

  // 수정
  fun updateUser(id: Long, data:UserData):Call<UserData>

  // ID중복 여부/ 중복 있으면 T
  fun CheckedId(id:String):Call<Boolean>


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

  ////////// notice
  // 공지사항만 연결해서 레트로핏 되는지 확인해보기
  fun findNoticeList():Call<List<NoticeData>>


}