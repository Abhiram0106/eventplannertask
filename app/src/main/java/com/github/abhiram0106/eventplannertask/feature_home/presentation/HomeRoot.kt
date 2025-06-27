package com.github.abhiram0106.eventplannertask.feature_home.presentation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.abhiram0106.eventplannertask.App
import com.github.abhiram0106.eventplannertask.R
import com.github.abhiram0106.eventplannertask.core.presentation.UiText
import com.github.abhiram0106.eventplannertask.core.util.toDisplayString
import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData
import com.github.abhiram0106.eventplannertask.feature_home.presentation.components.BookingListItemTimeIndicator
import com.github.abhiram0106.eventplannertask.feature_home.presentation.components.CalendarComponent
import com.github.abhiram0106.eventplannertask.feature_home.presentation.components.EventListItem
import com.github.abhiram0106.eventplannertask.feature_home.presentation.state_and_actions.HomeUiAction
import com.github.abhiram0106.eventplannertask.feature_home.presentation.state_and_actions.HomeUiState
import kotlinx.coroutines.launch

@Composable
fun HomeRoot(
    viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(App.homeModule.homeRepository)
    ),
    onSelectEvent: suspend (EventData) -> Unit,
    onShowSnackBar: suspend (message: UiText, actionLabel: UiText?) -> Boolean,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onUiAction = viewModel::onUiAction,
        onSelectEvent = onSelectEvent,
        onShowSnackBar = onShowSnackBar
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onUiAction: (HomeUiAction) -> Unit,
    onSelectEvent: suspend (EventData) -> Unit,
    onShowSnackBar: suspend (message: UiText, actionLabel: UiText?) -> Boolean,
) {

    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.scrolledMonth, uiState.selectedDate) {
        Log.e(
            "HomeScreen",
            "selectedDate = ${uiState.selectedDate}, selectedMonth = ${uiState.scrolledMonth}"
        )
    }
    LaunchedEffect(uiState.datesWhereAtLeastOneEventExists.size) {
        Log.e("HomeScreen", "datesWithEvents = ${uiState.datesWhereAtLeastOneEventExists}")
    }
    Column {
        CalendarComponent(
            selectedDate = uiState.selectedDate,
            datesWhereAtLeastOneEventExists = uiState.datesWhereAtLeastOneEventExists,
            onSelectDate = { onUiAction(HomeUiAction.OnSelectDate(it)) },
            onCurrentMonthChanged = { month, year ->
                onUiAction(HomeUiAction.OnCurrentMonthChanged(month = month, year = year))
            }
        )
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 4.dp),
        ) {
            if (uiState.events.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.no_events_tap_on_plus_button_to_create_one),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.6F),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                    )
                }
            } else {
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
                            onClick = { scope.launch { onSelectEvent(event) } },
                            onDeleteItem = {
                                scope.launch {
                                    val confirm= onShowSnackBar(
                                        UiText.StringResourceId(R.string.confirm_delete),
                                        UiText.StringResourceId(R.string.delete),
                                    )

                                    if (confirm) {
                                        onUiAction(HomeUiAction.OnDeleteEvent(event))
                                    }
                                }
                            },
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
            }
        }
    }
}