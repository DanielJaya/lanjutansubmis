package com.example.mysubmissionawal.database

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface favDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(fav: fav)

    @Delete
    fun delete(fav: fav)

    @Query("SELECT * FROM fav")
    fun getAllFavs(): LiveData<List<fav>>

    @Query("SELECT * FROM fav WHERE username = :username")
    fun getFavByUsername(username: String): fav?
}