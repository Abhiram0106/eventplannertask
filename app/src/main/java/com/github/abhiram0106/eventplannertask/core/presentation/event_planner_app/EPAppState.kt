package com.github.abhiram0106.eventplannertask.core.presentation.event_planner_app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.github.abhiram0106.eventplannertask.core.navigation.Graphs
import com.github.abhiram0106.eventplannertask.core.navigation.TopLevelDestination
import kotlin.reflect.KClass

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
): EPAppState {
    return remember(navController) {
        EPAppState(navController)
    }
}

class EPAppState(
    val navController: NavHostController
) {

    private val previousDestination = mutableStateOf<NavDestination?>(null)
    val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)

            // Fallback to previousDestination if currentEntry is null
            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val topLevelDestinations: Array<TopLevelDestination> =
        TopLevelDestination.entries.toTypedArray()

    @Composable
    fun isBottomNavItemSelected(route: KClass<*>): Boolean {
        return currentDestination.isRouteInHierarchy(route)
    }

//    @Composable
//    fun showBottomBar(): Boolean {
//        return topLevelDestinations.firstOrNull { dest ->
//            currentDestination?.hasTopScreenAs(dest.route) == true
//        } != null
//    }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        if (navController.currentDestination.hasTopScreenAs(topLevelDestination.route)) {
            return
        }

        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
//             Restore state when reselecting a previously selected item
            restoreState = true
        }

        navController.navigate(
            route = when (topLevelDestination) {
                TopLevelDestination.Home -> {
                    Graphs.Home
                }

                TopLevelDestination.UPCOMING_EVENTS -> {
                    Graphs.UpcomingEvents
                }
            },
            navOptions = topLevelNavOptions
        )
    }

    private fun NavDestination?.hasTopScreenAs(route: KClass<*>): Boolean {
        val topDestination = this?.hierarchy?.firstOrNull() ?: return false
        return topDestination.hasRoute(route)
    }

    private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
        this?.hierarchy?.any { dest ->
            dest.hasRoute(route)
        } == true
}