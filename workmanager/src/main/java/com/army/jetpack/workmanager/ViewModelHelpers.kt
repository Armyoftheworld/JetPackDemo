package com.army.jetpack.workmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author daijun
 * @date 2020/2/28
 * @description
 */

/**
 * Convenience factory to handle ViewModels with one parameter.
 * Make a factory:
 * ```
 * // Make a factory
 * val FACTORY = viewModelFactory(::MyViewModel)
 * ```
 *
 * Use the generated factory:
 * ```
 * ViewModelProviders.of(this, FACTORY(argument))
 *
 * ```
 *
 */
fun <T : ViewModel, ARG> singleArgViewModelFactory(constructor: (ARG) -> T):
            (ARG) -> ViewModelProvider.NewInstanceFactory {
    return { arg ->
        object : ViewModelProvider.NewInstanceFactory() {

            @Suppress("UNCHECKED_CAST")
            override fun <V : ViewModel> create(modelClass: Class<V>): V {
                return constructor(arg) as V
            }
        }
    }
}