package com.army.jetpack.room.repository

import com.army.jetpack.room.dao.WordDao
import com.army.jetpack.room.entity.Word

/**
 * @author daijun
 * @date 2020/2/26
 * @description
 */
class WordRepository(private val wordDao: WordDao) {
    val allWords = wordDao.getAlphabetizedWords()

    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}