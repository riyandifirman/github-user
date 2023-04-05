package com.riyandifirman.githubuser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.riyandifirman.githubuser.settings.SettingsPreferences
import kotlinx.coroutines.launch

class SettingsViewModel(private val pref : SettingsPreferences) : ViewModel() {

    fun getTheme() = pref.getTheme().asLiveData()

    fun setTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            pref.setTheme(isDarkMode)
        }
    }

    class Factory(private val pref: SettingsPreferences) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = SettingsViewModel(pref) as T
    }
}