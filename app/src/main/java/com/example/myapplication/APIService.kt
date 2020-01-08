package com.example.myapplication

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("/users")
    fun call(@Query("since") page: Int,
             @Query("per_page") pre_page: Int): Call<List<UserInfo>>
}