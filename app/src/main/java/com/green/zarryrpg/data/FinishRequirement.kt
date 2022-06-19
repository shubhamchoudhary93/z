package com.green.zarryrpg.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "finish_requirement_data_table")
data class FinishRequirement(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "inventoryFinishId")
    var inventoryFinish: String = "",
    @ColumnInfo(name = "inventoryId")
    var inventory: String = "",
    @ColumnInfo(name = "quantity")
    var quantity: Int = 0
)