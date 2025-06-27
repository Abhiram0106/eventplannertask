package com.github.abhiram0106.eventplannertask.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.github.abhiram0106.eventplannertask.core.data.local.model.EventEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface EventDao {

    @Query("SELECT * FROM EventEntity WHERE date = :date ORDER BY id ASC")
    fun getEventsByDate(date: LocalDate): Flow<List<EventEntity>>

    @Query(
        """
    SELECT * FROM EventEntity
    WHERE strftime('%Y', date) = :year AND strftime('%m', date) = :month
    ORDER BY id ASC
"""
    )
    fun getEventsByMonth(month: String, year: String): Flow<List<EventEntity>>

    @Query(
        """
        SELECT * FROM EventEntity
        WHERE date BETWEEN date('now') AND date('now', '+7 days')
        ORDER BY date ASC
    """
    )
    fun getUpcomingEvents(): Flow<List<EventEntity>>

    @Upsert
    suspend fun upsertEvent(event: EventEntity)

    @Delete
    suspend fun deleteEvent(event: EventEntity)
}