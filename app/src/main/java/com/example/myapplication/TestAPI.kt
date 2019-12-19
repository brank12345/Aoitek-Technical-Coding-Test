package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class TestAPI {
    val userListLiveData: MutableLiveData<MutableList<UserInfo>> = MutableLiveData()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    var retrofitCall : Call<JsonArray>? = null

    fun callAPI() {
        retrofitCall = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
            ?.call()

        retrofitCall?.enqueue(object : Callback<JsonArray> {
                override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                    // fail
                    call.cancel()
                    errorMessageLiveData.value = t.message
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
                }
            })
    }

    fun cancelApi() {
        retrofitCall?.cancel()
    }
}