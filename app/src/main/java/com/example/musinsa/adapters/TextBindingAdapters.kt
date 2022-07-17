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
    val rawText = title ?: ""
    val testList = rawText.split(blank)
    val prettifiedText = buildString {
        testList.forEachIndexed { index, text ->
            append(text)
            append(blank)
            if (index == 1) {
                append(lineSeparator)
            }
        }
    }
    view.text = prettifiedText
}

@BindingAdapter("headerTitle")
fun setHeaderTitleDependsOnLength(view: TextView, title: String?) {
    val rawText = title ?: ""
    if (rawText.length < 15) {
        view.text = rawText
        return
    }
    val spannableString = buildSpannedString {
        append(rawText)
        setSpan(
            AbsoluteSizeSpan(13, true),
            0,
            rawText.length,
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

@BindingAdapter("colorPercent")
fun setColorPercentageText(view: TextView, percentage: Int) {
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