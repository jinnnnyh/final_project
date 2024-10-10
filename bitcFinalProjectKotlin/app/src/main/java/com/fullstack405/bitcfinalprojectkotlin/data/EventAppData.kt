package com.fullstack405.bitcfinalprojectkotlin.data
// 신청자 내역
data class EventAppData(val eventId:Long,
                        val appId:Long,
                        val eventTitle:String,
                        val appDate:String, // 행사 수료 여부
                        val eventComp:Char
                        )
