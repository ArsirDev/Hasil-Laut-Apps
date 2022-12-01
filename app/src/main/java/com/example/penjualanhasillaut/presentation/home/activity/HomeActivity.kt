package com.example.penjualanhasillaut.presentation.home.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.penjualanhasillaut.R
import com.example.penjualanhasillaut.constant.SESSION
import com.example.penjualanhasillaut.constant.SESSION.ID
import com.example.penjualanhasillaut.constant.SESSION.STATUS
import com.example.penjualanhasillaut.constant.STATUS.KONSUMEN
import com.example.penjualanhasillaut.constant.STATUS.PENYALUR
import com.example.penjualanhasillaut.constant.TRANSAKSI
import com.example.penjualanhasillaut.databinding.ActivityHomeBinding
import com.example.penjualanhasillaut.presentation.home.fragment.InputProductFragment
import com.example.penjualanhasillaut.presentation.home.fragment.ProductFragment
import com.example.penjualanhasillaut.presentation.home.fragment.ProductSoldFragment
import com.example.penjualanhasillaut.presentation.home.fragment.ProfileFragment
import com.example.penjualanhasillaut.utils.SessionManager
import com.example.penjualanhasillaut.utils.replace
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null

    private val binding get() = _binding as ActivityHomeBinding

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInstance()
        setContentView(binding.root)
        initIntent()
    }

    private fun initIntent() {
        sessionManager.status?.let { status ->
            when(status) {
                PENYALUR -> {
                    initBottomNavigationComponent()
                }

                KONSUMEN -> {
                    binding.bottomNav.menu.removeItem(R.id.input_product)
                    binding.bottomNav.menu.removeItem(R.id.product_terjual)
                    initBottomNavigationComponent()
                }
            }
        }
    }

    private fun initBottomNavigationComponent() {
        with(binding) {
            val bundle = Bundle().apply {
                sessionManager.let {
                    putInt(ID, it.id)
                    putString(STATUS, it.status)
                }
            }

            bottomNav.setOnItemSelectedListener { item ->
                when(item.itemId){
                    R.id.product -> {
                        replace(
                            R.id.nav_host_fragment,
                            ProductFragment(),
                            addToBackstack = true
                        )
                    }

                    R.id.input_product -> {
                        replace(
                            R.id.nav_host_fragment,
                            InputProductFragment(),
                            addToBackstack = true
                        )
                    }

                    R.id.product_terjual -> {
                        replace(
                            R.id.nav_host_fragment,
                            ProductSoldFragment(),
                            addToBackstack = true
                        )
                    }

                    R.id.profile -> {
                        replace(
                            R.id.nav_host_fragment,
                            ProfileFragment(),
                            bundle = bundle,
                            addToBackstack = true
                        )
                    }
                }
                return@setOnItemSelectedListener true
            }
        }
    }

    private fun initInstance() {
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        sessionManager = SessionManager(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}