package com.example.penjualanhasillaut.presentation.register.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.penjualanhasillaut.R
import com.example.penjualanhasillaut.constant.MESSAGE
import com.example.penjualanhasillaut.constant.MESSAGE.STATUS_ERROR
import com.example.penjualanhasillaut.constant.MESSAGE.STATUS_SUCCESS
import com.example.penjualanhasillaut.constant.PASSINGDATA.REGISTER_DATA
import com.example.penjualanhasillaut.data.dto.AuthRegisterResponse
import com.example.penjualanhasillaut.databinding.FragmentStepTwoBinding
import com.example.penjualanhasillaut.presentation.login.activity.LoginActivity
import com.example.penjualanhasillaut.presentation.register.viewmodel.StepViewModel
import com.example.penjualanhasillaut.presentation.start.StartActivity
import com.example.penjualanhasillaut.utils.Result
import com.example.penjualanhasillaut.utils.convertHtml
import com.example.penjualanhasillaut.utils.setOnClickListenerWithDebounce
import com.example.penjualanhasillaut.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StepTwoFragment : Fragment(R.layout.fragment_step_two) {

    private var _binding: FragmentStepTwoBinding? = null

    private val binding get() = _binding as FragmentStepTwoBinding

    private val viewModel: StepViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initInstance(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLaunch(view)
        initView()
    }

    private fun initLaunch(view: View) {
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiEvent.collectLatest { message ->
                    snackbar(view, message, STATUS_ERROR)
                    }
                }
            }
        }

        observerLoginResult?.let {
            viewModel.getRegister().observe(viewLifecycleOwner, it)
        }
    }

    private fun initView() {

        binding.cbAgree.convertHtml(R.string.register_agree)

        val name = arguments?.getBundle(REGISTER_DATA)?.getString("name")

        val email = arguments?.getBundle(REGISTER_DATA)?.getString("email")

        val address = arguments?.getBundle(REGISTER_DATA)?.getString("address")

        val status = arguments?.getBundle(REGISTER_DATA)?.getString("status")

        binding.cbAgree.setOnCheckedChangeListener { _, isChecked ->
            binding.btnSave.isEnabled = isChecked
        }

        binding.btnSave.setOnClickListenerWithDebounce {
            val numberPhone = binding.etPhone.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()
            viewModel.onStepTwoValidation(
                name.toString(), email.toString(), address.toString(), status.toString(), numberPhone, password, confirmPassword
            )
        }

        binding.tvSignIn.setOnClickListenerWithDebounce {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finishAffinity()
        }
    }

    private var observerLoginResult: Observer<Result<AuthRegisterResponse>>? = Observer { result ->
        lifecycleScope.launchWhenCreated {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when (result) {
                        is Result.Loading -> {
                            binding.pbLoading.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.pbLoading.visibility = View.GONE
                            result.data?.message?.let { message ->
                                snackbar(binding.root, message, STATUS_SUCCESS)
                                delay(2000)
                                toStart()
                            }
                        }
                        is Result.Error -> {
                            binding.pbLoading.visibility = View.GONE
                            result.message?.let { message ->
                                snackbar(binding.root, message, MESSAGE.STATUS_ERROR)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun toStart() {
        startActivity(Intent(requireContext(), StartActivity::class.java))
        requireActivity().finishAffinity()
    }

    private fun initInstance(inflater: LayoutInflater): View {
        _binding = FragmentStepTwoBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}