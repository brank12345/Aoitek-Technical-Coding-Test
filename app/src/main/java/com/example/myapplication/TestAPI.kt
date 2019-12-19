package com.example.myapplication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class TestAPI {
    private val userListLiveData: MutableLiveData<MutableList<UserInfo>> = MutableLiveData()

    fun updateUserList(): MutableLiveData<MutableList<UserInfo>> {
        return userListLiveData
    }

    fun callAPI() {
        Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
            ?.call()
            ?.enqueue(object : Callback<JsonArray> {
                override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                    // fail
                    Log.d("QAQ", t.toString())
                }

                override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                    // success
                    val userList: MutableList<UserInfo> = mutableListOf()
                    response.body()?.forEach {
                        val name = it?.asJsonObject?.get("login")?.asString ?: ""
                        val imageUrl = it?.asJsonObject?.get("avatar_url")?.asString ?: ""
                        userList.add(UserInfo(name, imageUrl))
                    }

                    userListLiveData.value = userList
                    Log.d("QAQ", "$userList")
                }
            })
    }
}