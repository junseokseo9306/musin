package com.example.musinsa.common

import androidx.viewpager2.widget.ViewPager2

class CustomViewPagerCallback(
    private val changeIndicator: (Int) -> Unit,
) : ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        changeIndicator(position)
    }
}