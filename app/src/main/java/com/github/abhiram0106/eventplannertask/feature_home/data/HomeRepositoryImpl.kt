package com.github.abhiram0106.eventplannertask.feature_home.data

import com.github.abhiram0106.eventplannertask.core.data.local.dao.EventDao
import com.github.abhiram0106.eventplannertask.feature_home.domain.HomeRepository
import com.github.abhiram0106.eventplannertask.feature_home.domain.asDate
import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData
import com.github.abhiram0106.eventplannertask.feature_home.domain.toEntity
import com.github.abhiram0106.eventplannertask.feature_home.domain.toLocalList
import com.github.abhiram0106.eventplannertask.feature_home.domain.toMappedAsDateToLocal
import com.github.abhiram0106.eventplannertask.feature_home.domain.toMappedAsTimeToLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth

class HomeRepositoryImpl(
    private val eventDao: EventDao
) : HomeRepository {

    override suspend fun getEventsByDate(date: LocalDate): Flow<Map<LocalTime, List<EventData>>> {
        return eventDao.getEventsByDate(date).map { it.toMappedAsTimeToLocal() }
    }

    override suspend fun getEventsByMonth(month: Int, year: Int): Flow<List<LocalDate>> {
        val monthStr = month.toString().padStart(2, '0')
        val yearStr = year.toString()

        val next = YearMonth.of(year, month).plusMonths(1)
        val nextMonthStr = next.monthValue.toString().padStart(2, '0')
        val nextYearStr = next.year.toString()

        return eventDao.getEventsByMonth(
            month = monthStr,
            year = yearStr,
            nextMonth = nextMonthStr,
            nextYear = nextYearStr
        ).map { it.asDate() }
    }

    override suspend fun getUpcomingEvents(): Flow<Map<LocalDate, List<EventData>>> {
        return eventDao.getUpcomingEvents().map { it.toMappedAsDateToLocal() }
    }

    override suspend fun upsertEvent(event: EventData) {
        eventDao.upsertEvent(event.toEntity())
    }

    override suspend fun deleteEvent(event: EventData) {
        eventDao.deleteEvent(event.toEntity())
    }
}