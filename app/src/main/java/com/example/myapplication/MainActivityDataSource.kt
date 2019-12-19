package com.example.myapplication

class MainActivityDataSource : MainActivityRepository.DataSource {
    private val api = TestAPI()
    override val userInfoListResponse = api.userListLiveData
    override val errorMessage = api.errorMessageLiveData


    override fun requestData() {
        api.callAPI()
    }

    override fun cancelAPI() {
        api.cancelApi()
    }

}