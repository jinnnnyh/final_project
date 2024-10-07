package com.fullstack405.bitcfinalprojectkotlin.interfaces

import com.fullstack405.bitcfinalprojectkotlin.data.AttendInfoData
import com.fullstack405.bitcfinalprojectkotlin.data.EventData
import com.fullstack405.bitcfinalprojectkotlin.data.EventScheduleData
import com.fullstack405.bitcfinalprojectkotlin.data.NoticeData
import com.fullstack405.bitcfinalprojectkotlin.data.TempData
import com.fullstack405.bitcfinalprojectkotlin.data.UserAttendData
import com.fullstack405.bitcfinalprojectkotlin.data.UserData
import retrofit2.Call
import retrofit2.http.GET

interface Interface {

  @GET("/temp")
  fun tempDatas(): Call<List<TempData>>

  ///////////// user
  // 유저 리스트
  fun findUserList():Call<List<UserData>>

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
  fun findEventList(accept:Char):Call<List<EventData>>

  // 이벤트 항목 1개 불러오기
  fun findEventId(id:Long):Call<EventData>


  /////////// attend
  // 추가
  fun insertAttend(data:AttendInfoData):Call<AttendInfoData>

  // userid 신청 목록 조회 >> 이거로 제목까지 끌어와지는지 확인
  // 서버에서 제목이랑 날짜 같이 넘겨주는걸 이걸로 받아지나
  fun findAttendList(id:Long):Call<List<UserAttendData>>

  // 유저id, 참석여부 'Y' 인 목록 조회
  fun findFinalAttendList(id:Long, attend:Char):Call<List<AttendInfoData>>

  // 이벤트id 스케줄 리스트 불러오기
  fun findScheduleList(id:Long):Call<List<EventScheduleData>>

  
  ////////// notice
  // 공지사항만 연결해서 레트로핏 되는지 확인해보기
  fun findNoticeList():Call<List<NoticeData>>


}