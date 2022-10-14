package com.example.penjualanhasillaut.presentation.home.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.penjualanhasillaut.R
import com.example.penjualanhasillaut.constant.MESSAGE
import com.example.penjualanhasillaut.constant.SESSION.ID
import com.example.penjualanhasillaut.data.dto.GetKeranjangResponse
import com.example.penjualanhasillaut.data.dto.SearchResponse
import com.example.penjualanhasillaut.databinding.FragmentProductBinding
import com.example.penjualanhasillaut.domain.adapter.search.SearchAdapter
import com.example.penjualanhasillaut.presentation.detail.activity.DetailActivity
import com.example.penjualanhasillaut.presentation.home.viewmodel.SearchViewModel
import com.example.penjualanhasillaut.presentation.keranjang.activity.KeranjangActivity
import com.example.penjualanhasillaut.presentation.keranjang.viewmodel.KeranjangViewModel
import com.example.penjualanhasillaut.utils.GridCardMargin
import com.example.penjualanhasillaut.utils.Result
import com.example.penjualanhasillaut.utils.SessionManager
import com.example.penjualanhasillaut.utils.formatCurrency
import com.example.penjualanhasillaut.utils.removeView
import com.example.penjualanhasillaut.utils.setOnClickListenerWithDebounce
import com.example.penjualanhasillaut.utils.showView
import com.example.penjualanhasillaut.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : Fragment(R.layout.fragment_product) {

    private var _binding: FragmentProductBinding? = null

    private val binding get() = _binding as FragmentProductBinding

    private val viewModel: SearchViewModel by viewModels()

    private lateinit var searchAdapter: SearchAdapter

    private val keranjangViewModel: KeranjangViewModel by viewModels()

    private val amounQtytItem = mutableListOf<Int>()

    private val amounPrizetItem = mutableListOf<Int>()

    private lateinit var sessionManager: SessionManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInstance(view)
        super.onViewCreated(binding.root, savedInstanceState)
        initAdapter()
        initLaunch()
        initView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchSearch("")
    }

    private fun initAdapter() {
        searchAdapter.let { adapter ->
            binding.rvFish.apply {
                this.adapter = adapter
                this.layoutManager = GridLayoutManager(requireContext(), 2)
                this.addItemDecoration(GridCardMargin(16))
                ViewCompat.setNestedScrollingEnabled(this, true)
            }
            adapter.setOnItemClickListener { id ->
                startActivity(Intent(requireContext(), DetailActivity::class.java).putExtra(ID, id))
            }
        }
    }

    private fun initLaunch() {
        observerSearchResult?.let {
            viewModel.getSearch().observe(viewLifecycleOwner, it)
        }

        observerGetKeranjang?.let {
            keranjangViewModel.getKeranjang().observe(viewLifecycleOwner, it)
        }
    }

    private var observerGetKeranjang: Observer<Result<GetKeranjangResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when(result) {
                        is Result.Loading -> {
                        }
                        is Result.Success -> {
                            result.data?.dataGetKeranjangItem?.let { item ->
                                if (item.isEmpty()) {
                                    if (sessionManager.status == "Penyalur") {
                                        binding.icLayoutKeranjang.root.removeView()
                                        return@let
                                    }
                                    binding.icLayoutKeranjang.root.removeView()
                                    return@let
                                }
                                amounQtytItem.clear()
                                amounPrizetItem.clear()
                                item.map {
                                    if (sessionManager.name != it.payerName) {
                                        binding.icLayoutKeranjang.root.removeView()
                                        return@map
                                    }
                                    binding.icLayoutKeranjang.root.showView()
                                }

                                with(binding.icLayoutKeranjang) {
                                    item.map{ item ->
                                        amounQtytItem.add(item.qty.toInt())
                                        amounPrizetItem.add(item.amount.toInt())
                                        with(binding.icLayoutKeranjang) {
                                            tvTotalItem.text = String.format("%s Product", amounQtytItem.size)
                                            tvPrize.text = String.format("Rp.%s", amounPrizetItem.sum().toString().formatCurrency())
                                        }
                                    }
                                }
                            }
                        }
                        is Result.Error -> {
                        }
                    }
                }
            }
        }
    }

    private var observerSearchResult: Observer<Result<SearchResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when(result) {
                        is Result.Loading -> {
                            binding.pbLoading.showView()
                        }
                        is Result.Success -> {
                            binding.pbLoading.removeView()
                            searchAdapter.differ.submitList(result.data?.dataItem)
                        }
                        is Result.Error -> {
                            binding.pbLoading.removeView()
                            result.data?.message?.let { message ->
                                snackbar(binding.root, message, MESSAGE.STATUS_ERROR)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initView() {
        binding.tfSearch.setStartIconOnClickListener {
            val search = binding.etSearch.text.toString().trim()
            viewModel.fetchSearch(search)
        }
        binding.icLayoutKeranjang.root.setOnClickListenerWithDebounce {
            startActivity(Intent(requireContext(), KeranjangActivity::class.java))
        }
    }

    private fun initInstance(view: View) {
        _binding = FragmentProductBinding.bind(view)
        searchAdapter = SearchAdapter.instance()
        sessionManager = SessionManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}