package com.daijun.jetpack

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020/3/1
 * @description
 */

@BindingAdapter("app:hideIfZero")
fun hideIfZero(view: View, number: Int) {
    view.visibility = if (number == 0) View.GONE else View.VISIBLE
}

@BindingAdapter("app:progressScaled", "android:max")
fun setProgress(progressBar: ProgressBar, likes: Int, max: Int) {
    progressBar.progress = (likes * max / 5).coerceAtMost(max)
}

@BindingAdapter("app:popularityIcon")
fun popularityIcon(view: ImageView, popularity: Popularity) {
    val color = getAssociatedColor(popularity, view.context)
    ImageViewCompat.setImageTintList(view, ColorStateList.valueOf(color))
    view.setImageDrawable(getPopularityDrawable(popularity, view.context))
}

@BindingAdapter("app:progressTint")
fun popularityTint(progressBar: ProgressBar, popularity: Popularity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val color = getAssociatedColor(popularity, progressBar.context)
        progressBar.progressTintList = ColorStateList.valueOf(color)
    }
}

private fun getAssociatedColor(popularity: Popularity, context: Context): Int {
    return when (popularity) {
        Popularity.NORMAL ->
            context.theme.obtainStyledAttributes(intArrayOf(android.R.attr.colorForeground))
                .getColor(0, 0x000000)
        Popularity.POPULAR -> ContextCompat.getColor(context, R.color.popular)
        Popularity.STAR -> ContextCompat.getColor(context, R.color.star)
    }
}

private fun getPopularityDrawable(popularity: Popularity, context: Context): Drawable? {
    return when (popularity) {
        Popularity.NORMAL -> ContextCompat.getDrawable(context, R.drawable.ic_person_black_96dp)
        Popularity.POPULAR -> ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
        Popularity.STAR -> ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
    }
}