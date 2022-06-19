package com.green.zarryrpg.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QuestReward::class], version = 1, exportSchema = false)
abstract class QuestRewardDatabase : RoomDatabase()  {
    abstract val questRewardDatabaseDao: QuestRewardDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: QuestRewardDatabase? = null

        fun getInstance(context: Context): QuestRewardDatabase {
            synchronized(this) {

                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        QuestRewardDatabase::class.java,
                        "quest_reward_data_table"
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