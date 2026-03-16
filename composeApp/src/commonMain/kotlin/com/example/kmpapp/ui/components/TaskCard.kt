package com.example.a1gworkapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kmpapp.ui.home.DaySchedule
import kotlinx.coroutines.launch

@Composable
fun TaskCard(
    thisWeekSchedule: List<DaySchedule>,
    nextWeekSchedule: List<DaySchedule>
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val todayIndex = thisWeekSchedule.indexOfFirst { it.isToday }

    LaunchedEffect(thisWeekSchedule) {
        if (todayIndex >= 0) {
            coroutineScope.launch {
                listState.animateScrollToItem(index = todayIndex)
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//Заголовок Текущая неделя
            Text(
                text = "Текущая неделя",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            LazyRow(
                state = listState,
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(thisWeekSchedule.size) { index ->
                    val dayData = thisWeekSchedule[index]

                    DayScheduleCard(
                        dayName = dayData.dayName,
                        workers = dayData.workers,
                        workTimes = dayData.workTimes,
                        modifier = if (dayData.isToday) {
                            Modifier.border(
                                BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                                shape = RoundedCornerShape(24.dp)
                            )
                        } else {
                            Modifier
                        }
                    )
                }
            }
//Заголовок Следующая неделя
            Text(
                text = "Следующая неделя",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(nextWeekSchedule) { dayData ->
                    DayScheduleCard(
                        dayName = dayData.dayName,
                        workers = dayData.workers,
                        workTimes = dayData.workTimes
                    )
                }
            }
        }
    }
}
