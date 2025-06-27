package com.github.abhiram0106.eventplannertask.feature_home.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.abhiram0106.eventplannertask.feature_home.util.isSelectedDateInWeekDays
import com.github.abhiram0106.eventplannertask.feature_home.util.narrowDisplayText
import com.github.abhiram0106.eventplannertask.feature_home.util.rememberFirstVisibleMonthAfterScroll
import com.github.abhiram0106.eventplannertask.feature_home.util.rememberFirstVisibleWeekAfterScroll
import com.github.abhiram0106.eventplannertask.feature_home.util.shortDisplayText
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.kizitonwose.calendar.core.yearMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CalendarComponent(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    datesWhereAtLeastOneEventExists: List<LocalDate>,
    onSelectDate: (LocalDate) -> Unit,
    onCurrentMonthChanged: (monthNumber: Int, year: Int) -> Unit
) {

    val currentDate = rememberSaveable { selectedDate }
    val startDate = remember { currentDate.minusDays(500) }
    val endDate = remember { currentDate.plusDays(500) }

    val currentMonth = remember(currentDate) { currentDate.yearMonth }
    val startMonth = remember { currentMonth.minusMonths(500L) }
    val endMonth = remember { currentMonth.plusMonths(500L) }
    val daysOfWeek = remember { daysOfWeek() }

    var selection by rememberSaveable { mutableStateOf(currentDate) }
    var isWeekMode by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(key1 = selection) { onSelectDate(selection) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
    ) {
        val coroutineScope = rememberCoroutineScope()

        val weekState = rememberWeekCalendarState(
            startDate = startDate,
            endDate = endDate,
            firstVisibleWeekDate = currentDate,
        )
        val visibleWeek = rememberFirstVisibleWeekAfterScroll(state = weekState)

        val monthState = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first()
        )
        val visibleMonth = rememberFirstVisibleMonthAfterScroll(state = monthState)

        LaunchedEffect(
            key1 = visibleMonth.yearMonth.monthValue,
            key2 = visibleMonth.yearMonth.year
        ) {
            onCurrentMonthChanged(
                visibleMonth.yearMonth.monthValue,
                visibleMonth.yearMonth.year
            )
        }

        SimpleCalendarTitle(
            modifier = modifier.padding(vertical = 10.dp, horizontal = 8.dp),
            currentMonth = if (isWeekMode) {
                if (visibleWeek.days.isSelectedDateInWeekDays(selectedDay = selection)) selection.yearMonth else visibleWeek.days.first().date.yearMonth
            } else {
                visibleMonth.yearMonth
            },
            goToPrevious = {
                coroutineScope.launch {
                    if (isWeekMode) {
                        val targetDate = weekState.firstVisibleWeek.days.first().date.minusDays(1)
                        weekState.animateScrollToWeek(targetDate)
                    } else {
                        val targetMonth = monthState.firstVisibleMonth.yearMonth.previousMonth
                        monthState.animateScrollToMonth(targetMonth)
                    }
                }
            },
            goToNext = {
                coroutineScope.launch {
                    if (isWeekMode) {
                        val targetDate = weekState.firstVisibleWeek.days.last().date.plusDays(1)
                        weekState.animateScrollToWeek(targetDate)
                    } else {
                        val targetMonth = monthState.firstVisibleMonth.yearMonth.nextMonth
                        monthState.animateScrollToMonth(targetMonth)
                    }
                }
            },
            isWeekMode = isWeekMode,
            toggleWeekMode = {
                isWeekMode = !it
                coroutineScope.launch {
                    weekState.animateScrollToWeek(selectedDate)
                    monthState.animateScrollToMonth(selectedDate.yearMonth)
                }
            }
        )

        AnimatedVisibility(visible = isWeekMode) {
            WeekCalendar(
                state = weekState,
                dayContent = { day ->
                    WeekCalendarDay(
                        date = day.date,
                        atLeastOneEventExists = datesWhereAtLeastOneEventExists.contains(day.date),
                        isSelected = selection == day.date
                    ) { clicked ->
                        if (selection != clicked) {
                            selection = clicked
                        }
                    }
                },
                modifier = modifier
            )
        }
        AnimatedVisibility(visible = !isWeekMode) {

            Column {
                CalendarHeader(daysOfWeek = daysOfWeek, modifier = modifier)
                HorizontalCalendar(
                    state = monthState,
                    dayContent = { day ->
                        val isSelectable = day.position == DayPosition.MonthDate
                        MonthCalendarDay(
                            day = day.date,
                            isSelected = isSelectable && selection == day.date,
                            isSelectable = isSelectable,
                            atLeastOneEventExists = datesWhereAtLeastOneEventExists.contains(
                                day.date
                            ),
                        ) { clicked ->
                            if (selection != clicked) {
                                selection = clicked
                            }
                        }
                    },
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
private fun WeekCalendarDay(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isSelected: Boolean,
    atLeastOneEventExists: Boolean,
    onClick: (LocalDate) -> Unit
) {

    val dotColor = MaterialTheme.colorScheme.primary

    Column {
        Box(
            modifier = modifier
                .padding(4.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(40))
                .background(if (isSelected) dotColor.copy(alpha = 0.1F) else Color.Transparent)
                .clickable { onClick(date) },
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = modifier.padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                            append("${date.dayOfWeek.narrowDisplayText()}\n")
                        }
                        append(dateFormatter.format(date))
                    },
                    fontSize = 14.sp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = modifier.height(8.dp))

                if (atLeastOneEventExists) {
                    Canvas(modifier = Modifier.size(16.dp)) {
                        drawCircle(color = dotColor)
                    }
                }
            }
        }

        if (!atLeastOneEventExists) {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

private val dateFormatter = DateTimeFormatter.ofPattern("dd")

@Composable
fun MonthCalendarDay(
    day: LocalDate,
    isSelected: Boolean,
    isSelectable: Boolean,
    atLeastOneEventExists: Boolean,
    selectedDotColor: Color = MaterialTheme.colorScheme.primary,
    unSelectedDotColor: Color = MaterialTheme.colorScheme.onBackground,
    onClick: (LocalDate) -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(6.dp)
            .clip(CircleShape)
            .background(color = if (isSelected) selectedDotColor.copy(0.1F) else Color.Transparent)
            .clickable(
                enabled = isSelectable,
                onClick = { onClick(day) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        val textColor = when {
            isSelected -> MaterialTheme.colorScheme.primary
            isSelectable -> MaterialTheme.colorScheme.onBackground
            else -> MaterialTheme.colorScheme.error
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = day.dayOfMonth.toString(),
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            if (atLeastOneEventExists) {
                Canvas(modifier = Modifier.size(4.dp)) {
                    drawCircle(
                        color = if (isSelected) selectedDotColor else unSelectedDotColor
                    )
                }
            }
        }
    }
}

@Composable
fun CalendarHeader(modifier: Modifier = Modifier, daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                text = dayOfWeek.shortDisplayText(uppercase = true),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}