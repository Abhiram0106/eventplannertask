package com.github.abhiram0106.eventplannertask.feature_home.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class EventData(
    val id: Int,
    val title: String,
    val description: String,
    val date: LocalDate,
    val time: LocalTime
) {
    companion object {
        fun blank(): EventData = EventData(
            id = 0,
            title = "",
            description = "",
            date = LocalDate.now(),
            time = LocalTime.now()
        )
    }
}
