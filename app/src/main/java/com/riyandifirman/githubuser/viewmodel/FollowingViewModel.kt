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

class FollowingViewModel : ViewModel() {
    var _listFollowing = MutableLiveData<ArrayList<User>>()
    val listFollowing: LiveData<ArrayList<User>> = _listFollowing

    // fungsi untuk mengambil data dari API
    fun setFollowing(username : String) {
        // memanggil fungsi getFollowingUser pada ApiConfig untuk mengambil data dengan parameter username
        val client = ApiConfig.getApiService().getFollowingUser(username)
        client.enqueue(object : Callback<ArrayList<User>> {
            // jika berhasil
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) {
                    // mengisi data ke dalam _listFollowing dengan data yang didapat dari API
                    _listFollowing.postValue(response.body())
                }
            }

            // jika gagal
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("ERROR", t.message.toString())
            }
        })
    }

    // fungsi untuk mengembalikan data
    fun getFollowing(): LiveData<ArrayList<User>> = listFollowing
}