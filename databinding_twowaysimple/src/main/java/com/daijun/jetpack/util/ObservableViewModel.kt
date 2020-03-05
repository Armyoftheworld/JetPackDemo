package com.daijun.jetpack.util

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020/3/1
 * @description
 */
open class ObservableViewModel : ViewModel(), Observable {

    private val callbacks = PropertyChangeRegistry()

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    fun notifyFieldChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }
}