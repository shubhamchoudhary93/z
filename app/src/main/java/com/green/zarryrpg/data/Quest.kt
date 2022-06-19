package com.green.zarryrpg.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quest_data_table")
data class Quest(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "detail")
    var detail: String = "",
    @ColumnInfo(name = "inventory")
    var inventory: String = "",
    @ColumnInfo(name = "quantity")
    var quantity: Int = 0,
    @ColumnInfo(name = "pre_quest")
    var preQuest: String = "",
    @ColumnInfo(name = "level")
    var level: Int = 0,
    @ColumnInfo(name = "completed")
    var completed: Int = 0,
    @ColumnInfo(name = "world")
    var world: String = "Muggle"
)