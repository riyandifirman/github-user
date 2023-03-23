package com.riyandifirman.githubuser.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riyandifirman.githubuser.ApiConfig
import com.riyandifirman.githubuser.User
import com.riyandifirman.githubuser.response.GithubResponse
import com.riyandifirman.githubuser.response.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    companion object {
        private const val ERROR = "Failure"
        private const val USERNAME = "riyandi"
    }

    init {
        setUser(USERNAME)
    }

    val _listUser = MutableLiveData<ArrayList<User>>()
    val listUser: LiveData<ArrayList<User>> = _listUser

    fun setUser(query: String) {
        val client = ApiConfig.getApiService().getUsers(USERNAME)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(call: Call<GithubResponse>, response: Response<GithubResponse>) {
                if (response.isSuccessful) {
                    _listUser.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                Log.d(ERROR, t.message.toString())
            }
        })
    }

    fun getUser(): LiveData<ArrayList<User>> = listUser

    fun getSearchUser(query: String) {
        // Inisiasi Retrofit
        val client = ApiConfig.getApiService().getSearchData(query)
        client.enqueue(object : Callback<SearchResponse> {
            // Jika berhasil
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
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
}