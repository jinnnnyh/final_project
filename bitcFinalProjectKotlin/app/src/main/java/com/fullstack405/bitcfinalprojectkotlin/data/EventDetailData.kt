package com.fullstack405.bitcfinalprojectkotlin.data
// 서버에서 작성자 이름으로 넘겨줌
data class EventDetailData(val eventId:Long, val eventTitle:String, val eventContent:String?, val eventPoster:String?, val posterUserName:String)
