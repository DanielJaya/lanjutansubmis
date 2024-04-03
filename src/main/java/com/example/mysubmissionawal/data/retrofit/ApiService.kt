package com.example.mysubmissionawal.data.retrofit

import com.example.mysubmissionawal.data.response.DetailResponse
import com.example.mysubmissionawal.data.response.GitResponse
import com.example.mysubmissionawal.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") username: String,
    ): Call<GitResponse>

    @GET("users/{username}")
    fun getDetailUser
                (@Path("username") username: String
    ): Call<DetailResponse>

    @GET("search/users")
    fun searchUser(
        @Query("q") username: String,
    ): Call<GitResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String,
    ): Call<List<ItemsItem>>
}