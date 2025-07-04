package com.github.abhiram0106.eventplannertask.core.util

import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData
import java.time.LocalDate
import java.time.LocalTime
import java.time.Year
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.time.Instant

fun LocalTime.toDisplayString(): String {
    return this.format(DateTimeFormatter.ofPattern("hh:mm a"))
}

fun LocalDate.toDisplayString(): String {
    val currentYear = Year.now().value
    return if (this.year == currentYear) {
        this.format(DateTimeFormatter.ofPattern("dd MMM"))
    } else {
        this.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
    }
}

fun Long?.toLocalDate(): LocalDate {
    return java.time.Instant.ofEpochMilli(this ?: System.currentTimeMillis())
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

fun generateEventList(): List<EventData> {
    val list = mutableListOf<EventData>()
    repeat(20) {
        list.add(
            EventData(
                id = it,
                title = "event title #$it",
                description = "a description for the event #$it ".repeat(3),
                date = LocalDate.now(),
                time = LocalTime.now().plusHours(it.toLong())
            )
        )
        list.add(
            EventData(
                id = it + 21,
                title = "event title #$it 2",
                description = "a description for the event #$it 2 ".repeat(3),
                date = LocalDate.now(),
                time = LocalTime.now().plusHours(it.toLong())
            )
        )
    }
    return list
}