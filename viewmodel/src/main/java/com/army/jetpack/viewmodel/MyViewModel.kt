package com.army.jetpack.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author daijun
 * @date 2020/2/24
 * @description
 */
class MyViewModel: ViewModel() {

    val mName = MutableLiveData<String>()

}