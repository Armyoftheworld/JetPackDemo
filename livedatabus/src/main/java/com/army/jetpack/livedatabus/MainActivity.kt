package com.army.jetpack.livedatabus

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToMain2Activity(view: View) {
        startActivity(Intent(this, Main2Activity::class.java))
    }

    fun sendMessageToText1(view: View) {
        val currentTimeMillis = System.currentTimeMillis().toString()
        LiveDataBus.instance.get<String>("text1")
            .setStickyValue(currentTimeMillis)
        println("sendMessageToText1 $currentTimeMillis")
    }

    fun sendMessageToText2(view: View) {
        val currentTimeMillis = System.currentTimeMillis().toString()
        LiveDataBus.instance.get<String>("text2")
            .setStickyValue(currentTimeMillis)
        println("sendMessageToText2 $currentTimeMillis")
    }
}
