package com.github.abhiram0106.eventplannertask.feature_home.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.github.abhiram0106.eventplannertask.core.presentation.UiText

@Composable
fun HomeRoot(
    onShowSnackBar: suspend (message: UiText, actionLabel: UiText?) -> Boolean,
) {
    Text(text = "home")
}