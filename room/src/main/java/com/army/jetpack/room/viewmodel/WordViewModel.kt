package com.army.jetpack.room.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.army.jetpack.room.db.WordRoomDatabase
import com.army.jetpack.room.entity.Word
import com.army.jetpack.room.repository.WordRepository
import kotlinx.coroutines.launch

/**
 * @author daijun
 * @date 2020/2/26
 * @description
 */
class WordViewModel(application: Application) : AndroidViewModel(application) {
    private val wordDao =
        WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
    private val repository = WordRepository(wordDao)
    val allWords = repository.allWords

    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }

}