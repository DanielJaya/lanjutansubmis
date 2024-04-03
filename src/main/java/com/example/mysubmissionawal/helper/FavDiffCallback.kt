package com.example.mysubmissionawal.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.mysubmissionawal.database.fav

class FavDiffCallback(
    private val oldFavList: List<fav>,
    private val newFavList: List<fav>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavList.size

    override fun getNewListSize(): Int = newFavList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavList[oldItemPosition].username == newFavList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFav = oldFavList[oldItemPosition]
        val newFav = newFavList[newItemPosition]

        return oldFav.username == newFav.username && oldFav.avatarUrl == newFav.avatarUrl && oldFav.isFavorite == newFav.isFavorite
    }
}