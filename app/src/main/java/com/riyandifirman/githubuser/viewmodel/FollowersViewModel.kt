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

    // fungsi untuk mengambil data dari API
    fun setFollowers(username : String) {
        // memanggil fungsi getFollowerUser pada ApiConfig untuk mengambil data dengan parameter username
        val client = ApiConfig.getApiService().getFollowerUser(username)
        client.enqueue(object : Callback<ArrayList<User>> {
            // jika berhasil
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) {
                    // mengisi data ke dalam _listFollowers dengan data yang didapat dari API
                    _listFollowers.postValue(response.body())
                }
            }

            // jika gagal
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("ERROR", t.message.toString())
            }
        })
    }

    // fungsi untuk mengembalikan data
    fun getFollowers(): LiveData<ArrayList<User>> = listFollowers
}