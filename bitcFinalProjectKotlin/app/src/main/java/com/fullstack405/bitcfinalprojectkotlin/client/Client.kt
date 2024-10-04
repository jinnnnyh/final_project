package com.fullstack405.bitcfinalprojectkotlin.client

import com.fullstack405.bitcfinalprojectkotlin.interfaces.Interface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {
//  val retrofit: Interface = Retrofit.Builder()
//  .baseUrl("http://10.100.105.205:8080")
//  .addConverterFactory(GsonConverterFactory.create())
//  .build()
//  .create(Interface::class.java)

  val user_retrofit: Interface = Retrofit.Builder()
    .baseUrl("http://10.100.105.205:8080/user")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(Interface::class.java)

  val signup_retrofit: Interface = Retrofit.Builder()
    .baseUrl("http://10.100.105.205:8080/signup")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(Interface::class.java)

  val event_retrofit: Interface = Retrofit.Builder()
    .baseUrl("http://10.100.105.205:8080/event")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(Interface::class.java)

  val photo_retrofit: Interface = Retrofit.Builder()
    .baseUrl("http://10.100.105.205:8080/photo")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(Interface::class.java)

  val attend_retrofit: Interface = Retrofit.Builder()
    .baseUrl("http://10.100.105.205:8080/attend")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(Interface::class.java)



}