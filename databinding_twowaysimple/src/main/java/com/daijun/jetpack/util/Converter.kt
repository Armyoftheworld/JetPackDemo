@file:JvmName("Converter")

package com.daijun.jetpack.util

import kotlin.math.round

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020/3/2
 * @description
 */

fun fromTenthsToSeconds(tenths: Int): String {
    return if (tenths < 600) {
        String.format("%.1f", tenths / 10.0)
    } else {
        val minutes = tenths / 10 / 60
        val seconds = (tenths / 10) % 60
        String.format("%d:%02d", minutes, seconds)
    }
}

fun cleanSecondsString(seconds: String): Int {
    // Remove letters and other characters
    val filteredValue = seconds.replace(Regex("""[^\d:.]"""), "")
    if (filteredValue.isEmpty()) {
        return 0
    }
    val elements = filteredValue.split(":").map { round(it.toDouble()).toInt() }
    var result = 0
    return when {
        elements.size > 2 -> result
        elements.size > 1 -> {
            result = elements[0] * 60
            result += elements[1]
            result * 10
        }
        else -> elements[0] * 10
    }
}