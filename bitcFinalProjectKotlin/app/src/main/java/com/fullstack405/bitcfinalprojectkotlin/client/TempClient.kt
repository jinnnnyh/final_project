package com.fullstack405.bitcfinalprojectkotlin.client

import com.fullstack405.bitcfinalprojectkotlin.interfaces.TempInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TempClient {
  val retrofit: TempInterface = Retrofit.Builder()
    .baseUrl("http://10.100.105.194:8811/travel/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(TempInterface::class.java)
}