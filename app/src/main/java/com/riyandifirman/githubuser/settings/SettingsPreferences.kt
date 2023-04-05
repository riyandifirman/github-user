package com.riyandifirman.githubuser.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.prefDataStore by preferencesDataStore(name = "settings")

class SettingsPreferences(context: Context) {

    private val settingsDataStore = context.prefDataStore
    private val themeKey = booleanPreferencesKey("theme")

    fun getTheme(): Flow<Boolean> = settingsDataStore.data.map { preferences ->
        preferences[themeKey] ?: false
    }

    suspend fun setTheme(isDarkMode: Boolean) {
        settingsDataStore.edit { preferences ->
            preferences[themeKey] = isDarkMode
        }
    }
}