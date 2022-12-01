package com.example.penjualanhasillaut.presentation.transaksi.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.penjualanhasillaut.constant.MESSAGE.STATUS_ERROR
import com.example.penjualanhasillaut.constant.SESSION.ID
import com.example.penjualanhasillaut.constant.TRANSAKSI
import com.example.penjualanhasillaut.data.dto.InvoiceResponse
import com.example.penjualanhasillaut.databinding.ActivityTransaksiBinding
import com.example.penjualanhasillaut.presentation.home.activity.HomeActivity
import com.example.penjualanhasillaut.presentation.transaksi.viewmodel.TransaksiViewModel
import com.example.penjualanhasillaut.utils.Result
import com.example.penjualanhasillaut.utils.removeQuote
import com.example.penjualanhasillaut.utils.removeView
import com.example.penjualanhasillaut.utils.setOnClickListenerWithDebounce
import com.example.penjualanhasillaut.utils.showView
import com.example.penjualanhasillaut.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransaksiActivity : AppCompatActivity() {

    private var _binding: ActivityTransaksiBinding? = null

    private val binding get() = _binding as ActivityTransaksiBinding

    private val viewModel: TransaksiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
        setContentView(binding.root)
        initWebView()
        initIntent()
        initLaunch()
        initView()
    }

    private fun initIntent() {
        val id_product = intent.getIntExtra(ID, 0)
        val user_id = intent.getStringExtra(TRANSAKSI.USERID)
        val product_name = intent.getStringExtra(TRANSAKSI.PRODUCTNAME)
        val email = intent.getStringExtra(TRANSAKSI.EMAILTRANSAKSI)
        val address = intent.getStringExtra(TRANSAKSI.ADDRESSTRANSAKSI)
        val owner = intent.getStringExtra(TRANSAKSI.OWNERPRODUCT)
        val amount = intent.getIntExtra(TRANSAKSI.AMONT, 0)
        val qty = intent.getIntExtra(TRANSAKSI.QTY, 0)
        val total_item = intent.getIntExtra(TRANSAKSI.TOTAL_ITEM, 0)
        val image = intent.getStringExtra(TRANSAKSI.IMAGE)
        val descrip = intent.getStringExtra(TRANSAKSI.DESCRIPTION)

        viewModel.setTransaksi(
            id_product,
            user_id.toString(),
            product_name.toString().removeQuote(),
            email.toString().removeQuote(),
            address.toString().removeQuote(),
            owner.toString().removeQuote(),
            amount,
            qty,
            total_item,
            image.toString().removeQuote(),
            descrip.toString().removeQuote(),
        )
    }

    private fun initWebView() {
        with(binding) {
            val setting = wvTransaksi.settings
            setting.javaScriptEnabled = true
            setting.domStorageEnabled = true
            setting.allowContentAccess = true
            setting.useWideViewPort = true
            setting.loadsImagesAutomatically = true
            wvTransaksi.webViewClient = WebViewClient()
            wvTransaksi.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun initView() {
        with(binding) {
            btnBack.setOnClickListenerWithDebounce {
                onBackPressed()
            }
        }
    }

    private fun initLaunch() {
        observerTransaksiResult?.let {
            viewModel.getTransaksi().observe(this, it)
        }
    }

    private var observerTransaksiResult: Observer<Result<InvoiceResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when(result){
                        is Result.Loading ->{
                            binding.pbLoading.showView()
                        }
                        is Result.Success ->{
                            binding.pbLoading.removeView()
                            result.data?.dataInvoice?.let { data ->
                                binding.wvTransaksi.loadUrl(data.invoiceUrl)
                            }
                        }
                        is Result.Error ->{
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

    override fun onBackPressed() {
        if (binding.wvTransaksi.canGoBack()) {
            binding.wvTransaksi.goBack()
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
            super.onBackPressed()
        }
    }

    private fun initInstance() {
        _binding = ActivityTransaksiBinding.inflate(layoutInflater)
    }
}