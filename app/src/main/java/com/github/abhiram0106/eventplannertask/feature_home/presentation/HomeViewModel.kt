package com.github.abhiram0106.eventplannertask.feature_home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.github.abhiram0106.eventplannertask.feature_home.domain.HomeRepository
import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData
import com.github.abhiram0106.eventplannertask.feature_home.presentation.state_and_actions.HomeUiAction
import com.github.abhiram0106.eventplannertask.feature_home.presentation.state_and_actions.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState
        .onStart {
            observeSelectedMonth()
            observeSelectedDate()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            HomeUiState()
        )

    fun onUiAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.OnCurrentMonthChanged -> {
                _uiState.update {
                    it.copy(
                        scrolledMonth = action.month,
                        scrolledYear = action.year
                    )
                }
            }

            is HomeUiAction.OnSelectDate -> {
                _uiState.update {
                    it.copy(selectedDate = action.date)
                }
            }

            is HomeUiAction.OnDeleteEvent -> {
                deleteEvent(action.event)
            }
        }
    }

    private fun observeSelectedMonth() = viewModelScope.launch {

        combine(
            _uiState.map { it.scrolledMonth },
            _uiState.map { it.scrolledYear }
        ) { month, year ->
            month to year
        }.collectLatest { (month, year) ->
            homeRepository.getEventsByMonth(
                month = month,
                year = year
            ).collectLatest { dates ->
                _uiState.update {
                    it.copy(
                        datesWhereAtLeastOneEventExists = dates
                    )
                }
            }
        }
    }

    private fun observeSelectedDate() = viewModelScope.launch {
        _uiState.map { it.selectedDate }
            .collectLatest { date ->
                homeRepository.getEventsByDate(date).collectLatest { events ->
                    _uiState.update {
                        it.copy(events = events)
                    }
                }
            }
    }

    private fun deleteEvent(event: EventData) = viewModelScope.launch {
        homeRepository.deleteEvent(event)
    }
}

class HomeViewModelFactory(
    private val homeRepository: HomeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(homeRepository) as T
    }
}