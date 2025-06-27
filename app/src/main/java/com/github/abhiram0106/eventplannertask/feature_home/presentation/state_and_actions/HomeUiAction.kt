package com.github.abhiram0106.eventplannertask.feature_home.presentation.state_and_actions

import java.time.LocalDate

sealed class HomeUiAction {
    data class OnSelectDate(val date: LocalDate) : HomeUiAction()
    data class OnCurrentMonthChanged(val month: Int, val year: Int) : HomeUiAction()
    data class OnSelectEvent(val eventId: Int) : HomeUiAction()
}