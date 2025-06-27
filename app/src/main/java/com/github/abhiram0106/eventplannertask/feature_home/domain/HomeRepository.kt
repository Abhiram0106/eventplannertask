package com.github.abhiram0106.eventplannertask.feature_home.domain

import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime

interface HomeRepository {

    suspend fun getEventsByDate(date: LocalDate): Flow<Map<LocalTime, List<EventData>>>

    suspend fun getEventsByMonth(month: Int): Flow<List<LocalDate>>

    suspend fun getUpcomingEvents(): Flow<List<EventData>>

    suspend fun upsertEvent(event: EventData)

    suspend fun deleteEvent(event: EventData)
}