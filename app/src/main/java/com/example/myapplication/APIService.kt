package com.example.myapplication

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("/users")
    fun call(): Call<JsonArray>
}