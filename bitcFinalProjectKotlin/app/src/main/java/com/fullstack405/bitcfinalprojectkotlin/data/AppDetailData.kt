package com.fullstack405.bitcfinalprojectkotlin.data

data class AppDetailData(val eventId:Long,
                         val eventTitle:String,
                         val eventContent:String?,
                         val eventPoster:String?,
                         val posterUserName:String,
                         val visibleDate:String,
                         val schedules:List<Map<String, Any>>,
                         val complete:Char)
