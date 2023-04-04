package com.riyandifirman.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.riyandifirman.githubuser.R
import com.riyandifirman.githubuser.adapter.SectionsPagerAdapter
import com.riyandifirman.githubuser.databinding.ActivityDetailUserBinding
import com.riyandifirman.githubuser.viewmodel.DetailUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    // companion object digunakan untuk membuat variabel yang dapat diakses dari mana saja
    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // mengambil data dari intent yang dikirimkan dari MainActivity
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)

        // membuat bundle untuk mengirimkan data ke fragment
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        // menginisialisasi viewModel
        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        if (username != null) {
            viewModel.setDetailUser(username)
        }

        // memanggil fungsi getDetailUser() yang ada di viewModel untuk mengambil data dari API
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
                // memanggil fungsi showLoading() untuk menghilangkan progress bar
                showLoading(false)
            }
        }

        var isCheck = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.isFavorite(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        isCheck = true
                        binding.toggle.isChecked = true
                    } else {
                        isCheck = false
                        binding.toggle.isChecked = false
                    }
                }
            }
        }

        binding.toggle.setOnClickListener{
            isCheck = !isCheck
            if (isCheck) {
                if (username != null) {
                    if (avatar != null) {
                        viewModel.insertFavoriteUser(username, id, avatar)
                    }
                }
            } else {
                viewModel.deleteFavoriteUser(id)
            }
            binding.toggle.isChecked = isCheck
        }

        // menginisialisasi adapter untuk view pager
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, bundle)
        val viewPager: ViewPager = binding.viewPager
        // menghubungkan view pager dengan tab layout
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
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
}