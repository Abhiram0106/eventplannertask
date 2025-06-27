package com.github.abhiram0106.eventplannertask.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.github.abhiram0106.eventplannertask.core.navigation.nav_graphs.homeGraph
import com.github.abhiram0106.eventplannertask.core.navigation.nav_graphs.upcomingEventsGraph
import com.github.abhiram0106.eventplannertask.core.presentation.UiText
import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData

@Composable
fun EPNavHost(
    navHostController: NavHostController,
    onShowSnackBar: suspend (message: UiText, actionLabel: UiText?) -> Boolean,
    onSelectEvent: suspend (EventData) -> Unit,
) {

    NavHost(
        navController = navHostController,
        startDestination = Graphs.Home,
        enterTransition = {
            fadeIn(animationSpec = tween(200)).plus(
                slideInHorizontally(
                    animationSpec = tween(200),
                    initialOffsetX = { fullWidth ->
                        fullWidth / 5
                    }
                )
            )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(200)).plus(
                slideOutHorizontally(
                    animationSpec = tween(200),
                    targetOffsetX = { fullWidth ->
                        -fullWidth / 5
                    }
                )
            )
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(200)).plus(
                slideInHorizontally(
                    animationSpec = tween(200),
                    initialOffsetX = { fullWidth ->
                        -fullWidth / 5
                    }
                )
            )
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(200)).plus(
                slideOutHorizontally(
                    animationSpec = tween(200),
                    targetOffsetX = { fullWidth ->
                        fullWidth / 5
                    }
                )
            )
        }
    ) {
        homeGraph(
            onShowSnackBar = onShowSnackBar,
            onSelectEvent = onSelectEvent
        )

        upcomingEventsGraph(onShowSnackBar = onShowSnackBar)
    }
}