package com.army.jetpack.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * @author daijun
 * @date 2020/2/26
 * @description
 */
// Entity注解还有别的参数，包括设置多个主键、设置某几列编入索引
@Entity(tableName = "word_table")
class Word(@[PrimaryKey ColumnInfo(name = "word")] val word: String) {
    @Ignore
    var pingyin: String = ""

    @ColumnInfo(defaultValue = "")
    var english: String = ""

    override fun toString(): String {
        return "Word(word='$word', pingyin='$pingyin', english='$english')"
    }
}