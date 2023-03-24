package com.riyandifirman.githubuser.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riyandifirman.githubuser.R
import com.riyandifirman.githubuser.adapter.UserAdapter
import com.riyandifirman.githubuser.databinding.FragmentFollowsBinding
import com.riyandifirman.githubuser.viewmodel.FollowersViewModel

class FollowersFragment : Fragment(R.layout.fragment_follows) {

    // inisialisasi binding di fragment
    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: UserAdapter
    private lateinit var username: String
    private lateinit var viewModel: FollowersViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // mengambil data dari detail user activity
        username = arguments?.getString(DetailUserActivity.EXTRA_USERNAME).toString()

        // menginisialisasi binding
        _binding = FragmentFollowsBinding.bind(view)

        // menginisialisasi adapter
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        // menginisialisasi recycle view
        binding.apply {
            recycleView.layoutManager = LinearLayoutManager(activity)
            recycleView.setHasFixedSize(true)
            recycleView.adapter = adapter
        }

        showLoading(true)

        // menginisialisasi view model
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        // mengirimkan data username ke view model
        viewModel.setFollowers(username)
        // mengambil data followers dari view model
        viewModel.getFollowers().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setData(it)
                showLoading(false)
            }
        }
    }

    // fungsi untuk menampilkan progress bar
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            // jika isLoading true maka progress bar akan muncul
            binding.progressBar.visibility = View.VISIBLE
        } else {
            // jika isLoading false maka progress bar akan hilang
            binding.progressBar.visibility = View.GONE
        }
    }

    // fungsi untuk menghapus binding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}