package com.riyandifirman.githubuser.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riyandifirman.githubuser.R
import com.riyandifirman.githubuser.User
import com.riyandifirman.githubuser.UserAdapter
import com.riyandifirman.githubuser.databinding.ActivityMainBinding
import com.riyandifirman.githubuser.detail.DetailUserActivity

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                // panggil DetailUserActivity dengan membawa data user
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                startActivity(intent)
            }
        })

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

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

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            // jika isLoading true maka progress bar akan muncul
            binding.progressBar.visibility = View.VISIBLE
        } else {
            // jika isLoading false maka progress bar akan hilang
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

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
}