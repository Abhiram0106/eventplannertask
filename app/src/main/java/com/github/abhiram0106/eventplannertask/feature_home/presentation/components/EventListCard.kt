package com.github.abhiram0106.eventplannertask.feature_home.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.abhiram0106.eventplannertask.core.util.toDisplayString
import java.time.LocalTime

@Composable
fun EventListCard(
    modifier: Modifier = Modifier,
    time: LocalTime,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            EventListItem(
                title = title,
                description = description,
                onClick = onClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}


@Composable
fun BookingListItemTimeIndicator(
    modifier: Modifier = Modifier,
    time: String,
    lineColor: Color = MaterialTheme.colorScheme.primary
) {

    val pathEffect = remember { PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = time,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.width(10.dp))

        Canvas(Modifier.fillMaxWidth()) {
            drawLine(
                color = lineColor,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                pathEffect = pathEffect,
                strokeWidth = 3f
            )
        }
    }
}

@Composable
fun EventListItem(
    modifier: Modifier = Modifier,
    time: String? = null,
    title: String,
    description: String,
    onClick: () -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        shape = MaterialTheme.shapes.extraSmall,
        onClick = onClick,
        modifier = modifier.fillMaxWidth().padding(horizontal = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 6.dp)
        ) {
            if (time != null) {
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(3.dp))
            }
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}