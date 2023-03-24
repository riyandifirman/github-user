package com.riyandifirman.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.riyandifirman.githubuser.adapter.SectionsPagerAdapter
import com.riyandifirman.githubuser.databinding.ActivityDetailUserBinding
import com.riyandifirman.githubuser.viewmodel.DetailUserViewModel

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailUserViewModel::class.java)
        if (username != null) {
            viewModel.setDetailUser(username)
        }
        viewModel.getDetailUser().observe(this) {
            if (it != null) {
                binding.apply {
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatarUrl)
                        .into(ivProfile)
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                }
                showLoading(false)
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, bundle)
        val viewPager: ViewPager = binding.viewPager
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            // jika isLoading true maka progress bar akan muncul
            binding.progressBar.visibility = View.VISIBLE
        } else {
            // jika isLoading false maka progress bar akan hilang
            binding.progressBar.visibility = View.GONE
        }
    }
}