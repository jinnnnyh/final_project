package com.fullstack405.bitcfinalprojectkotlin.interfaces

import com.fullstack405.bitcfinalprojectkotlin.data.TempData
import retrofit2.Call
import retrofit2.http.GET

interface TempInterface {
  @GET("/temp")
  fun tempDatas(): Call<List<TempData>>
}