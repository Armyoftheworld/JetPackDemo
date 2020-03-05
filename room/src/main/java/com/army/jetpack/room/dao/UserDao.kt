package com.army.jetpack.room.dao

import android.database.Cursor
import androidx.room.*
import com.army.jetpack.room.entity.User

/**
 * @author daijun
 * @date 2020/2/26
 * @description
 */
@Dao
abstract class UserDao {
    @Query("SELECT * FROM user WHERE id=:id")
    abstract fun getUserInfo(id: String): User

    /**
     * 直接光标访问
     * 注意：强烈建议您不要使用 Cursor API，因为它无法保证行是否存在或者行包含哪些值。
     * 只有当您已具有需要光标且无法轻松重构的代码时，才使用此功能。
     */
    @Query("SELECT * FROM user WHERE id=:id")
    abstract fun getUserInfo2(id: String): Cursor

    /**
     * 注意：应避免在单个数据库事务中执行额外的应用端工作，因为 Room 会将此类事务视为独占事务，并且按顺序每次仅执行一个事务。
     * 也就是说，包含不必要操作的事务很容易锁定您的数据库并影响性能。
     */
    @Transaction
    open suspend fun setLoggedInUser(user: User) {
        deleteUser(user)
        insertUser(user)
    }

    @Delete
    abstract fun deleteUser(user: User)

    @Insert
    abstract fun insertUser(user: User)
}