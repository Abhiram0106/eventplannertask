package com.github.abhiram0106.eventplannertask.feature_upcoming_events.presentation.state_and_actions

import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData

sealed class UpcomingEventsUiAction {
    data class OnDeleteEvent(val event: EventData) : UpcomingEventsUiAction()
}