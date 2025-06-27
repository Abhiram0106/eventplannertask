package com.github.abhiram0106.eventplannertask.feature_upcoming_events.presentation.state_and_actions

import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData
import java.time.LocalDate

data class UpcomingEventsUiState(
    val events: Map<LocalDate, List<EventData>> = emptyMap(),
)
