package com.riyandifirman.githubuser.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.riyandifirman.githubuser.R
import com.riyandifirman.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}