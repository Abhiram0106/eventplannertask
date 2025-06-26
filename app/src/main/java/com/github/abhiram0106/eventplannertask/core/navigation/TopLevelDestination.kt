package com.github.abhiram0106.eventplannertask.core.navigation

import androidx.annotation.DrawableRes
import com.github.abhiram0106.eventplannertask.R
import com.github.abhiram0106.eventplannertask.core.presentation.UiText
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

enum class TopLevelDestination(
    @DrawableRes val icon: Int,
    val displayText: UiText,
    val route: @Serializable KClass<*>,
    val baseRoute: @Serializable KClass<*>
) {
    Home(
        icon = R.drawable.ic_calendar,
        displayText = UiText.StringResourceId(R.string.home),
        route = Screens.Home::class,
        baseRoute = Graphs.Home::class
    ),
    UPCOMING_EVENTS(
        icon = R.drawable.ic_calendar_upcoming,
        displayText = UiText.StringResourceId(R.string.upcoming),
        route = Screens.UpcomingEvents::class,
        baseRoute = Graphs.UpcomingEvents::class
    ),
}