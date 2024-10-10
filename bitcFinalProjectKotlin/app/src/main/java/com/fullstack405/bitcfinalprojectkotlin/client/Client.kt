package com.fullstack405.bitcfinalprojectkotlin.client

import com.fullstack405.bitcfinalprojectkotlin.interfaces.Interface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {
  val retrofit: Interface = Retrofit.Builder()
  .baseUrl("http://10.100.105.205:8080")
  .addConverterFactory(GsonConverterFactory.create())
  .build()
  .create(Interface::class.java)

}
