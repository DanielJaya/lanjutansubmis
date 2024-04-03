package com.example.mysubmissionawal.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mysubmissionawal.database.FavRoomDatabase
import com.example.mysubmissionawal.database.fav
import com.example.mysubmissionawal.database.favDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavRepository(application: Application) {
    private val mFavsDao: favDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavRoomDatabase.getDatabase(application)
        mFavsDao = db.favDao()
    }

    fun getAllFavs(): LiveData<List<fav>> = mFavsDao.getAllFavs()

    fun insert(fav: fav) {
        executorService.execute { mFavsDao.insert(fav) }
    }

    fun toggleFavorite(fav: fav) {
        executorService.execute {
            if (fav.isFavorite) {
                mFavsDao.delete(fav)
            } else {
                mFavsDao.insert(fav)
            }
        }
    }

    fun isFavoriteUser(username: String): Boolean {
        return mFavsDao.getFavByUsername(username) != null
    }
}
