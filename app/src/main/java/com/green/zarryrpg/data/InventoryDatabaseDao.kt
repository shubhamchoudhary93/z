package com.green.zarryrpg.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface InventoryDatabaseDao {
    @Insert
    fun insert(inventory: Inventory)

    @Update
    fun update(inventory: Inventory)

    @Query("SELECT * from inventory_data_table WHERE id = :id")
    fun get(id: Long): Inventory?

    @Query("SELECT * from inventory_data_table WHERE name = :name")
    fun getName(name: String): Inventory

    @Query("DELETE FROM inventory_data_table WHERE id = :id")
    fun delete(id: Long)

    @Query("DELETE FROM inventory_data_table")
    fun clear()

    @Query("SELECT * from inventory_data_table WHERE world= :world AND quantity>0")
    fun getAvailableByWorld(world: String): MutableList<Inventory>

    @Query("SELECT * from inventory_data_table WHERE type= :type AND unlockLevel <= :level")
    fun getAvailableItem(type: String, level: Int): MutableList<Inventory>

    @Query("SELECT corresponding from inventory_data_table WHERE name = :name")
    fun getCorresponding(name: String): String

    @Query("SELECT * from inventory_data_table WHERE world = :world AND type = :type")
    fun getAvailableByWorldAndType(world: String,type: String): MutableList<Inventory>

    @Query("SELECT * from inventory_data_table WHERE quantity>0 AND type != :type")
    fun getAvailable(type: String): MutableList<Inventory>

    @Query("SELECT * from inventory_data_table WHERE world= :world AND can_buy= 1 AND unlockLevel <= :level")
    fun getAllBuy(level: Int, world: String): MutableList<Inventory>

    @Query("SELECT * from inventory_data_table WHERE world= :world AND quantity>0 AND can_sell = 1")
    fun getAllSell(world: String): MutableList<Inventory>
}