package com.example.penjualanhasillaut.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class SwipeLockableViewPager(
    context: Context, attrs: AttributeSet
): ViewPager(context, attrs) {

    private var swipeEnabled = false

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return when(swipeEnabled) {
            true -> super.onTouchEvent(ev)
            false -> false
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return when(swipeEnabled) {
            true -> super.onInterceptTouchEvent(ev)
            false -> false
        }
    }

    fun setSwipePAgingEnabled(swipeEnabled: Boolean) {
        this.swipeEnabled = swipeEnabled
    }
}