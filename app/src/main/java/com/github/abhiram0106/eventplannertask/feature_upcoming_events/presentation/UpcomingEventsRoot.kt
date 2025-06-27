package com.github.abhiram0106.eventplannertask.feature_upcoming_events.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.abhiram0106.eventplannertask.core.presentation.UiText

@Composable
fun UpcomingEventsRoot(
    onShowSnackBar: suspend (message: UiText, actionLabel: UiText?) -> Boolean,
) {
    Text(text = "upcoming")
}

@Composable
fun UpcomingEventsScreen(
    modifier: Modifier = Modifier
) {

}