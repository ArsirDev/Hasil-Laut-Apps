package com.example.penjualanhasillaut.presentation.keranjang.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.penjualanhasillaut.constant.MESSAGE.STATUS_ERROR
import com.example.penjualanhasillaut.constant.MESSAGE.STATUS_SUCCESS
import com.example.penjualanhasillaut.constant.SESSION
import com.example.penjualanhasillaut.constant.TRANSAKSI
import com.example.penjualanhasillaut.data.dto.DataGetKeranjangItem
import com.example.penjualanhasillaut.data.dto.GeneralResponse
import com.example.penjualanhasillaut.data.dto.GetKeranjangResponse
import com.example.penjualanhasillaut.databinding.ActivityKeranjangBinding
import com.example.penjualanhasillaut.presentation.detail.activity.DetailActivity
import com.example.penjualanhasillaut.presentation.home.activity.HomeActivity
import com.example.penjualanhasillaut.presentation.keranjang.adapter.KeranjangAdapter
import com.example.penjualanhasillaut.presentation.keranjang.viewmodel.KeranjangViewModel
import com.example.penjualanhasillaut.presentation.transaksi.activity.TransaksiActivity
import com.example.penjualanhasillaut.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KeranjangActivity : AppCompatActivity() {

    private var _binding: ActivityKeranjangBinding? = null

    private val binding get() = _binding as ActivityKeranjangBinding

    private val viewModel: KeranjangViewModel by viewModels()

    private lateinit var keranjangAdapter: KeranjangAdapter

    private lateinit var sessionManager: SessionManager

    private val amountItem = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
        setContentView(binding.root)
        initViews()
        initLaunch()
        initAdapter()
    }

    private fun initViews() {
        binding.ivBack.setOnClickListenerWithDebounce {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }
    }

    private fun initAdapter() {
        keranjangAdapter.let { adapter ->
            binding.rvCart.apply {
                this.adapter = adapter
                this.layoutManager = LinearLayoutManager(this@KeranjangActivity)
                this.addItemDecoration(GridCardMargin(16))
                ViewCompat.setNestedScrollingEnabled(this, true)
            }
            adapter.setOnItemClickListener { id ->
                viewModel.fetchDeleteKeranjangById(id)
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
        observerDeleteKeranjangById?.let {
            viewModel.getDeleteKeranjangById().observe(this, it)
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

    private var observerDeleteKeranjangById: Observer<Result<GeneralResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when(result){
                        is Result.Loading -> {
                            binding.pbLoading.showView()
                        }
                        is Result.Success -> {
                            binding.pbLoading.removeView()
                            result.message?.let { msg ->
                                snackbar(binding.root, msg, STATUS_SUCCESS)
                            } ?: result.data?.message?.let { msg ->
                                snackbar(binding.root, msg, STATUS_SUCCESS)
                            }
                            delay(900)
                            finish()
                            startActivity(intent)
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
                putInt(TRANSAKSI.TOTAL_ITEM, result.qty.toInt())
                putString(TRANSAKSI.IMAGE, result.image)
                putString(TRANSAKSI.DESCRIPTION, result.description)
            }
            binding.btnBayar.setOnClickListenerWithDebounce {
                if (item.isEmpty()) {
                    binding.btnBayar.isEnabled = false
                    return@setOnClickListenerWithDebounce
                }
                viewModel.fetchDeleteKeranjang()
                viewModel.sendPushNotification(sessionManager.seller_device_token ?: "", "${result.productName} telah dibelih oleh ${result.payerName}")
                startActivity(Intent(this, TransaksiActivity::class.java).putExtras(bundle))
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        startActivity(Intent(this, HomeActivity::class.java))
        finishAffinity()
        super.onBackPressed()
    }

    private fun initInstance() {
        _binding = ActivityKeranjangBinding.inflate(layoutInflater)
        keranjangAdapter = KeranjangAdapter.instance()
        sessionManager = SessionManager(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}