package com.riyandifirman.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riyandifirman.githubuser.ApiConfig
import com.riyandifirman.githubuser.response.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {
    val _user = MutableLiveData<DetailUserResponse>()
    val user: LiveData<DetailUserResponse> = _user

    // fungsi untuk mengambil data dari API
    fun setDetailUser(username: String) {
        // memanggil fungsi getDetailUser pada ApiConfig untuk mengambil data dengan parameter username
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            // jika berhasil
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    // mengisi data ke dalam _user dengan data yang didapat dari API
                    _user.postValue(response.body())
                }
            }

            // jika gagal
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.d("ERROR", t.message.toString())
            }
        })
    }

    // fungsi untuk mengembalikan data
    fun getDetailUser(): LiveData<DetailUserResponse> = user
}