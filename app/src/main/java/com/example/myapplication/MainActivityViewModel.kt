package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class MainActivityViewModel(private val repository: MainActivityRepository) : ViewModel() {

    private val userInfoList: MutableLiveData<MutableList<UserInfo>> = MutableLiveData()
    private val errorMessage: MutableLiveData<String> = MutableLiveData()

    private val userInfoListObserver: Observer<MutableList<UserInfo>> = Observer { data ->
        this@MainActivityViewModel.userInfoList.value = data
    }

    private val errorMessageObserver: Observer<String> = Observer { data ->
        this@MainActivityViewModel.errorMessage.value = data
    }

    init {
        repository.apply {
            userInfoList.observeForever(userInfoListObserver)

            errorMessage.observeForever(errorMessageObserver)
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
        repository.userInfoList.removeObserver(userInfoListObserver)
        repository.errorMessage.removeObserver(errorMessageObserver)
    }
}