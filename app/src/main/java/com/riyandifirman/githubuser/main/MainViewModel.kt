package com.riyandifirman.githubuser.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riyandifirman.githubuser.ApiConfig
import com.riyandifirman.githubuser.User
import com.riyandifirman.githubuser.response.GithubResponse
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

    val listUser = MutableLiveData<ArrayList<User>>()

    fun setUser(query: String) {
        val client = ApiConfig.getApiService().getUsers(USERNAME)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(call: Call<GithubResponse>, response: Response<GithubResponse>) {
                if (response.isSuccessful) {
                    listUser.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                Log.d(ERROR, t.message.toString())
            }
        })
    }

    fun getUser(): LiveData<ArrayList<User>> = listUser
}