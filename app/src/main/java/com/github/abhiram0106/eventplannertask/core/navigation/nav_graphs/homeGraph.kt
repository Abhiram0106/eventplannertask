package com.github.abhiram0106.eventplannertask.core.navigation.nav_graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.github.abhiram0106.eventplannertask.core.navigation.Graphs
import com.github.abhiram0106.eventplannertask.core.navigation.Screens
import com.github.abhiram0106.eventplannertask.core.presentation.UiText
import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData
import com.github.abhiram0106.eventplannertask.feature_home.presentation.HomeRoot

fun NavGraphBuilder.homeGraph(
    onShowSnackBar: suspend (message: UiText, actionLabel: UiText?) -> Boolean,
    onSelectEvent: suspend (EventData) -> Unit,
) {

    navigation<Graphs.Home>(startDestination = Screens.Home) {
        composable<Screens.Home> {
            HomeRoot(
                onSelectEvent = onSelectEvent,
                onShowSnackBar = onShowSnackBar
            )
        }
    }
}