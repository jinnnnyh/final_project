package com.fullstack405.bitcfinalprojectkotlin.data

data class UserUpcomingEventData(
    val eventId:Long?,
    val eventTitle:String?,
    val eventDate:String?,
    val eventComp:Char?,
    val startTime:String?,
    val endTime:String?
)
