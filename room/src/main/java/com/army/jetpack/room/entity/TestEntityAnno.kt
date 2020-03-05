package com.army.jetpack.room.entity

import androidx.room.*

/**
 * @author daijun
 * @date 2020/2/26
 * @description See <a href="https://www.sqlite.org/datatype3.html">SQLite types documentation</a>
 */
data class Address(
    val street: String?, val state: String?, val city: String?,
    @ColumnInfo(name = "post_code") val postCode: Int
)

@Entity(primaryKeys = ["id"], tableName = "user")
data class User(val id: Int, val name: String, @Embedded val address: Address)

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"]
    )],
    tableName = "book"
)
data class Book(
    @PrimaryKey val bookId: Int, val title: String,
    @ColumnInfo(name = "user_id") val userId: Int
)