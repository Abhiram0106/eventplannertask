package com.github.abhiram0106.eventplannertask.feature_home.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.abhiram0106.eventplannertask.App
import com.github.abhiram0106.eventplannertask.core.presentation.UiText
import com.github.abhiram0106.eventplannertask.core.util.generateEventList
import com.github.abhiram0106.eventplannertask.core.util.toDisplayString
import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData
import com.github.abhiram0106.eventplannertask.feature_home.presentation.components.BookingListItemTimeIndicator
import com.github.abhiram0106.eventplannertask.feature_home.presentation.components.CalendarComponent
import com.github.abhiram0106.eventplannertask.feature_home.presentation.components.EventListCard
import com.github.abhiram0106.eventplannertask.feature_home.presentation.components.EventListItem
import com.github.abhiram0106.eventplannertask.feature_home.presentation.state_and_actions.HomeUiAction
import com.github.abhiram0106.eventplannertask.feature_home.presentation.state_and_actions.HomeUiState
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@Composable
fun HomeRoot(
    onShowSnackBar: suspend (message: UiText, actionLabel: UiText?) -> Boolean,
    viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(App.homeModule.homeRepository)
    )
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onUiAction = viewModel::onUiAction
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onUiAction: (HomeUiAction) -> Unit,
) {
    Column {
        CalendarComponent(
            selectedDate = uiState.selectedDate,
            datesWhereAtLeastOneEventExists = uiState.datesWhereAtLeastOneEventExists,
            onSelectDate = { onUiAction(HomeUiAction.OnSelectDate(it)) },
            onCurrentMonthChanged = { onUiAction(HomeUiAction.OnCurrentMonthChanged(it)) }
        )
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 4.dp),
        ) {
            uiState.events.forEach { time, eventList ->
                item {
                    BookingListItemTimeIndicator(
                        time = time.toDisplayString(),
                        modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                    )
                }
                items(
                    items = eventList,
                    key = { it.id }
                ) { event ->
                    EventListItem(
                        title = event.title,
                        description = event.description,
                        onClick = { onUiAction(HomeUiAction.OnSelectEvent(event.id)) },
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
        }
    }
}