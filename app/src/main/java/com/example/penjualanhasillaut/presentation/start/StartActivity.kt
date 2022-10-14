package com.example.penjualanhasillaut.presentation.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.penjualanhasillaut.databinding.ActivityStartBinding
import com.example.penjualanhasillaut.presentation.login.activity.LoginActivity
import com.example.penjualanhasillaut.presentation.register.activity.RegisterActivity
import com.example.penjualanhasillaut.utils.setOnClickListenerWithDebounce

class StartActivity : AppCompatActivity() {

    private var _binding: ActivityStartBinding? = null

    private val binding get() = _binding as ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        with(binding) {
            btnSignIn.setOnClickListenerWithDebounce {
                startActivity(Intent(this@StartActivity, LoginActivity::class.java))
            }

            btnSingUp.setOnClickListenerWithDebounce {
                startActivity(Intent(this@StartActivity, RegisterActivity::class.java))
            }
        }
    }

    private fun initInstance() {
        _binding = ActivityStartBinding.inflate(layoutInflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}