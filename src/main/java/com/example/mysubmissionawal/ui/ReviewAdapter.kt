package com.example.mysubmissionawal.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysubmissionawal.data.response.ItemsItem
import com.example.mysubmissionawal.databinding.ItemReviewBinding

class ReviewAdapter : ListAdapter<ItemsItem, ReviewAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var listener: ((ItemsItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int) : MyViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            listener?.invoke(user)
        }
    }

    class MyViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review : ItemsItem) {
            binding.tvItem.text = "${review.login}"

            Glide.with(binding.ivUser.context)
                .load(review.avatarUrl)
                .circleCrop()
                .into(binding.ivUser)
        }
    }

    fun setOnItemClickListener(listener: (ItemsItem) -> Unit) {
        this.listener = listener
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}