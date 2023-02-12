package com.example.workshopapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workshopapp.databinding.ActivityLoginRegisterBinding

class LoginRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}