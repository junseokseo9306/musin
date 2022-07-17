package com.example.musinsa.adapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun setVisible(view: View, isVisible: Boolean?) {
    view.visibility = if (isVisible == true) {
        View.VISIBLE
    } else {
        View.GONE
    }
}