package com.github.abhiram0106.eventplannertask.feature_home.domain

import com.github.abhiram0106.eventplannertask.core.data.local.model.EventEntity
import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit

fun EventEntity.toLocal(): EventData = EventData(
    id = id,
    title = title,
    description = description,
    date = date,
    time = time
)

fun List<EventEntity>.toLocalList(): List<EventData> = map { it.toLocal() }

fun EventEntity.toLocalAsDate(): LocalDate = this.date
fun List<EventEntity>.asDate(): List<LocalDate> = map { it.toLocalAsDate() }

fun List<EventEntity>.toMappedAsTimeAndLocal(): Map<LocalTime, List<EventData>> =
    map { it.toLocal() }
        .groupBy { it.time.truncatedTo(ChronoUnit.MINUTES) }

fun EventData.toEntity(): EventEntity = EventEntity(
    id = id,
    title = title,
    description = description,
    date = date,
    time = time
)