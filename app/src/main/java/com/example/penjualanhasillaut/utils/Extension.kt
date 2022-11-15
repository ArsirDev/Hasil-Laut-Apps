package com.example.penjualanhasillaut.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.penjualanhasillaut.R
import com.example.penjualanhasillaut.constant.MESSAGE.STATUS_ERROR
import com.example.penjualanhasillaut.constant.MESSAGE.STATUS_SUCCESS
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.DecimalFormat
import android.widget.Toast

import android.content.pm.PackageManager

import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import android.os.SystemClock
import android.widget.TextView

fun CompoundButton.convertHtml(@SuppressLint("SupportAnnotationUsage") @StringRes text: Int) {
    this.text = HtmlCompat.fromHtml(this.context.getString(text), HtmlCompat.FROM_HTML_MODE_LEGACY)
}

fun at(at: String) = "Bearer $at"

fun snackbar(view: View, message: String, type: String, duration: Int = Snackbar.LENGTH_SHORT) {
    when(type) {
        STATUS_SUCCESS -> {
            Snackbar.make(view, message, duration).apply {
                this.view.setBackgroundColor(ContextCompat.getColor(this.context, R.color.green_color))
            }.show()
        }
        STATUS_ERROR -> {
            Snackbar.make(view, message, duration).apply {
                this.view.setBackgroundColor(ContextCompat.getColor(this.context, R.color.red_color))
            }.show()
        }
    }
}

fun ImageView.loadImage(imageUrl: String, cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.NONE) {
    Glide.with(this.context)
        .load(imageUrl)
        .override(480, 320)
        .timeout(20000)
        .diskCacheStrategy(cacheStrategy)
        .error(ContextCompat.getDrawable(this.context, R.drawable.ic_broken_image_black))
        .placeholder(ContextCompat.getDrawable(this.context, R.drawable.ic_image_black))
        .into(this)
}

fun AppCompatActivity.replace(containerViewId: Int, fragment: Fragment?, bundle: Bundle? = null, addToBackstack: Boolean = false) {
    supportFragmentManager.commit {
        fragment?.let {
            if (!fragment.isAdded) {
                fragment.arguments = bundle
                replace(containerViewId, fragment, fragment.tag)
                if (addToBackstack) addToBackStack(fragment.tag)
            }
        }
    }
}

@Throws(IOException::class)
private fun copy(source: InputStream, target: OutputStream) {
    val buf = ByteArray(8192)
    var length: Int
    while (source.read(buf).also { length = it } > 0) {
        target.write(buf, 0, length)
    }
}

fun Context.getFileFromContentUri(contentUri: Uri): File {
    val fileExtension = getFileExtension(this, contentUri)
    val fileName = "temp_file" + if (fileExtension != null) ".$fileExtension" else ""

    val tempFile = File(this.cacheDir, fileName)
    tempFile.createNewFile()

    var oStream: FileOutputStream? = null
    var inputStream: InputStream? = null

    try {
        oStream = FileOutputStream(tempFile)
        inputStream = this.contentResolver.openInputStream(contentUri)

        inputStream?.let { copy(inputStream, oStream) }
        oStream.flush()
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {

        inputStream?.close()
        oStream?.close()
    }

    return tempFile
}

private fun getFileExtension(context: Context, uri: Uri): String? {
    val fileType: String? = context.contentResolver.getType(uri)
    return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
}

class GridCardMargin(val margin: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            top = margin
            bottom = margin
            left = margin
            right = margin
        }
    }
}

fun isLastVisible(rv: RecyclerView, shouldFindLastCompletelyVisible: Boolean = true): Boolean {
    val layoutManager = rv.layoutManager as LinearLayoutManager
    val pos = if (shouldFindLastCompletelyVisible) {
        layoutManager.findLastCompletelyVisibleItemPosition()
    } else {
        layoutManager.findFirstVisibleItemPosition()
    }
    val numItems: Int = rv.adapter?.itemCount ?: 0
    return pos >= numItems - 1
}

fun String.removeQuote(): String {
    return this.replace("\"", "")
}

fun String.formatCurrency(): String {
    return try {
        val formater = DecimalFormat("#,###")
        formater.format(toInt())
    } catch (e: Exception) {
        this
    }
}

fun openWhatsApp(view: View, context: Context, contact: String) {
    val url = "https://api.whatsapp.com/send?phone=${contact.replaceFirst("0", "+62")}"
    try {
        val pm: PackageManager = context.packageManager
        pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(context, i,null)
    } catch (e: PackageManager.NameNotFoundException) {
        snackbar(
            view,
            "Whatsapp app not installed in your phone",
            STATUS_ERROR
        )
        e.printStackTrace()
    }
}

fun View.setOnClickListenerWithDebounce(debounceTime: Long = 600L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}


fun View.showView() {
    this.visibility = View.VISIBLE
}

fun View.removeView() {
    this.visibility = View.GONE
}
