package com.army.jetpack.livedatabus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        LiveDataBus.instance.get<String>("text1").observeSticky(this, Observer {
            text1.text = it
        })
        LiveDataBus.instance.get<String>("text2").observeSticky(this, Observer {
            text2.text = it
        })

        edit1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                LiveDataBus.instance.get<String>("text1").setValue(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        edit2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                LiveDataBus.instance.get<String>("text2").setValue(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}
