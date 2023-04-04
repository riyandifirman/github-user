package com.riyandifirman.githubuser.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riyandifirman.githubuser.ApiConfig
import com.riyandifirman.githubuser.favorite.FavoriteUser
import com.riyandifirman.githubuser.favorite.FavoriteUserDao
import com.riyandifirman.githubuser.favorite.UserDatabase
import com.riyandifirman.githubuser.response.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val _user = MutableLiveData<DetailUserResponse>()
    val user: LiveData<DetailUserResponse> = _user

    private var userDao: FavoriteUserDao?
    private var userDatabase: UserDatabase?

    init {
        userDatabase = UserDatabase.getInstance(application)
        userDao = userDatabase?.favoriteUserDao()
    }

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

    // fungsi untuk menambahkan data user favorit ke dalam database
    fun insertFavoriteUser(username: String, id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(username, id)
            userDao?.insertFavoriteUser(user)
        }
    }

    // fungsi untuk mengecek apakah user sudah ada di database atau belum
    suspend fun isFavorite(id: Int) = userDao?.isFavorite(id)

    // fungsi untuk menghapus data user favorit dari database
    fun deleteFavoriteUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteFavoriteUser(id)
        }
    }
}