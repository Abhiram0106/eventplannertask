package com.github.abhiram0106.eventplannertask.feature_home.data

import com.github.abhiram0106.eventplannertask.core.data.local.dao.EventDao
import com.github.abhiram0106.eventplannertask.feature_home.domain.HomeRepository
import com.github.abhiram0106.eventplannertask.feature_home.domain.asDate
import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData
import com.github.abhiram0106.eventplannertask.feature_home.domain.toEntity
import com.github.abhiram0106.eventplannertask.feature_home.domain.toLocalList
import com.github.abhiram0106.eventplannertask.feature_home.domain.toMappedAsTimeAndLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalTime

class HomeRepositoryImpl(
    private val eventDao: EventDao
) : HomeRepository {

    override suspend fun getEventsByDate(date: LocalDate): Flow<Map<LocalTime, List<EventData>>> {
        return eventDao.getEventsByDate(date).map { it.toMappedAsTimeAndLocal() }
    }

    override suspend fun getEventsByMonth(month: Int): Flow<List<LocalDate>> {
        return eventDao.getEventsByMonth(month).map { it.asDate() }
    }

    override suspend fun getUpcomingEvents(): Flow<List<EventData>> {
        return eventDao.getUpcomingEvents().map { it.toLocalList() }
    }

    override suspend fun upsertEvent(event: EventData) {
        eventDao.upsertEvent(event.toEntity())
    }

    override suspend fun deleteEvent(event: EventData) {
        eventDao.deleteEvent(event.toEntity())
    }
}