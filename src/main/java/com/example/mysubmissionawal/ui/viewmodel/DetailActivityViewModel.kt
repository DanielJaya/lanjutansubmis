package com.example.mysubmissionawal.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysubmissionawal.data.response.DetailResponse
import com.example.mysubmissionawal.data.retrofit.ApiConfig
import com.example.mysubmissionawal.database.fav
import com.example.mysubmissionawal.repository.FavRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivityViewModel(application: Application) : ViewModel() {

    private val mFavRepository: FavRepository = FavRepository(application)

    private val _detailUser = MutableLiveData<DetailResponse?>()
    val detailUser: LiveData<DetailResponse?> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                    _isFavorite.value = mFavRepository.isFavoriteUser(username)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun toggleFavorite(fav: fav, onSuccess: () -> Unit) {
        viewModelScope.launch {
            mFavRepository.toggleFavorite(fav)
            onSuccess()
            _isFavorite.value = mFavRepository.isFavoriteUser(fav.username)
        }
    }

    companion object {
        private const val TAG = "DetailActivityViewModel"
    }
}