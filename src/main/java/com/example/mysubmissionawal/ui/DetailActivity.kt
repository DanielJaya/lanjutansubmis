package com.example.mysubmissionawal.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mysubmissionawal.R
import com.example.mysubmissionawal.databinding.ActivityDetailBinding
import com.example.mysubmissionawal.ui.viewmodel.DetailActivityViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(DetailActivityViewModel::class.java)

        val username = intent.getStringExtra(EXTRA_LOGIN)

        if(viewModel.detailUser.value==null) viewModel.getDetailUser(username.toString())

        viewModel.detailUser.observe(this){
            it?.let{
                with(binding){
                    followers.text = it.followers.toString()
                    following.text = it.following.toString()
                    nama.text = it.name.toString()
                    usernama.text = it.login.toString()
                    Glide.with(binding.root)
                        .load(it.avatarUrl)
                        .into(binding.profilpic)
                        .clearOnDetach()
                }
            }
        }

        viewModel.isLoading.observe(this){
            binding.progressBar.isVisible = it
        }

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        sectionsPagerAdapter.username = username.toString() // Mengatur username untuk diteruskan ke Fragment

        val viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun isFavoriteUser(favorite: Boolean) {
        if (favorite) {
            binding.fabAdd.setImageResource(R.drawable.ic_fav)
        } else {
            binding.fabAdd.setImageResource(R.drawable.ic_nofav)
        }
    }

    companion object{
        const val EXTRA_LOGIN="login"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following,
        )
    }
}