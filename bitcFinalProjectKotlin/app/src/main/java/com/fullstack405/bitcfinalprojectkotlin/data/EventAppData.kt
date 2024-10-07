package com.fullstack405.bitcfinalprojectkotlin.data
// 신청자 내역
data class EventAppData(val appId:Long,
                        val userId:Long,
                        val evnetId:Long,
                        val eventComp:Char // 행사 수료 여부
                        )
