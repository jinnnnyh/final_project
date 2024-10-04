package com.fullstack405.bitcfinalprojectkotlin.data

import java.util.Date

data class EventData(val evnetId: Long, val userId:Long,
                     val eventTitle:String,
                     val eventDate:String, val uploadDate:String,
                     val eventContent:String?,
                     val eventAccept:Char, val eventState:Char,
                     val eventPoster:String?)
