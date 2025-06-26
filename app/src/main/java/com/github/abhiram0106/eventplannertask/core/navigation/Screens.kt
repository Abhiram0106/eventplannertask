package com.github.abhiram0106.eventplannertask.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Graphs {
    @Serializable
    data object Home : Graphs()

    @Serializable
    data object UpcomingEvents : Graphs()
}

@Serializable
sealed class Screens {
    @Serializable
    data object Home : Screens()

    @Serializable
    data object UpcomingEvents : Screens()
}
