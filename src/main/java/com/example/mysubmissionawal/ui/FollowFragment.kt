package com.example.mysubmissionawal.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissionawal.data.response.ItemsItem
import com.example.mysubmissionawal.databinding.FragmentFollowBinding
import com.example.mysubmissionawal.ui.viewmodel.FollowFragmentViewModel

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var viewModel: FollowFragmentViewModel
    private var username: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FollowFragmentViewModel::class.java)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        username = arguments?.getString(ARG_USERNAME) ?: ""

        val layoutManager = LinearLayoutManager(context)
        binding.rvUser.layoutManager = layoutManager

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }
        viewModel.isEmpty.observe(viewLifecycleOwner) { isEmpty ->
            binding.tvEmpty.isVisible = isEmpty
        }
        if (index == 1) {
            if (viewModel.listFollowers.value == null) {
                viewModel.getFollowers(username)
            }
            viewModel.listFollowers.observe(viewLifecycleOwner) { followers ->
                followers?.let { setAdapter(it) }
            }
        } else {
            if (viewModel.listFollowings.value == null) {
                viewModel.getFollowings(username)
            }
            viewModel.listFollowings.observe(viewLifecycleOwner) { followings ->
                followings?.let { setAdapter(it) }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun setAdapter(users: List<ItemsItem>) {
        val adapter = ReviewAdapter()
        adapter.submitList(users)
        binding.rvUser.adapter = adapter
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = "username"
    }
}
