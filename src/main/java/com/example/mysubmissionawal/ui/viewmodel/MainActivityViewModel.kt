package com.example.mysubmissionawal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysubmissionawal.data.response.GitResponse
import com.example.mysubmissionawal.data.response.ItemsItem
import com.example.mysubmissionawal.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userReviews = MutableLiveData<List<ItemsItem>>()
    val userReviews: LiveData<List<ItemsItem>> = _userReviews

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun findUsers(gitId: String) {
        _isLoading.value = true
        ApiConfig.getApiService().getUsers(gitId)
            .enqueue(object : Callback<GitResponse> {
                override fun onResponse(
                    call: Call<GitResponse>,
                    response: Response<GitResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _userReviews.value = responseBody.items
                        }
                    } else {
                        _errorMessage.value = response.message()
                    }
                }

                override fun onFailure(call: Call<GitResponse>, t: Throwable) {
                    _isLoading.value = false
                    _errorMessage.value = t.message
                }
            })
    }

    fun searchUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(username)
        client.enqueue(object : Callback<GitResponse> {
            override fun onResponse(
                call: Call<GitResponse>,
                response: Response<GitResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val totalCount = response.body()?.totalCount!!
                    _isEmpty.value = totalCount < 1
                    _userReviews.value = response.body()?.items!!
                } else {
                    _errorMessage.value = response.message()
                }
            }

            override fun onFailure(call: Call<GitResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = t.message.toString()
            }
        })
    }
}