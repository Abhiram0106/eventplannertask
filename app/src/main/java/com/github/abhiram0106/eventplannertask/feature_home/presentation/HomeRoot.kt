package com.github.abhiram0106.eventplannertask.feature_home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.abhiram0106.eventplannertask.core.presentation.UiText
import com.github.abhiram0106.eventplannertask.core.util.generateEventList
import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData
import com.github.abhiram0106.eventplannertask.feature_home.presentation.components.EventListCard

@Composable
fun HomeRoot(
    onShowSnackBar: suspend (message: UiText, actionLabel: UiText?) -> Boolean,
) {
    HomeScreen(
        events = generateEventList()
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    events: List<EventData>,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = events
        ) { event ->
            EventListCard(
                time = event.time,
                title = event.title,
                description = event.description,
                onClick = {}
            )
        }
    }
}