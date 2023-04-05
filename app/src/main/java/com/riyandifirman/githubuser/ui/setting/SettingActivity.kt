package com.riyandifirman.githubuser.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.riyandifirman.githubuser.R
import com.riyandifirman.githubuser.databinding.ActivitySettingBinding
import com.riyandifirman.githubuser.settings.SettingsPreferences
import com.riyandifirman.githubuser.viewmodel.SettingsViewModel

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, SettingsViewModel.Factory(SettingsPreferences(this))).get(SettingsViewModel::class.java)

        viewModel.getTheme().observe(this) {
            if (it) {
                binding.switchTheme.text = getString(R.string.dark_mode)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                binding.switchTheme.text = getString(R.string.light_mode)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            binding.switchTheme.isChecked = it
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setTheme(isChecked)
        }
    }
}