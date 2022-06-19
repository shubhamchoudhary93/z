package com.green.zarryrpg.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quest_reward_data_table")
data class QuestReward(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "inventory")
    var inventory: String = "",
    @ColumnInfo(name = "quantity")
    var quantity: Int = 0
)