package com.army.jetpack.workmanager

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @author daijun
 * @date 2020/2/28
 * @description
 */
@Entity
data class Title(val title: String, @PrimaryKey val id: Int = 0)

@Dao
interface TitleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTitle(title: Title)

    @get:Query("SELECT * FROM Title WHERE id = 0")
    val titleLiveData: LiveData<Title?>
}

@Database(entities = [Title::class], version = 1, exportSchema = false)
abstract class TitleDataBase : RoomDatabase() {
    abstract val titleDao: TitleDao
}

private lateinit var INSTANCE: TitleDataBase

fun getDatabase(context: Context): TitleDataBase {
    if (!::INSTANCE.isInitialized) {
        synchronized(TitleDataBase::class) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                TitleDataBase::class.java,
                "titles_db"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}