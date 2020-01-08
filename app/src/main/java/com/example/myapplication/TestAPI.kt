package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

/**
 * After I investigate Github api pagination, I find out that I set "page" keyword at
 * Link: https://api.github.com/users and it doesn't work.
 * It's my fault to choose this api request.
 * After investigating, I use "since" keyword for pagination,
 * "since" keyword will help us start from which ID
 *
 * p.s. in https://api.github.com/users request
 * ID will be an ascending list, and it is not continuous.
 * So I will remember the last ID plus 1, and next pagination will use the sinceID to query.
 */
class TestAPI {
    val userListLiveData: MutableLiveData<MutableList<UserInfo>> = MutableLiveData()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    private var retrofitCall : Call<List<UserInfo>>? = null
    private var sinceID = 0

    companion object {
        const val PER_PAGE = 20
    }

    fun callAPI() {
        retrofitCall = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
            .call( sinceID, PER_PAGE)

        retrofitCall?.enqueue(object : Callback<List<UserInfo>> {
            override fun onFailure(call: Call<List<UserInfo>>, t: Throwable) {
                call.cancel()
                errorMessageLiveData.value = t.message
            }

            override fun onResponse(
                call: Call<List<UserInfo>>,
                response: Response<List<UserInfo>>
            ) {
                response.body()?.size?.apply {
                    sinceID = response.body()?.get(this-1)?.id ?: -1 + 1
                }
                userListLiveData.value = response.body()?.toMutableList()

            }

        })
    }

    fun cancelApi() {
        retrofitCall?.cancel()
    }
}