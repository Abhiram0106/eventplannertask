package com.github.abhiram0106.eventplannertask.feature_home.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.github.abhiram0106.eventplannertask.core.util.toDisplayText
import java.time.LocalDate

@Composable
fun DateTopBar(
    modifier: Modifier = Modifier,
    date: LocalDate,
    onSelectDate: (LocalDate) -> Unit
) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    Column {
        TextButton(
            shape = MaterialTheme.shapes.small,
            onClick = { isExpanded = !isExpanded },
        ) {
            Text(
                text = "${date.toDisplayText()}   ${if(isExpanded){"▲"}else{"▼"}}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }
        AnimatedVisibility(isExpanded) {
            Text("TODO Calendar")
        }
    }
}