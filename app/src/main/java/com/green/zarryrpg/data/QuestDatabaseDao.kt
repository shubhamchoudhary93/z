package com.green.zarryrpg.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestDatabaseDao {
    @Insert
    fun insert(quest: Quest)

    @Update
    fun update(quest: Quest)

    @Query("SELECT * from quest_data_table WHERE id = :id")
    fun get(id: Long): Quest?

    @Query("DELETE FROM quest_data_table WHERE id = :id")
    fun delete(id: Long)

    @Query("DELETE FROM quest_data_table")
    fun clear()

    @Query("SELECT * from quest_data_table WHERE name = :name")
    fun getByName(name: String): MutableList<Quest>

    @Query("SELECT * FROM quest_data_table WHERE id IN (SELECT MIN(id) FROM quest_data_table GROUP BY name) AND completed = 0")
    fun getAvailable(): MutableList<Quest>

}