package com.example.penjualanhasillaut.presentation.detail.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.penjualanhasillaut.R
import com.example.penjualanhasillaut.constant.MESSAGE.STATUS_ERROR
import com.example.penjualanhasillaut.constant.SESSION.ID
import com.example.penjualanhasillaut.constant.TRANSAKSI.ADDRESSTRANSAKSI
import com.example.penjualanhasillaut.constant.TRANSAKSI.AMONT
import com.example.penjualanhasillaut.constant.TRANSAKSI.DESCRIPTION
import com.example.penjualanhasillaut.constant.TRANSAKSI.EMAILTRANSAKSI
import com.example.penjualanhasillaut.constant.TRANSAKSI.IMAGE
import com.example.penjualanhasillaut.constant.TRANSAKSI.OWNERPRODUCT
import com.example.penjualanhasillaut.constant.TRANSAKSI.PRODUCTNAME
import com.example.penjualanhasillaut.constant.TRANSAKSI.QTY
import com.example.penjualanhasillaut.constant.TRANSAKSI.USERID
import com.example.penjualanhasillaut.data.dto.DataDetail
import com.example.penjualanhasillaut.data.dto.DetailResponse
import com.example.penjualanhasillaut.data.dto.GeneralResponse
import com.example.penjualanhasillaut.data.dto.GetTokenResponse
import com.example.penjualanhasillaut.databinding.ActivityDetailBinding
import com.example.penjualanhasillaut.presentation.detail.viewmodel.DetailViewModel
import com.example.penjualanhasillaut.presentation.home.activity.HomeActivity
import com.example.penjualanhasillaut.presentation.keranjang.activity.KeranjangActivity
import com.example.penjualanhasillaut.presentation.keranjang.viewmodel.KeranjangViewModel
import com.example.penjualanhasillaut.presentation.transaksi.activity.TransaksiActivity
import com.example.penjualanhasillaut.utils.Result
import com.example.penjualanhasillaut.utils.SessionManager
import com.example.penjualanhasillaut.utils.formatCurrency
import com.example.penjualanhasillaut.utils.loadImage
import com.example.penjualanhasillaut.utils.openWhatsApp
import com.example.penjualanhasillaut.utils.removeQuote
import com.example.penjualanhasillaut.utils.removeView
import com.example.penjualanhasillaut.utils.setOnClickListenerWithDebounce
import com.example.penjualanhasillaut.utils.showView
import com.example.penjualanhasillaut.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null

    private val binding get() = _binding as ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels()

    private val keranjangViewModel: KeranjangViewModel by viewModels()

    private lateinit var sessionManager: SessionManager

    private var count: Int = 0

    private var priceCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
        setContentView(binding.root)
        initLaunch()
        initBundle()
    }

    private fun initBundle() {
        intent.getIntExtra(ID, 0).let { id ->
            viewModel.setDetail(id)
        }
    }

    private fun initLaunch() {
        observerDetailResult?.let {
            viewModel.getDetail().observe(this, it)
        }

        observerSetKeranjang?.let {
            keranjangViewModel.setKeranjang().observe(this, it)
        }

        observerGetToken?.let {
            viewModel.getToken().observe(this, it)
        }
    }

    private var observerDetailResult: Observer<Result<DetailResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when (result) {
                        is Result.Loading -> {
                            binding.pbLoading.showView()
                        }
                        is Result.Success -> {
                            binding.pbLoading.removeView()
                            result.data?.dataDetail?.let { data ->
                                viewModel.fetchGetToken(data.userId.toInt())
                                with(binding) {
                                    ivImage.loadImage(data.image)
                                    conditionType(data.type)
                                    tvType.text = data.type
                                    tvProductName.text = data.productName.removeQuote()
                                    tvName.text = data.userName
                                    tvPrice.text = data.price.toString().formatCurrency()
                                    tvPiece.text = data.qty.toString().removeQuote()
                                    tvDescription.text = data.description.removeQuote()
                                    tvPhone.text = data.numberPhone.removeQuote()
                                    tvAddress.text = data.address.removeQuote()
                                    tvTotalPrice.text = priceCount.toString().formatCurrency()
                                    binding.tvPhone.setOnClickListenerWithDebounce {
                                        openWhatsApp(
                                            binding.root,
                                            this@DetailActivity,
                                            data.numberPhone
                                        )
                                    }
                                }
                                if (sessionManager.status == "Konsumen") {
                                    binding.btnSave.showView()
                                }
                                if (sessionManager.status == "Penyalur") {
                                    binding.btnSave.removeView()
                                }
                                onCount(data)
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

    private var observerSetKeranjang: Observer<Result<GeneralResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    when(result){
                        is Result.Loading -> {
                            binding.pbLoading.showView()
                        }
                        is Result.Success -> {
                            binding.pbLoading.removeView()
                            startActivity(Intent(this@DetailActivity, HomeActivity::class.java))
                        }
                        is Result.Error -> {
                            binding.pbLoading.removeView()
                        }
                    }
                }
            }
        }
    }

    private var observerGetToken: Observer<Result<GetTokenResponse>>? = Observer { result ->
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    when(result) {
                        is Result.Loading -> {
                            binding.pbLoading.showView()
                        }
                        is Result.Success -> {
                            binding.pbLoading.removeView()
                            result.data?.data?.deviceToken?.let { token ->
                                Log.e("TAG", "token Penjual: $token", )
                                sessionManager.setSellerDeviceToken(token.toString())
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

    private fun onCount(result: DataDetail) {
        with(binding) {
            result.let { item ->
                if (item.qty <= 0) {
                    btnSave.apply {
                        text = "SOLD OUT"
                        isEnabled = false
                    }
                    return@let
                }

                btnMinus.setOnClickListener {
                    if(count <= 0) {
                        snackbar(binding.root, "Pemesanan barang tidak boleh minus", STATUS_ERROR)
                        return@setOnClickListener
                    }

                    count--
                    priceCount -= item.price.toInt()
                    tvCount.text = count.toString()
                    tvTotalPrice.text = priceCount.toString().formatCurrency()
                }

                btnPlus.setOnClickListener {
                    if(count >= tvPiece.text.toString().toInt()) {
                        snackbar(binding.root, "Pemesanan barang tidak boleh melebihi stok", STATUS_ERROR)
                        return@setOnClickListener
                    }
                    count++
                    priceCount = (item.price * count).toInt()
                    tvCount.text = count.toString()
                    tvTotalPrice.text = priceCount.toString().formatCurrency()
                }

                btnSave.setOnClickListenerWithDebounce {
                    keranjangViewModel.fetchKeranjang(
                        id_product = result.id,
                        user_id = result.userId,
                        product_name = result.productName,
                        email = result.email,
                        address = result.address,
                        owner_product = result.userName,
                        amount = priceCount,
                        qty = count,
                        image = result.image,
                        description = result.description
                    )
                }
            }
        }
    }

    private fun conditionType(type: String) {
        if (type.removeQuote().contains("Premium")) {
            binding.cvType.setCardBackgroundColor(getColor(R.color.orangeBadge))
            return
        } else if (type.removeQuote().contains("Regular")) {
            binding.cvType.setCardBackgroundColor(getColor(R.color.dark))
            binding.ivType.visibility = View.GONE
            return
        }
    }

    private fun initInstance() {
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        sessionManager = SessionManager(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}