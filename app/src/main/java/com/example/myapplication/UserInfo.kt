package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class UserInfo(@SerializedName("id") val id: Int,
                    @SerializedName("login") val name: String,
                    @SerializedName("avatar_url") val imageUrl: String)