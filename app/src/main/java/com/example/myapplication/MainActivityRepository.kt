package com.example.myapplication

import androidx.lifecycle.MutableLiveData

class MainActivityRepository(private val dataSource: DataSource) {

    val userInfoList = dataSource.userInfoListResponse
    val errorMessage = dataSource.errorMessage

    fun requestData() {
        dataSource.requestData()
    }

    fun release() {
        dataSource.cancelAPI()
    }

    interface DataSource {
        val userInfoListResponse: MutableLiveData<MutableList<UserInfo>>
        val errorMessage: MutableLiveData<String>
        fun requestData()
        fun cancelAPI()
    }
}