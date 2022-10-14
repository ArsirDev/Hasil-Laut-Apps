package com.example.penjualanhasillaut.presentation.register.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.penjualanhasillaut.R
import com.example.penjualanhasillaut.databinding.ActivityRegisterBinding
import com.example.penjualanhasillaut.presentation.start.StartActivity
import com.example.penjualanhasillaut.utils.setOnClickListenerWithDebounce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null

    private val binding get() = _binding as ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.btnBack.setOnClickListenerWithDebounce {
            startActivity(Intent(this, StartActivity::class.java))
            finishAffinity()
        }
    }

    private fun initInstance() {
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
    }
}