package com.fullstack405.bitcfinalprojectkotlin.data

data class QRscanData(
    val eventTitle:String,
    val eventDate:String,
    val startTime:String,
    val endTime:String,
    val name:String,
    val userPhone:String,
    val checkInTime:String?,
    val checkoutTime:String?)
