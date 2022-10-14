package com.example.penjualanhasillaut.presentation.register.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.penjualanhasillaut.R
import com.example.penjualanhasillaut.constant.MESSAGE.STATUS_ERROR
import com.example.penjualanhasillaut.constant.PASSINGDATA.REGISTER_DATA
import com.example.penjualanhasillaut.constant.STATUS.KONSUMEN
import com.example.penjualanhasillaut.constant.STATUS.PENYALUR
import com.example.penjualanhasillaut.databinding.FragmentStepOneBinding
import com.example.penjualanhasillaut.presentation.register.viewmodel.StepViewModel
import com.example.penjualanhasillaut.utils.setOnClickListenerWithDebounce
import com.example.penjualanhasillaut.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StepOneFragment : Fragment(R.layout.fragment_step_one) {

    private var _binding: FragmentStepOneBinding? = null

    private val binding get() = _binding as FragmentStepOneBinding

    private val viewModel: StepViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStepOneBinding.inflate(inflater)
        return binding.root
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
                        snackbar(binding.root, message, STATUS_ERROR)
                    }
                }
            }
        }
    }

    private fun initView() {
        binding.btnNext.setOnClickListenerWithDebounce {
            val name = binding.etNama.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val address = binding.etAlamat.text.toString().trim()
            val checkRbStatus = binding.rgStatus.checkedRadioButtonId
            if (checkRbStatus <= 0) {
                snackbar(binding.root, "Field tidak boleh kosong", STATUS_ERROR)
                return@setOnClickListenerWithDebounce
            }
            val rb: RadioButton = requireActivity().findViewById(checkRbStatus)
            val status = if(rb.text.toString() == PENYALUR) {
                PENYALUR
            } else {
                KONSUMEN
            }
            val bundle = Bundle().apply {
                putString("name", name)
                putString("email", email)
                putString("address", address)
                putString("status", status)
            }

            viewModel.onStepOneValidation(
                name, email, address
            ) {
                findNavController().navigate(R.id.action_stepOneFragment_to_stepTwoFragment, bundleOf(REGISTER_DATA to bundle))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}