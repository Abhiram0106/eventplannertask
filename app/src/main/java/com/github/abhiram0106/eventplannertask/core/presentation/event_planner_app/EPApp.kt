package com.github.abhiram0106.eventplannertask.core.presentation.event_planner_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.github.abhiram0106.eventplannertask.ui.theme.EventPlannerTaskTheme
import com.github.abhiram0106.eventplannertask.R

@Composable
fun EPApp(
    appState: EPAppState = rememberAppState(),
) {

    val snackBarHostState = remember { SnackbarHostState() }

    EventPlannerTaskTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            bottomBar = {
                BottomAppBar(
                    actions = {
                        appState.topLevelDestinations.forEach { destination ->
                            IconButton(onClick = {
                                appState.navigateToTopLevelDestination(destination)
                            }) {
                                Icon(
                                    painter = painterResource(destination.icon),
                                    contentDescription = destination.displayText.asString()
                                )
                            }
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { /* TODO : open bottom sheet */ },
                            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_add),
                                contentDescription = stringResource(R.string.create_event)
                            )
                        }
                    }
                )
            },
            contentWindowInsets = WindowInsets.safeDrawing
        ) { paddingValues ->
            Box(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(paddingValues)
            ) {

            }
        }
    }
}