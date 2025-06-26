package com.github.abhiram0106.eventplannertask.feature_home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.abhiram0106.eventplannertask.R
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle

@Composable
fun SimpleCalendarTitle(
    modifier: Modifier = Modifier,
    currentMonth: YearMonth,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
    isWeekMode: Boolean,
    toggleWeekMode: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .testTag("MonthTitle"),
            text = "${currentMonth.month.getDisplayName(TextStyle.SHORT, java.util.Locale.US)} ${currentMonth.format(DateTimeFormatter.ofPattern("yy"))}",
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            IconButton(onClick = { toggleWeekMode(isWeekMode) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = "Toggle week or month mode",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            CalendarNavigationIcon(
                icon = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = "Previous",
                onClick = goToPrevious,
                modifier = Modifier.size(32.dp)
            )
            CalendarNavigationIcon(
                icon = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "Next",
                onClick = goToNext,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun CalendarNavigationIcon(
    modifier: Modifier = Modifier,
    icon: Painter,
    tint: Color = LocalContentColor.current,
    contentDescription: String,
    onClick: () -> Unit,
) = Box(
    modifier = modifier
        .fillMaxHeight()
        .aspectRatio(1f)
        .clip(shape = CircleShape)
        .clickable(role = Role.Button, onClick = onClick),
) {
    Icon(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
            .align(Alignment.Center),
        painter = icon,
        tint = tint,
        contentDescription = contentDescription,
    )
}