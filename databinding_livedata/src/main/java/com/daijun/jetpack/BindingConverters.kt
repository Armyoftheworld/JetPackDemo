package com.daijun.jetpack

import android.view.View
import androidx.databinding.BindingConversion

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020/3/1
 * @description
 */
object ConverterUtil {

    @JvmStatic
    fun isZero(number: Int) = number == 0
}

object BindingConverters {

    /**
     * There is no need to specify that this converter should be used. [BindingConversion]s are
     * applied automatically.
     */
    @BindingConversion
    @JvmStatic
    fun booleanToVisibility(isNotVisible: Boolean): Int {
        return if (isNotVisible) View.GONE else View.VISIBLE
    }
}