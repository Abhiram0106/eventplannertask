package com.github.abhiram0106.eventplannertask.feature_home.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import com.github.abhiram0106.eventplannertask.R
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.Week
import com.kizitonwose.calendar.core.WeekDay
import kotlinx.coroutines.flow.filter
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@StringRes
val hours: List<Int> = listOf(
    R.string.one_am,
    R.string.two_am,
    R.string.three_am,
    R.string.four_am,
    R.string.five_am,
    R.string.six_am,
    R.string.seven_am,
    R.string.eight_am,
    R.string.nine_am,
    R.string.ten_am,
    R.string.eleven_am,
    R.string.twelve_am,
    R.string.one_pm,
    R.string.two_pm,
    R.string.three_pm,
    R.string.four_pm,
    R.string.five_pm,
    R.string.six_pm,
    R.string.seven_pm,
    R.string.eight_pm,
    R.string.nine_pm,
    R.string.ten_pm,
    R.string.eleven_pm,
    R.string.twelve_pm,
)

@Composable
fun rememberFirstVisibleWeekAfterScroll(state: WeekCalendarState): Week {
    val visibleWeek = remember(state) { mutableStateOf(state.firstVisibleWeek) }
    LaunchedEffect(state) {
        snapshotFlow { state.isScrollInProgress }
            .filter { scrolling -> !scrolling }
            .collect { visibleWeek.value = state.firstVisibleWeek }
    }
    return visibleWeek.value
}

@Composable
fun rememberFirstVisibleMonthAfterScroll(state: CalendarState): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    LaunchedEffect(state) {
        snapshotFlow { state.isScrollInProgress }
            .filter { scrolling -> !scrolling }
            .collect { visibleMonth.value = state.firstVisibleMonth }
    }
    return visibleMonth.value
}

fun List<WeekDay>.isSelectedDateInWeekDays(selectedDay: LocalDate): Boolean {
    this.forEach {
        if (selectedDay == it.date) return true
    }
    return false
}

fun DayOfWeek.narrowDisplayText(uppercase: Boolean = false): String {
    return getDisplayName(TextStyle.NARROW, Locale.ENGLISH).let { value ->
        if (uppercase) value.uppercase(Locale.ENGLISH) else value
    }
}

fun DayOfWeek.shortDisplayText(uppercase: Boolean = false): String {
    return getDisplayName(TextStyle.SHORT, Locale.ENGLISH).let { value ->
        if (uppercase) value.uppercase(Locale.ENGLISH) else value
    }
}