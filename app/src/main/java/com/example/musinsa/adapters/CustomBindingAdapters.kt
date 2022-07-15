package com.example.musinsa.adapters

import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.buildSpannedString
import androidx.databinding.BindingAdapter
import coil.load
import com.example.musinsa.R
import java.text.DecimalFormat

private val decimalFormat = DecimalFormat("#,###")

@BindingAdapter("imgUrl")
fun setImageFromUrl(view: ImageView, imageUrl: String?) {
    imageUrl?.let { url ->
        view.load(url) {
            crossfade(200)
            error(R.drawable.img_not_found)
        }
    }
}

@BindingAdapter("title")
fun setTitle(view: TextView, title: String) {
    val blank = ' '
    val nextLine = '\n'
    val maxLength = 6
    var isChanged = false
    val modifiedString = StringBuilder()
    for (index in title.indices) {
        if (index >= maxLength && title[index] == blank && !isChanged) {
            modifiedString.append(nextLine)
            isChanged = true
        } else {
            modifiedString.append(title[index])
        }
    }
    view.text = modifiedString.toString()
}

@BindingAdapter("price")
fun setPriceText(view: TextView, price: Int) {
    view.text = view.context.getString(R.string.currency, decimalFormat.format(price))
}

@BindingAdapter("colorText")
fun setColorText(view: TextView, percentage: Int) {
    val percentMark = '%'
    val spannableString = buildSpannedString {
        append(percentage.toString())
        append(percentMark)
        setSpan(
            ForegroundColorSpan(view.context.getColor(R.color.red)),
            0,
            this.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    view.text = spannableString
}

@BindingAdapter("visible")
fun setVisible(view: View, isClicked: Boolean?) {
    view.visibility = if (isClicked == true) {
        View.VISIBLE
    } else {
        View.GONE
    }
}