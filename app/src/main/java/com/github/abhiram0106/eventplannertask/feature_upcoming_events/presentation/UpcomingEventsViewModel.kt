package com.github.abhiram0106.eventplannertask.feature_upcoming_events.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.abhiram0106.eventplannertask.feature_home.domain.HomeRepository
import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData
import com.github.abhiram0106.eventplannertask.feature_upcoming_events.presentation.state_and_actions.UpcomingEventsUiAction
import com.github.abhiram0106.eventplannertask.feature_upcoming_events.presentation.state_and_actions.UpcomingEventsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UpcomingEventsViewModel(
    private val homeRepository: HomeRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(UpcomingEventsUiState())
    val uiState = _uiState
        .onStart {
            getUpcomingEvents()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UpcomingEventsUiState()
        )

    private fun getUpcomingEvents() = viewModelScope.launch {
        homeRepository.getUpcomingEvents().collectLatest { events ->
            _uiState.update {
                it.copy(events = events)
            }
        }
    }

    fun onUiAction(action: UpcomingEventsUiAction) {
        when (action) {
            is UpcomingEventsUiAction.OnDeleteEvent -> {
                deleteEvent(action.event)
            }
        }
    }

    private fun deleteEvent(event: EventData) = viewModelScope.launch {
        homeRepository.deleteEvent(event)
    }
}

class UpcomingEventsViewModelFactory(
    private val homeRepository: HomeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UpcomingEventsViewModel(homeRepository) as T
    }
}