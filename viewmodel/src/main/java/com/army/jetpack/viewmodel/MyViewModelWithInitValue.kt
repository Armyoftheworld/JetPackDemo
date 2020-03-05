package com.army.jetpack.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author daijun
 * @date 2020/2/24
 * @description
 */
class MyViewModelWithInitValue(initValue: String) : ViewModel() {

    val otherName = MutableLiveData<String>(initValue)

    @Suppress("UNCHECKED_CAST")
    class Factory(private val initValue: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MyViewModelWithInitValue(initValue) as T
        }
    }
}