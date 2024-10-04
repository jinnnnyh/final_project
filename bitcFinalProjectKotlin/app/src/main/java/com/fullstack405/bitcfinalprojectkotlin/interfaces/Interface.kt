package com.fullstack405.bitcfinalprojectkotlin.interfaces

import android.media.metrics.Event
import com.fullstack405.bitcfinalprojectkotlin.data.AttendData
import com.fullstack405.bitcfinalprojectkotlin.data.EventData
import com.fullstack405.bitcfinalprojectkotlin.data.NoticeData
import com.fullstack405.bitcfinalprojectkotlin.data.PhotoData
import com.fullstack405.bitcfinalprojectkotlin.data.TempData
import com.fullstack405.bitcfinalprojectkotlin.data.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Interface {

  @GET("/temp")
  fun tempDatas(): Call<List<TempData>>

  ///////////// user
  fun findUserList():Call<List<UserData>>

  // 추가
  fun insetUser(data:UserData):Call<UserData>

  // ID중복 여부/ 중복 있으면 T
  fun CheckedId(id:String):Call<Boolean>


  /////////// event
  // 승인된 이벤트 리스트
  fun findEventList(accept:Char):Call<List<EventData>>

  // 이벤트 항목 1개 불러오기
  fun findEventId(id:Long):Call<EventData>


  /////////// attend
  // 추가
  fun insertAttend(data:AttendData):Call<AttendData>

  // 행사id 예비참석자 목록 조회
  fun findAttendList(id:Long):Call<List<AttendData>>

  // 행사id, 참석여부 'Y' 인 목록 조회
  fun findFinalAttendList(id:Long, attend:Char):Call<List<AttendData>>


  ////////// notice
  fun findNotiList():Call<List<NoticeData>>


}