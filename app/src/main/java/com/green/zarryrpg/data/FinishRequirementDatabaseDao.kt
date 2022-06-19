package com.green.zarryrpg.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FinishRequirementDatabaseDao {
    @Insert
    fun insert(finishRequirement: FinishRequirement)

    @Update
    fun update(finishRequirement: FinishRequirement)

    @Query("SELECT * from finish_requirement_data_table WHERE id = :id")
    fun get(id: Long): FinishRequirement?

    @Query("DELETE FROM finish_requirement_data_table WHERE id = :id")
    fun delete(id: Long)

    @Query("DELETE FROM finish_requirement_data_table")
    fun clear()

    @Query("SELECT * from finish_requirement_data_table WHERE inventoryFinishId = :corresponding")
    fun getByRequirementID(corresponding: String): MutableList<FinishRequirement>

}