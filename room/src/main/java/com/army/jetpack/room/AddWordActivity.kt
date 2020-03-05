package com.army.jetpack.room

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_word.*

class AddWordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
        button_save.setOnClickListener {
            val intent = Intent()
            intent.putExtra(EXTRA_REPLY_WORD, edit_word.text.toString())
            intent.putExtra(EXTRA_REPLY_ENGLISH, edit_english.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY_WORD = "com.example.android.wordlistsql.REPLY.word"
        const val EXTRA_REPLY_ENGLISH = "com.example.android.wordlistsql.REPLY.english"
    }
}
