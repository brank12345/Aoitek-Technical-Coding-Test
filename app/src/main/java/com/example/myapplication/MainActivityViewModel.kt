package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel(private val repository: MainActivityRepository) : ViewModel() {

    private val userInfoList: MutableLiveData<MutableList<UserInfo>> = MutableLiveData()
    private val errorMessage: MutableLiveData<String> = MutableLiveData()

    init {
        repository.apply {
            userInfoList.observeForever { data->
                this@MainActivityViewModel.userInfoList.value = data
            }

            errorMessage.observeForever { data ->
                this@MainActivityViewModel.errorMessage.value = data
            }
        }
    }

    /**
     * Get function start
     */
    fun getUserInfoList(): MutableLiveData<MutableList<UserInfo>> {
        return userInfoList
    }

    fun getErrorMessage(): MutableLiveData<String> {
        return errorMessage
    }

    /**
     * Get function end
     */

    fun requestData() {
        repository.requestData()
    }

    override fun onCleared() {
        super.onCleared()
        repository.release()
    }
}