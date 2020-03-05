package com.army.jetpack.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.army.jetpack.room.entity.Word

/**
 * @author daijun
 * @date 2020/2/26
 * @description
 */
@Dao
interface WordDao {
    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): LiveData<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWords(words: List<Word>): @JvmSuppressWildcards List<Long>

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

    @Delete
    fun delete(word: Word)

    @Update
    fun update(word: Word)
}