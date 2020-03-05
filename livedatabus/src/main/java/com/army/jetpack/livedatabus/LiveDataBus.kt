package com.army.jetpack.livedatabus

import androidx.lifecycle.*
import com.army.jetpack.livedatabus.LiveDataBus.ObserverWrapper
import java.util.concurrent.ConcurrentHashMap

/**
 * @author daijun
 * @date 2020/2/25
 * @description
 */
class LiveDataBus private constructor() {
    companion object {
        val instance: LiveDataBus by lazy { LiveDataBus() }
    }

    val liveDataMaps = ConcurrentHashMap<String, BusLiveData<Any>>(10)

    inline fun <reified T : Any> get(key: String): BusLiveData<T> {
        var busLiveData = liveDataMaps[key]
        if (busLiveData == null) {
            busLiveData = BusLiveData(key)
            liveDataMaps[key] = busLiveData
        }
        return busLiveData as BusLiveData<T>
    }

    inner class BusLiveData<T>(private val eventName: String) : LiveData<T>(),
        LifecycleEventObserver {

        var currentVersion = 0
            private set

        public override fun setValue(value: T) {
            currentVersion++
            super.setValue(value)
        }

        public override fun postValue(value: T) {
            currentVersion++
            super.postValue(value)
        }

        fun setStickyValue(value: T) {
            setValue(value)
        }

        fun postStickValue(value: T) {
            postValue(value)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            observe(owner, observer, false)
        }

        fun observeSticky(owner: LifecycleOwner, observer: Observer<in T>) {
            observe(owner, observer, true)
        }

        private fun observe(owner: LifecycleOwner, observer: Observer<in T>, isSticky: Boolean) {
            if (owner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                return
            }
            super.observe(owner, ObserverWrapper(this, isSticky, observer))
            owner.lifecycle.addObserver(this)
        }

        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                liveDataMaps.remove(eventName)
            }
        }
    }

    class ObserverWrapper<T>(
        private val busLiveData: BusLiveData<T>,
        private val isSticky: Boolean,
        private val observer: Observer<in T>
    ) : Observer<T> {

        /**
         * 把busLiveData的version赋值给刚创建的观察者的version，这样就不会收到之前之前发的消息了
         * 当调用busLiveData的setValue和postValue等方法时，busLiveData的version会自增长，
         * 当前观察者就能收到新发的消息了
         */
        private var lastVersion = busLiveData.currentVersion

        override fun onChanged(t: T) {
            // 到了这里，busLiveData的version肯定改变了，因为只有调用了setValue或者postValue才会走到这里
            if (lastVersion < busLiveData.currentVersion) {
                lastVersion = busLiveData.currentVersion
                observer.onChanged(t)
                return
            }
            // 如果是粘性事件，则之前发的消息也都会收到并处理，只有首次创建的时候会执行这里
            if (isSticky) {
                observer.onChanged(t)
            }
        }
    }
}