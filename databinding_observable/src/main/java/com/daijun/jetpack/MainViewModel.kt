package com.daijun.jetpack

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.daijun.jetpack.databinding.observable.BR

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020/3/1
 * @description
 */
class MainViewModel : ObservableViewModel() {
    val name = ObservableField("Army")
    val lastName = ObservableField("OldArmy")
    val likes = ObservableInt(0)

    @Bindable
    fun getPopularity(): Popularity {
        return likes.get().let {
            when {
                it > 9 -> Popularity.STAR
                it > 4 -> Popularity.POPULAR
                else -> Popularity.NORMAL
            }
        }
    }

    fun onLike() {
        likes.increment()
        notifyFieldChanged(BR.popularity)
    }
}

private fun ObservableInt.increment() {
    set(get() + 1)
}

enum class Popularity {
    NORMAL,
    POPULAR,
    STAR
}