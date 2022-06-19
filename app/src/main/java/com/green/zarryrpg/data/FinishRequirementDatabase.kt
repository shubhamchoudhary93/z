package com.green.zarryrpg.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FinishRequirement::class], version = 1, exportSchema = false)
abstract class FinishRequirementDatabase : RoomDatabase()  {
    abstract val finishRequirementDatabaseDao: FinishRequirementDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: FinishRequirementDatabase? = null

        fun getInstance(context: Context): FinishRequirementDatabase {
            synchronized(this) {

                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FinishRequirementDatabase::class.java,
                        "finish_requirement_data_table"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}