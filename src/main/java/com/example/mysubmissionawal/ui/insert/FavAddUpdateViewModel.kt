package com.example.mysubmissionawal.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.mysubmissionawal.database.fav
import com.example.mysubmissionawal.repository.FavRepository

class FavAddUpdateViewModel(application: Application) : ViewModel() {
    private val mFavRepository: FavRepository = FavRepository(application)

    fun insert(fav: fav) {
        mFavRepository.insert(fav)
    }

    fun isUserFavorited(username: String): Boolean {
        return mFavRepository.isFavoriteUser(username)
    }
}