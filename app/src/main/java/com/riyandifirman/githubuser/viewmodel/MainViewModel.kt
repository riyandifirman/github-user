package com.riyandifirman.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.riyandifirman.githubuser.ApiConfig
import com.riyandifirman.githubuser.User
import com.riyandifirman.githubuser.response.GithubResponse
import com.riyandifirman.githubuser.response.SearchResponse
import com.riyandifirman.githubuser.settings.SettingsPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val preferences: SettingsPreferences) : ViewModel() {

    // companion object digunakan untuk membuat variabel yang dapat diakses tanpa harus membuat objek
    companion object {
        private const val ERROR = "Failure"
        private const val USERNAME = "riyandi"
    }

    // fungsi yang akan dijalankan pertama kali ketika kelas ini diinisiasi
    init {
        setUser(USERNAME)
    }

    val _listUser = MutableLiveData<ArrayList<User>>()
    val listUser: LiveData<ArrayList<User>> = _listUser

    // fungsi untuk mengambil data dari API
    fun setUser(query: String) {
        // memanggil fungsi getUsers pada ApiConfig untuk mengambil data dengan parameter USERNAME
        val client = ApiConfig.getApiService().getUsers(USERNAME)
        client.enqueue(object : Callback<GithubResponse> {
            // Jika berhasil
            override fun onResponse(call: Call<GithubResponse>, response: Response<GithubResponse>) {
                if (response.isSuccessful) {
                    // mengisi data ke dalam _listUser dengan data yang didapat dari API
                    _listUser.postValue(response.body()?.items)
                }
            }

            // Jika gagal
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                Log.d(ERROR, t.message.toString())
            }
        })
    }

    // fungsi untuk mengembalikan data
    fun getUser(): LiveData<ArrayList<User>> = listUser

    // fungsi untuk mengambil data dari API
    fun getSearchUser(query: String) {
        // memanggil fungsi getSearchData pada ApiConfig untuk mengambil data dengan parameter query
        val client = ApiConfig.getApiService().getSearchData(query)
        client.enqueue(object : Callback<SearchResponse> {
            // Jika berhasil
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    // mengisi data ke dalam _listUser dengan data yang didapat dari API
                    val listUser = response.body()?.items
                    if (listUser != null) {
                        _listUser.postValue(listUser as ArrayList<User>?)
                    }
                }
            }

            // Jika gagal
            override fun onFailure(call: retrofit2.Call<SearchResponse>, t: Throwable) {
                Log.e("MainViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getTheme() = preferences.getTheme().asLiveData()

    class Factory(private val preferences: SettingsPreferences) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(preferences) as T
    }
}