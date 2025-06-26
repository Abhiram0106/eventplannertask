package com.github.abhiram0106.eventplannertask.feature_home.presentation

import androidx.lifecycle.ViewModel
import com.github.abhiram0106.eventplannertask.feature_home.presentation.state_and_actions.HomeUiAction
import com.github.abhiram0106.eventplannertask.feature_home.presentation.state_and_actions.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun onUiAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.OnCurrentMonthChanged -> {
                // todo fetch datesWhereAtLeastOneEventExists
            }

            is HomeUiAction.OnSelectDate -> {
                // todo fetch events from db
                // todo events = generateEventList().groupBy { it.time.truncatedTo(ChronoUnit.MINUTES) },
                _uiState.update {
                    it.copy(selectedDate = action.date)
                }
            }

            is HomeUiAction.OnSelectEvent -> {
                // todo : show populated bottom sheet
            }
        }
    }
}