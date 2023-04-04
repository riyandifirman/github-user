package com.riyandifirman.githubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riyandifirman.githubuser.R
import com.riyandifirman.githubuser.User
import com.riyandifirman.githubuser.adapter.UserAdapter
import com.riyandifirman.githubuser.databinding.ActivityFavoriteBinding
import com.riyandifirman.githubuser.favorite.FavoriteUser
import com.riyandifirman.githubuser.ui.detail.DetailUserActivity
import com.riyandifirman.githubuser.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            // fungsi untuk menangani ketika item di klik
            override fun onItemClicked(data: User) {
                // panggil DetailUserActivity dengan membawa data user
                val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                intent.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                intent.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatar_url)
                startActivity(intent)
            }
        })

        binding.apply {
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.setHasFixedSize(true)
            rvFavorite.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                adapter.setData(list)
            }
        }
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUser = ArrayList<User>()
        for (user in users) {
            val userItem = User(
                user.login,
                user.id,
                user.avatar_url
            )
            listUser.add(userItem)
        }
        return listUser
    }
}