package com.daijun.jetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableInt
import com.daijun.jetpack.databinding.observable.R
import com.daijun.jetpack.databinding.observable.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val profile = ObservableFieldProfile("Army", "oldArmy", ObservableInt(0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.user = profile
    }

    fun onLike(view: View) {
        profile.likes.set(profile.likes.get() + 1)
    }
}
