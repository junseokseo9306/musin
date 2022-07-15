package com.example.musinsa.adapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun setVisible(view: View, isClicked: Boolean?) {
    view.visibility = if (isClicked == true) {
        View.VISIBLE
    } else {
        View.GONE
    }
}