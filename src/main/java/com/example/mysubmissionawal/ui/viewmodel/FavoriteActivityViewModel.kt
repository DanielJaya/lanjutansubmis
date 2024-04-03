package com.example.mysubmissionawal.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mysubmissionawal.database.fav
import com.example.mysubmissionawal.repository.FavRepository

class FavoriteActivityViewModel(application : Application) : ViewModel() {
    private val mFavRepository : FavRepository = FavRepository(application)

    fun getAllFavs() : LiveData<List<fav>> = mFavRepository.getAllFavs()
}
