package com.fullstack405.bitcfinalprojectkotlin.data
// 이벤트 리스트 dto 데이터
data class EventListData(val eventId: Long,
                         val eventTitle:String,
                         val visibleDate:String,
                         val isRegistrationOpen:Char)
