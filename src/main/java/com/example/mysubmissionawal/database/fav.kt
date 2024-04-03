package com.example.mysubmissionawal.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize

data class fav (
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var avatarUrl: String? = null,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean
) : Parcelable

