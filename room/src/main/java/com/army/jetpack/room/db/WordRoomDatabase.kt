package com.army.jetpack.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.army.jetpack.room.dao.WordDao
import com.army.jetpack.room.entity.Book
import com.army.jetpack.room.entity.User
import com.army.jetpack.room.entity.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File

/**
 * @author daijun
 * @date 2020/2/26
 * @description
 */
@Database(entities = [Word::class, User::class, Book::class], version = 2, exportSchema = true)
abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {

        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE word_table ADD COLUMN english Text NOT NULL DEFAULT ''")
            }
        }

        fun getDatabase(context: Context, scope: CoroutineScope): WordRoomDatabase {
            // 可以把数据库存到
            val dbPath = File(context.externalCacheDirs[0], "word_database.db")
            println("dbPath = ${dbPath.absolutePath}")
            if (dbPath.parentFile?.exists() == false) {
                dbPath.parentFile?.mkdirs()
            }
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database.db"
//                    dbPath.absolutePath
                )
//                    .addCallback(WordDatabaseCallback(scope))
                    .addMigrations(MIGRATION_1_2)
                    .build().also { INSTANCE = it }
            }
        }
    }

    private class WordDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val wordDao = database.wordDao()
                    wordDao.deleteAll()
                    wordDao.insert(Word("Hello"))
                    wordDao.insert(Word("World"))
                    wordDao.insert(Word("TODO"))
                }
            }
        }
    }
}