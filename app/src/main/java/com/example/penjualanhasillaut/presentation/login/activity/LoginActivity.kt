package com.example.penjualanhasillaut.presentation.login.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.penjualanhasillaut.constant.MESSAGE.STATUS_ERROR
import com.example.penjualanhasillaut.constant.SESSION.ACCESS_TOKEN
import com.example.penjualanhasillaut.constant.SESSION.ADDRESS
import com.example.penjualanhasillaut.constant.SESSION.EMAIL
import com.example.penjualanhasillaut.constant.SESSION.ID
import com.example.penjualanhasillaut.constant.SESSION.NAME
import com.example.penjualanhasillaut.constant.SESSION.NUMBER_PHONE
import com.example.penjualanhasillaut.constant.SESSION.STATUS
import com.example.penjualanhasillaut.data.dto.AuthLoginResponse
import com.example.penjualanhasillaut.data.dto.DataLogin
import com.example.penjualanhasillaut.databinding.ActivityLoginBinding
import com.example.penjualanhasillaut.presentation.home.activity.HomeActivity
import com.example.penjualanhasillaut.presentation.login.viewmodel.LoginViewModel
import com.example.penjualanhasillaut.presentation.register.activity.RegisterActivity
import com.example.penjualanhasillaut.presentation.start.StartActivity
import com.example.penjualanhasillaut.utils.Result
import com.example.penjualanhasillaut.utils.SessionManager
import com.example.penjualanhasillaut.utils.removeView
import com.example.penjualanhasillaut.utils.setOnClickListenerWithDebounce
import com.example.penjualanhasillaut.utils.showView
import com.example.penjualanhasillaut.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        initInstance()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initLaunch()
        initView()
    }

    private fun initLaunch() {
        observerLoginResult?.let {
            viewModel.getLogin().observe(this, it)
        }
    }

    private var observerLoginResult: Observer<Result<AuthLoginResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when(result) {
                        is Result.Loading -> {
                            binding.pbLoading.showView()
                        }
                        is Result.Success -> {
                            binding.pbLoading.removeView()
                            result.data?.dataLogin?.let { data ->
                                val id = data.id
                                val name = data.name
                                val email = data.email
                                val address = data.address
                                val status = data.status
                                val numberPhone = data.numberPhone
                                val token = data.token
                                sessionManager.createAuthSession(id, name, email, address, status, numberPhone, token)
                                toHome(data)
                            }
                        }
                        is Result.Error -> {
                            binding.pbLoading.removeView()
                            result.message?.let { message ->
                                snackbar(binding.root, message, STATUS_ERROR)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun toHome(data: DataLogin) {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        finishAffinity()
    }

    private fun initView() {
        binding.btnSignIn.setOnClickListenerWithDebounce {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.validation(email, password)
        }

        binding.tvCreate.setOnClickListenerWithDebounce {
            startActivity(Intent(this, RegisterActivity::class.java))
            finishAffinity()
        }

        binding.btnBack.setOnClickListenerWithDebounce {
            startActivity(Intent(this, StartActivity::class.java))
            finishAffinity()
        }
    }

    private fun initInstance() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        sessionManager = SessionManager(this)
    }
}