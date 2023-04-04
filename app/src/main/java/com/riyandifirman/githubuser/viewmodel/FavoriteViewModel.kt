package com.riyandifirman.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.riyandifirman.githubuser.favorite.FavoriteUser
import com.riyandifirman.githubuser.favorite.FavoriteUserDao
import com.riyandifirman.githubuser.favorite.UserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDao: FavoriteUserDao?
    private var userDatabase: UserDatabase?

    init {
        userDatabase = UserDatabase.getInstance(application)
        userDao = userDatabase?.favoriteUserDao()
    }

    // fungsi get favorite user
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }
}