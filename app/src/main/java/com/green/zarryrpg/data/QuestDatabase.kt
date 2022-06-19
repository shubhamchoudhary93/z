package com.green.zarryrpg.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Quest::class], version = 1, exportSchema = false)
abstract class QuestDatabase : RoomDatabase()  {
    abstract val questDatabaseDao: QuestDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: QuestDatabase? = null

        fun getInstance(context: Context): QuestDatabase {
            synchronized(this) {

                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        QuestDatabase::class.java,
                        "quest_data_table"
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