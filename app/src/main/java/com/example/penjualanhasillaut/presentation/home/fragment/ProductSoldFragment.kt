package com.example.penjualanhasillaut.presentation.home.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.penjualanhasillaut.R
import com.example.penjualanhasillaut.constant.MESSAGE.STATUS_SUCCESS
import com.example.penjualanhasillaut.data.dto.GeneralResponse
import com.example.penjualanhasillaut.data.dto.TransaksiResponse
import com.example.penjualanhasillaut.databinding.FragmentProductSoldBinding
import com.example.penjualanhasillaut.domain.adapter.sold.SoldAdapter
import com.example.penjualanhasillaut.presentation.home.viewmodel.SoldViewModel
import com.example.penjualanhasillaut.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class ProductSoldFragment : Fragment(R.layout.fragment_product_sold) {

    private var _binding: FragmentProductSoldBinding? = null

    private val binding get() = _binding as FragmentProductSoldBinding

    private lateinit var soldAdapter: SoldAdapter

    private val soldViewModel: SoldViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductSoldBinding.inflate(inflater, container, false)
        soldAdapter = SoldAdapter.instance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(binding.root, savedInstanceState)
        initAdapter()
        iniLaunch()
    }

    private fun iniLaunch() {
        observerGetTransaksiSold?.let {
            soldViewModel.getSold().observe(viewLifecycleOwner, it)
        }
        observerDeleteTransaksi?.let {
            soldViewModel.getDeleteTransaksi().observe(viewLifecycleOwner, it)
        }
    }

    private var observerGetTransaksiSold: Observer<Result<TransaksiResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    when(result) {
                        is Result.Loading -> {
                            binding.pbLoading.showView()
                        }
                        is Result.Success -> {
                            binding.pbLoading.removeView()
                            result.data?.data?.let { item ->
                                if (item.isEmpty()) {
                                    binding.tvEmpty.showView()
                                    binding.rvProductSold.removeView()
                                }
                                binding.tvEmpty.removeView()
                                binding.rvProductSold.showView()
                                soldAdapter.differ.submitList(item)
                            }
                        }
                        is Result.Error -> {
                            binding.pbLoading.removeView()
                        }
                    }
                }
            }
        }
    }

    private var observerDeleteTransaksi: Observer<Result<GeneralResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when(result) {
                        is Result.Loading -> {
                            binding.pbLoading.showView()
                        }
                        is Result.Success -> {
                            binding.pbLoading.removeView()
                            result.data?.message?.let { message ->
                                snackbar(binding.root, message, STATUS_SUCCESS)
                                delay(1000)
                            }
                        }
                        is Result.Error -> {
                            binding.pbLoading.removeView()
                        }
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        soldAdapter.let { adapter ->
            binding.rvProductSold.apply {
                this.adapter = adapter
                this.layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(MarginItemDecorationVertical(16))
                ViewCompat.setNestedScrollingEnabled(this, true)
            }

            adapter.setOnItemClickListener { id ->
                soldViewModel.deleteTransaksi(id)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}