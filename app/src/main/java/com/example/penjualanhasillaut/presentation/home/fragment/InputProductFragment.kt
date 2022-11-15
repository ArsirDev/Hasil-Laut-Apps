package com.example.penjualanhasillaut.presentation.home.fragment

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.text.format.Formatter
import android.view.View
import android.widget.RadioButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.penjualanhasillaut.R
import com.example.penjualanhasillaut.constant.MESSAGE.STATUS_ERROR
import com.example.penjualanhasillaut.constant.MESSAGE.STATUS_SUCCESS
import com.example.penjualanhasillaut.constant.STATUS
import com.example.penjualanhasillaut.data.dto.InputResponse
import com.example.penjualanhasillaut.databinding.FragmentInputProductBinding
import com.example.penjualanhasillaut.presentation.home.viewmodel.InputViewModel
import com.example.penjualanhasillaut.utils.Result
import com.example.penjualanhasillaut.utils.getFileFromContentUri
import com.example.penjualanhasillaut.utils.loadImage
import com.example.penjualanhasillaut.utils.removeView
import com.example.penjualanhasillaut.utils.setOnClickListenerWithDebounce
import com.example.penjualanhasillaut.utils.showView
import com.example.penjualanhasillaut.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class InputProductFragment : Fragment(R.layout.fragment_input_product) {

    private var _binding: FragmentInputProductBinding? = null

    private val binding get() = _binding as FragmentInputProductBinding

    private val viewModel: InputViewModel by viewModels()

    private var permissionRequest: ActivityResultLauncher<Array<String>>? = null

    private lateinit var imageLauncher: ActivityResultLauncher<String?>

    private var newImage: File? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInstance(view)
        super.onViewCreated(binding.root, savedInstanceState)
        initCheckPermission()
        initImageActivityResultLauncher()
        initLaunch()
        initView()
    }

    private fun initImageActivityResultLauncher() {
        permissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            it?.entries?.forEach { permission ->
            }
        }

        imageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            result?.let { uri ->
                val file = requireContext().getFileFromContentUri(uri)
                newImage = file

                val fileFormatted = Formatter.formatShortFileSize(requireContext(), file.length())
                val fileSizeNum = fileFormatted.replace(" ", "").dropLast(2).toDouble()
                val fileSizeUnit = fileFormatted.replace(" ", "").takeLast(2)

                if (fileSizeNum > 2.0 && fileSizeUnit.contains("mb", true)) {
                    snackbar(binding.root, "Gambar Melebihi 2Mb", STATUS_ERROR)
                    newImage = null
                } else {
                    binding.ivProduct.loadImage(uri.toString(), DiskCacheStrategy.RESOURCE)
                }
            }
        }
    }

    private fun initCheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET)
            permissionRequest?.launch(permissions)
        }
    }

    private fun initLaunch() {
        observerInputResult?.let {
            viewModel.getInput().observe(viewLifecycleOwner, it)
        }

        lifecycleScope.launchWhenCreated {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiEvent.collectLatest { message ->
                        snackbar(binding.root, message, STATUS_ERROR)
                    }
                }
            }
        }
    }

    private var observerInputResult: Observer<Result<InputResponse>>? = Observer { result ->
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
                            }
                        }
                        is Result.Error -> {
                            binding.pbLoading.removeView()
                            result.data?.message?.let { message ->
                                snackbar(binding.root, message, STATUS_ERROR)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initView() {
        with(binding) {
            cardView.setOnClickListenerWithDebounce {
                try {
                    imageLauncher.launch("image/*")
                } catch (e: Exception) {
                    initCheckPermission()
                }
            }

            binding.btnSave.setOnClickListenerWithDebounce {
                val productName = binding.etNameProduct.text.toString().trim()
                val qty = binding.etQty.text.toString().trim()
                val price = binding.etPrice.text.toString().trim()
                val checkRbType = binding.rgProductType.checkedRadioButtonId
                val image = newImage
                val description = binding.etDescription.text.toString().trim()

                onValidation(
                    productName,
                    qty,
                    price,
                    checkRbType,
                    image,
                    description
                )
            }
        }
    }

    private fun onValidation(
        productName: String,
        qty: String,
        price: String,
        checkRbType: Int,
        image: File?,
        description: String
    ) {
        if (
            productName.isEmpty() || qty.isEmpty() || price.isEmpty() || checkRbType <= 0 || image == null || description.isEmpty()
        ) {
            snackbar(binding.root, "Field tidak boleh kosong", STATUS_ERROR)
            return
        }

        val rb: RadioButton = requireActivity().findViewById(checkRbType)
        val type = if(rb.text.toString() == STATUS.PREMIUM) {
            STATUS.PREMIUM
        } else {
            STATUS.REGULAR
        }

        viewModel.setInput(
            productName,
            qty,
            price,
            type,
            image,
            description
        )
    }

    private fun initInstance(view: View) {
        _binding = FragmentInputProductBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}