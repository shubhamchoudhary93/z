package com.green.zarryrpg.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestRewardDatabaseDao {
    @Insert
    fun insert(questReward: QuestReward)

    @Update
    fun update(questReward: QuestReward)

    @Query("SELECT * from quest_reward_data_table WHERE id = :id")
    fun get(id: Long): QuestReward?

    @Query("DELETE FROM quest_reward_data_table WHERE id = :id")
    fun delete(id: Long)

    @Query("DELETE FROM quest_reward_data_table")
    fun clear()

    @Query("SELECT * from quest_reward_data_table WHERE name = :name")
    fun getByName(name: String): MutableList<QuestReward>

}