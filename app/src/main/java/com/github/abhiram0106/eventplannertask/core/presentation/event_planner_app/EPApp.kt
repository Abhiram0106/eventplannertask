package com.github.abhiram0106.eventplannertask.core.presentation.event_planner_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.github.abhiram0106.eventplannertask.App
import com.github.abhiram0106.eventplannertask.ui.theme.AppTheme
import com.github.abhiram0106.eventplannertask.R
import com.github.abhiram0106.eventplannertask.core.navigation.EPNavHost
import com.github.abhiram0106.eventplannertask.core.presentation.components.EventBottomSheet
import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EPApp(
    appState: EPAppState = rememberAppState(),
) {

    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val bottomSheetState = rememberModalBottomSheetState()

    val scope = rememberCoroutineScope()

    var selectedEventData by remember {
        mutableStateOf<EventData?>(null)
    }
    val showBottomSheet: (EventData) -> Unit = { eventData ->
        selectedEventData = eventData
        scope.launch {
            bottomSheetState.show()
        }
    }

    LaunchedEffect(selectedEventData) {
        if (selectedEventData == null) {
            bottomSheetState.hide()
        }
    }

    AppTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            bottomBar = {
                BottomAppBar(
                    actions = {
                        appState.topLevelDestinations.forEach { destination ->
                            val isSelected =
                                appState.isBottomNavItemSelected(destination.baseRoute)

                            IconButton(onClick = {
                                appState.navigateToTopLevelDestination(destination)
                            }) {
                                Icon(
                                    painter = painterResource(destination.icon),
                                    contentDescription = destination.displayText.asString(),
                                    tint = if (isSelected) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onBackground
                                    }
                                )
                            }
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { scope.launch { showBottomSheet(EventData.blank()) } },
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
                EPNavHost(
                    navHostController = appState.navController,
                    onShowSnackBar = { message, actionLabel ->
                        snackBarHostState.showSnackbar(
                            message = message.asStringNonComposable(context),
                            actionLabel = actionLabel?.asStringNonComposable(context),
                        ) == SnackbarResult.ActionPerformed
                    },
                    onSelectEvent = { showBottomSheet(it) }
                )
                if (selectedEventData != null) {
                    EventBottomSheet(
                        sheetState = bottomSheetState,
                        eventData = selectedEventData!!,
                        onUpdateEventDate = { selectedEventData = it },
                        onDismiss = { selectedEventData = null },
                        onSave = {
                            scope.launch {
                                App.homeModule.homeRepository.upsertEvent(it)
                                selectedEventData = null
                            }
                        }
                    )
                }
            }
        }
    }
}