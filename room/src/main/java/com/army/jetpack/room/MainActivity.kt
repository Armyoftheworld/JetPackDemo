package com.army.jetpack.room

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.army.jetpack.room.adapter.WordListAdapter
import com.army.jetpack.room.entity.Word
import com.army.jetpack.room.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1
    private val wordViewModel: WordViewModel by lazy {
        ViewModelProvider(this).get(WordViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = WordListAdapter()
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)
        wordViewModel.allWords.observe(this, Observer {
            adapter.setWords(it)
        })
        fab.setOnClickListener {
            startActivityForResult(
                Intent(this, AddWordActivity::class.java),
                newWordActivityRequestCode
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val word = data?.getStringExtra(AddWordActivity.EXTRA_REPLY_WORD) ?: ""
            val english = data?.getStringExtra(AddWordActivity.EXTRA_REPLY_ENGLISH) ?: ""
            wordViewModel.insert(Word(word).also { it.english = english })
        }
    }
}
