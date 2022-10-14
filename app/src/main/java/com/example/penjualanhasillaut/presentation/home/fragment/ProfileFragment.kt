package com.example.penjualanhasillaut.presentation.home.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.penjualanhasillaut.R
import com.example.penjualanhasillaut.constant.MESSAGE.STATUS_ERROR
import com.example.penjualanhasillaut.constant.SESSION.ID
import com.example.penjualanhasillaut.data.dto.AuthLoginResponse
import com.example.penjualanhasillaut.databinding.FragmentProfileBinding
import com.example.penjualanhasillaut.presentation.home.viewmodel.ProfileViewModel
import com.example.penjualanhasillaut.presentation.start.StartActivity
import com.example.penjualanhasillaut.utils.Result
import com.example.penjualanhasillaut.utils.SessionManager
import com.example.penjualanhasillaut.utils.removeView
import com.example.penjualanhasillaut.utils.showView
import com.example.penjualanhasillaut.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding as FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels()

    private lateinit var sessionManager: SessionManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInstance(view)
        super.onViewCreated(binding.root, savedInstanceState)
        initArgument()
        initLaunch()
        initView()
    }

    private fun initView() {
        binding.ivLogout.setOnClickListener {
            sessionManager.logout()
            startActivity(Intent(requireContext(), StartActivity::class.java))
            requireActivity().finishAffinity()
        }
    }

    private fun initArgument() {
        arguments?.getInt(ID)?.let { id ->
            viewModel.setUsers(id)
        }
    }

    private fun initLaunch() {
        observerUsersResult?.let {
            viewModel.getUsers().observe(viewLifecycleOwner, it)
        }
    }

    private var observerUsersResult: Observer<Result<AuthLoginResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when (result) {
                        is Result.Loading -> {
                            binding.pbLoading.showView()
                        }
                        is Result.Success -> {
                            binding.pbLoading.removeView()
                            result.data?.dataLogin?.let { data ->
                                with(binding) {
                                    tvName.text = data.name
                                    tvEmail.text = data.email
                                    tvAddress.text = data.address
                                    tvStatus.text = data.status
                                    tvPhone.text = data.numberPhone
                                }
                            }

                        }
                        is Result.Error -> {
                            binding.pbLoading.removeView()
                            result.data?.message?.let { message ->
                                snackbar(
                                    binding.root,
                                    message,
                                    STATUS_ERROR
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initInstance(view: View) {
        _binding = FragmentProfileBinding.bind(view)
        sessionManager = SessionManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}