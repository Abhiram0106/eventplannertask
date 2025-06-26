package com.github.abhiram0106.eventplannertask.core.util

import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalTime.toDisplayString(): String {
    return this.format(DateTimeFormatter.ofPattern("hh:mm a"))
}

fun generateEventList(): List<EventData> {
    val list = mutableListOf<EventData>()
    repeat(20) {
        list.add(
            EventData(
                title = "event title #$it",
                description = "a description for the event #$it ".repeat(3),
                date = LocalDate.now(),
                time = LocalTime.now().plusHours(it.toLong())
            )
        )
    }
    return list
}