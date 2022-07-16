package com.example.musinsa.adapters

import android.text.Spannable
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.core.text.buildSpannedString
import androidx.databinding.BindingAdapter
import com.example.musinsa.R
import java.text.DecimalFormat

@BindingAdapter("bannerTitle")
fun setTitleLineSeparation(view: TextView, title: String?) {
    val blank = ' '
    val lineSeparator = '\n'
    val maxLength = 6
    var isChanged = false
    val modifiedString = StringBuilder()
    val nonNullTitle = title ?: ""
    for (index in nonNullTitle.indices) {
        if (!isChanged && index >= maxLength && nonNullTitle[index] == blank) {
            modifiedString.append(lineSeparator)
            isChanged = true
        } else {
            modifiedString.append(nonNullTitle[index])
        }
    }
    view.text = modifiedString.toString()
}

@BindingAdapter("headerTitle")
fun setHeaderTitleSeparation(view: TextView, title: String?) {
    val nonNullTitle = title ?: ""
    val lineSeparatorChar = ':'
    var separationIndex = 0
    if (!nonNullTitle.contains(lineSeparatorChar)) {
        view.text = nonNullTitle
        return
    }
    for (index in nonNullTitle.indices) {
        val char = nonNullTitle[index]
        if (char == lineSeparatorChar) {
            separationIndex = index
        }
    }
    val spannableString = buildSpannedString {
        append(nonNullTitle)
        setSpan(
            AbsoluteSizeSpan(24),
            separationIndex,
            nonNullTitle.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    view.text = spannableString
}

@BindingAdapter("price")
fun setPriceText(view: TextView, price: Int) {
    val decimalFormat = DecimalFormat("#,###")
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