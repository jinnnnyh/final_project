package com.fullstack405.bitcfinalprojectkotlin.data

data class AdminUpcomingEventData(val eventId:Long,
                                  val eventTitle:String,
                                  val isRegistrationOpen:Char,
                                  val eventDate:String,
                                  val startTime:String,
                                  val endTime:String)
