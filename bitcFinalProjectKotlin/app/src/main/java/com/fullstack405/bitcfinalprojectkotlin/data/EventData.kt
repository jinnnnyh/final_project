package com.fullstack405.bitcfinalprojectkotlin.data

import java.util.Date

data class EventData(val evnetId: Long,
                     val userId:Long,
                     val eventTitle:String, // 제목
                     val eventContent:String?, // 내용
                     val visibleDate:String, // 게시일
                     val eventPoster:String?,
                    val eventState:Char)
