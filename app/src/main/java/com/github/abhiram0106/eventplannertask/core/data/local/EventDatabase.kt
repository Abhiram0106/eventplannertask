package com.github.abhiram0106.eventplannertask.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.abhiram0106.eventplannertask.core.data.local.converters.Converters
import com.github.abhiram0106.eventplannertask.core.data.local.dao.EventDao
import com.github.abhiram0106.eventplannertask.core.data.local.model.EventEntity

@Database(entities = [EventEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}

object DatabaseInstance {
    @Volatile
    private var INSTANCE: EventDatabase? = null

    fun getDatabase(context: Context): EventDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                EventDatabase::class.java,
                "event_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}