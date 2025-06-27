package com.github.abhiram0106.eventplannertask.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.TimePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.github.abhiram0106.eventplannertask.R
import com.github.abhiram0106.eventplannertask.core.util.toDisplayString
import com.github.abhiram0106.eventplannertask.core.util.toLocalDate
import com.github.abhiram0106.eventplannertask.feature_home.domain.model.EventData
import java.time.LocalTime
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    eventData: EventData,
    onUpdateEventDate: (EventData) -> Unit,
    onSave: (EventData) -> Unit,
    onDismiss: () -> Unit
) {

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    var shouldShowDatePicker by remember {
        mutableStateOf(false)
    }

    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
    )
    var shouldShowTimePicker by remember {
        mutableStateOf(false)
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onDismiss
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = stringResource(R.string.close)
                    )
                }

                Button(
                    onClick = { onSave(eventData) }
                ) {
                    Text(
                        text = stringResource(R.string.save),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            OutlinedTextField(
                value = eventData.title,
                onValueChange = {
                    onUpdateEventDate(eventData.copy(title = it))
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                label = {
                    Text(
                        text = stringResource(R.string.title),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = eventData.description,
                onValueChange = {
                    onUpdateEventDate(eventData.copy(description = it))
                },
                textStyle = MaterialTheme.typography.bodyMedium,
                label = {
                    Text(
                        text = stringResource(R.string.description),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.select_date_and_time),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = { shouldShowDatePicker = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = eventData.date.toDisplayString(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                TextButton(
                    onClick = { shouldShowTimePicker = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = eventData.time.toDisplayString(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        if (shouldShowDatePicker) {
            DatePickerDialog(
                onDismissRequest = { shouldShowDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onUpdateEventDate(eventData.copy(date = datePickerState.selectedDateMillis.toLocalDate()))
                            shouldShowDatePicker = false
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.ok),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { shouldShowDatePicker = false }
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        if (shouldShowTimePicker) {
            DatePickerDialog(
                onDismissRequest = { shouldShowTimePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onUpdateEventDate(
                                eventData.copy(
                                    time = LocalTime.of(
                                        timePickerState.hour,
                                        timePickerState.minute
                                    )
                                )
                            )
                            shouldShowTimePicker = false
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.ok),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { shouldShowTimePicker = false }
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    TimePicker(state = timePickerState)
                }
            }
        }
    }
}