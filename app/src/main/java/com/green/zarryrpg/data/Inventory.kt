package com.green.zarryrpg.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory_data_table")
data class Inventory(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String = "name",
    @ColumnInfo(name = "type")
    var type: String = "type",
    @ColumnInfo(name = "world")
    var world: String = "Muggle",
    @ColumnInfo(name = "cost")
    var cost: Int = 0,
    @ColumnInfo(name = "sell")
    var sell: Int = 0,
    @ColumnInfo(name = "costGold")
    var costGold: Int = 0,
    @ColumnInfo(name = "xp")
    var xp: Int = 0,
    @ColumnInfo(name = "time")
    var time: Long = 0,
    @ColumnInfo(name = "unlockLevel")
    var unlockLevel: Int = 0,
    @ColumnInfo(name = "quantity")
    var quantity: Int = 0,
    @ColumnInfo(name = "corresponding")
    var corresponding: String = "",
    @ColumnInfo(name = "can_buy")
    var canBuy: Int = 0,
    @ColumnInfo(name = "can_sell")
    var canSell: Int = 0,
    @ColumnInfo(name = "stamina")
    var stamina: Int = 0
)