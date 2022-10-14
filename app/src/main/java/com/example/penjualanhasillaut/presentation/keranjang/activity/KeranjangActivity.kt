package com.example.penjualanhasillaut.presentation.keranjang.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.penjualanhasillaut.constant.MESSAGE.STATUS_ERROR
import com.example.penjualanhasillaut.constant.SESSION
import com.example.penjualanhasillaut.constant.TRANSAKSI
import com.example.penjualanhasillaut.data.dto.DataGetKeranjangItem
import com.example.penjualanhasillaut.data.dto.GeneralResponse
import com.example.penjualanhasillaut.data.dto.GetKeranjangResponse
import com.example.penjualanhasillaut.databinding.ActivityKeranjangBinding
import com.example.penjualanhasillaut.presentation.keranjang.adapter.KeranjangAdapter
import com.example.penjualanhasillaut.presentation.keranjang.viewmodel.KeranjangViewModel
import com.example.penjualanhasillaut.presentation.transaksi.activity.TransaksiActivity
import com.example.penjualanhasillaut.utils.GridCardMargin
import com.example.penjualanhasillaut.utils.Result
import com.example.penjualanhasillaut.utils.formatCurrency
import com.example.penjualanhasillaut.utils.removeView
import com.example.penjualanhasillaut.utils.setOnClickListenerWithDebounce
import com.example.penjualanhasillaut.utils.showView
import com.example.penjualanhasillaut.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KeranjangActivity : AppCompatActivity() {

    private var _binding: ActivityKeranjangBinding? = null

    private val binding get() = _binding as ActivityKeranjangBinding

    private val viewModel: KeranjangViewModel by viewModels()

    private lateinit var keranjangAdapter: KeranjangAdapter

    private val amountItem = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
        setContentView(binding.root)
        initLaunch()
        initAdapter()
    }

    private fun initAdapter() {
        keranjangAdapter.let { adapter ->
            binding.rvCart.apply {
                this.adapter = adapter
                this.layoutManager = LinearLayoutManager(this@KeranjangActivity)
                this.addItemDecoration(GridCardMargin(16))
                ViewCompat.setNestedScrollingEnabled(this, true)
            }
        }
    }

    private fun initLaunch() {
        observerGetKeranjang?.let {
            viewModel.getKeranjang().observe(this, it)
        }
        obseverDeleteKeranjang?.let {
            viewModel.getDeleteKeranjang().observe(this, it)
        }
    }

    private var observerGetKeranjang: Observer<Result<GetKeranjangResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when(result) {
                        is Result.Loading -> {
                            binding.pbLoading.showView()
                        }
                        is Result.Success -> {
                            binding.pbLoading.removeView()
                            result.data?.dataGetKeranjangItem?.let { item ->
                                keranjangAdapter.differ.submitList(item)
                                initView(item)
                            }
                        }
                        is Result.Error -> {
                            binding.pbLoading.removeView()
                            result.message?.let { msg ->
                                snackbar(binding.root, msg, STATUS_ERROR)
                            } ?: result.data?.message?.let { msg ->
                                snackbar(binding.root, msg, STATUS_ERROR)
                            }
                        }
                    }
                }
            }
        }
    }

    private var obseverDeleteKeranjang: Observer<Result<GeneralResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    when(result){
                        is Result.Loading -> {
                        }
                        is Result.Success -> {
                        }
                        is Result.Error -> {
                        }
                    }
                }
            }
        }
    }

    private fun initView(item: List<DataGetKeranjangItem>) {
        item.map {
            amountItem.add(it.amount.toInt())
            binding.tvTotalPrize.text = String.format("TOTAL HARGA: Rp.%s.-", amountItem.sum().toString().formatCurrency())
        }

        item.map { result ->
            val bundle = Bundle().apply {
                putInt(SESSION.ID, result.id)
                putString(TRANSAKSI.USERID, result.userId)
                putString(TRANSAKSI.PRODUCTNAME, result.productName)
                putString(TRANSAKSI.EMAILTRANSAKSI, result.email)
                putString(TRANSAKSI.ADDRESSTRANSAKSI, result.address)
                putString(TRANSAKSI.OWNERPRODUCT, result.ownerProduct)
                putInt(TRANSAKSI.AMONT, amountItem.sum().toString().toInt())
                putInt(TRANSAKSI.QTY, item.size)
                putString(TRANSAKSI.IMAGE, result.image)
                putString(TRANSAKSI.DESCRIPTION, result.productName)
            }
            binding.btnBayar.setOnClickListenerWithDebounce {
                viewModel.fetchDeleteKeranjang()
                startActivity(Intent(this, TransaksiActivity::class.java).putExtras(bundle))
            }
        }
    }

    private fun initInstance() {
        _binding = ActivityKeranjangBinding.inflate(layoutInflater)
        keranjangAdapter = KeranjangAdapter.instance()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}