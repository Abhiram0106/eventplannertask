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
import com.github.abhiram0106.eventplannertask.core.presentation.UiText
import com.github.abhiram0106.eventplannertask.core.util.generateEventList
import com.github.abhiram0106.eventplannertask.core.util.toDisplayString
import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData
import com.github.abhiram0106.eventplannertask.feature_home.presentation.components.BookingListItemTimeIndicator
import com.github.abhiram0106.eventplannertask.feature_home.presentation.components.CalendarComponent
import com.github.abhiram0106.eventplannertask.feature_home.presentation.components.EventListCard
import com.github.abhiram0106.eventplannertask.feature_home.presentation.components.EventListItem
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@Composable
fun HomeRoot(
    onShowSnackBar: suspend (message: UiText, actionLabel: UiText?) -> Boolean,
) {
    HomeScreen(
        events = generateEventList().groupBy { it.time.truncatedTo(ChronoUnit.MINUTES) }
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    events: Map<LocalTime, List<EventData>>,
) {
    LaunchedEffect(Unit) {
        Log.e("HomeScreen", "events = $events")
    }
    Column {
        var selectedDate by remember {
            mutableStateOf(LocalDate.now())
        }
        CalendarComponent(
            selectedDate = selectedDate,
            datesWhereAtLeastOneEventExists = listOf(LocalDate.now()),
            onSelectDate = {
                selectedDate = it
                Log.e("HomeRoot", "select date = $it")
            },
            onCurrentMonthChanged = {
                Log.e("HomeRoot", "month changed = $it")
                // todo fetch datesWhereAtLeastOneEventExists
            }
        )
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 4.dp),
        ) {
            events.forEach { time, eventList ->
                item {
                    BookingListItemTimeIndicator(
                        time = time.toDisplayString(),
                        modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                    )
                }
                items(eventList) { event ->
                    EventListItem(
                        title = event.title,
                        description = event.description,
                        onClick = {},
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
        }
    }
}