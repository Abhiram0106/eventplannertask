package com.github.abhiram0106.eventplannertask.feature_home.presentation.state_and_actions

import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData
import java.time.LocalDate
import java.time.LocalTime

data class HomeUiState(
    val selectedDate: LocalDate = LocalDate.now(),
    val events: Map<LocalTime, List<EventData>> = emptyMap(),
    val selectedMonth: Int = LocalDate.now().monthValue,
    val datesWhereAtLeastOneEventExists: List<LocalDate> = emptyList(),
    val isLoading: Boolean = false,
)
