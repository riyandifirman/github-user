package com.riyandifirman.githubuser.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riyandifirman.githubuser.R
import com.riyandifirman.githubuser.User
import com.riyandifirman.githubuser.adapter.UserAdapter
import com.riyandifirman.githubuser.databinding.ActivityMainBinding
import com.riyandifirman.githubuser.settings.SettingsPreferences
import com.riyandifirman.githubuser.ui.detail.DetailUserActivity
import com.riyandifirman.githubuser.ui.favorite.FavoriteActivity
import com.riyandifirman.githubuser.ui.setting.SettingActivity
import com.riyandifirman.githubuser.viewmodel.MainViewModel
import com.riyandifirman.githubuser.viewmodel.SettingsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        // set on item click callback untuk adapter
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            // fungsi untuk menangani ketika item di klik
            override fun onItemClicked(data: User) {
                // panggil DetailUserActivity dengan membawa data user
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                intent.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                intent.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatar_url)
                startActivity(intent)
            }
        })

        // inisialisasi MainViewModel
        viewModel = ViewModelProvider(this, MainViewModel.Factory(SettingsPreferences(this))).get(MainViewModel::class.java)
//        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        viewModel.getTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // inisialisasi recycle view
        binding.apply {
            recycleView.layoutManager = LinearLayoutManager(this@MainActivity)
            recycleView.setHasFixedSize(true)
            recycleView.adapter = adapter
        }

        // observe data user dari MainViewModel
        viewModel.getUser().observe(this){
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

    // fungsi untuk menampilkan option menu search
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        // inisialisasi search manager dan search view
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        // set hint dan listener untuk search view
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                // fungsi untuk menangani ketika tombol search di klik
                override fun onQueryTextSubmit(query: String): Boolean {
                    // panggil method getSearchData() dari MainViewModel untuk mendapatkan data user dari github api dengan query yang diinputkan
                    viewModel.getSearchUser(query)
                    showLoading(true)

                    // hilangkan keyboard
                    searchView.clearFocus()
                    return true
                }

                // fungsi untuk menangani ketika text pada search view berubah
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        // fungsi untuk menangani ketika tombol close di klik
        return super.onCreateOptionsMenu(menu)
    }

    // fungsi untuk menangani ketika item di option menu favorit di klik
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.settings -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}