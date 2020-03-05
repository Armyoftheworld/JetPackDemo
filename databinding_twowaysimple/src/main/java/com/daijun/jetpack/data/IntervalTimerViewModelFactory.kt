package com.daijun.jetpack.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daijun.jetpack.util.DefaultTimer
import java.lang.IllegalArgumentException

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020/3/3
 * @description
 */
class IntervalTimerViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IntervalTimerViewModel::class.java)) {
            return IntervalTimerViewModel(DefaultTimer) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}