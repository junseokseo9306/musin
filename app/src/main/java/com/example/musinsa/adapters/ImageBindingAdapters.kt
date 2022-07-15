package com.example.musinsa.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.musinsa.R

@BindingAdapter("imgUrl")
fun setImageFromUrl(view: ImageView, imageUrl: String?) {
    imageUrl?.let { url ->
        view.load(url) {
            crossfade(200)
            error(R.drawable.img_not_found)
        }
    }
}