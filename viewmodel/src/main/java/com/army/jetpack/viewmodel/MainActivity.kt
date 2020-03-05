package com.army.jetpack.viewmodel

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val myViewModel: MyViewModel by lazy {
        ViewModelProvider(this).get(MyViewModel::class.java)
    }

    private val myViewModelWithInitValue: MyViewModelWithInitValue by lazy {
        ViewModelProvider(this, MyViewModelWithInitValue.Factory("initValue"))
            .get(MyViewModelWithInitValue::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myViewModel.mName.observe(this, Observer {
            text.text = it
        })
        myViewModelWithInitValue.otherName.observe(this, Observer {
            text1.text = it
        })
        edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                myViewModel.mName.value = s?.toString()
                myViewModelWithInitValue.otherName.value = s?.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}
