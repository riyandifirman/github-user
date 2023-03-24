package com.riyandifirman.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riyandifirman.githubuser.ApiConfig
import com.riyandifirman.githubuser.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    var _listFollowers = MutableLiveData<ArrayList<User>>()
    val listFollowers: LiveData<ArrayList<User>> = _listFollowers

    fun setFollowers(username : String) {
        val client = ApiConfig.getApiService().getFollowerUser(username)
        client.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) {
                    _listFollowers.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("ERROR", t.message.toString())
            }
        })
    }

    fun getFollowers(): LiveData<ArrayList<User>> = listFollowers
}