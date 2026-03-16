package com.example.kmpapp.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.example.a1gworkapp.ui.components.TaskCard
import com.example.kmpapp.ui.components.Header
import com.example.kmpapp.ui.components.SalaryCard

@Composable
fun HomeScreen(
    state: HomeUiState,
    onRefresh: () -> Unit,
    onLogoutClick: () -> Unit,
    onToggleBalance: () -> Unit
) {
    val uriHandler = LocalUriHandler.current

    val motivationUrl = "https://docs.google.com/spreadsheets/d/1ynj_7wBLXgnFCaqLhr_OTuv61fm_reU0/edit?gid=1355084412#gid=1355084412"
    val actionsUrl = "https://docs.google.com/spreadsheets/d/1swtOJv0Lu5_Mu1USUltOeAg2FQFw4eiZTxLO3bTDo1s/edit?gid=0#gid=0"

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (state.currentSalary != null) {
                    val salary = state.currentSalary

                    Header(
                        workName = salary.employee,
                        month = salary.month,
                        dayCount = salary.workShift,
                        alarmConfig = state.alarmConfig,
                        onLogoutClick = onLogoutClick,
                        onSendSms = { phone, command ->
                            println("Попытка отправить СМС на $phone с текстом: $command")
                        }
                    )

                    SalaryCard(
                        salary = salary.salary.toInt(),
                        cash = salary.cash.toInt(),
                        card = salary.card.toInt(),
                        isHidden = state.isBalanceHidden,
                        onToggleHidden = onToggleBalance,
                        onDoubleTap = {
                            uriHandler.openUri("https://docs.google.com/spreadsheets/d/${state.salarySheetId}")
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    TaskCard(
                        thisWeekSchedule = state.thisWeekSchedule,
                        nextWeekSchedule = state.nextWeekSchedule,
                    )
                } else {
                    Spacer(modifier = Modifier.height(200.dp))
                    if (state.isRefreshing) {
                        Text("Загрузка данных...", modifier = Modifier.padding(16.dp), color = MaterialTheme.colorScheme.onSurface)
                    }
                }
                Spacer(modifier = Modifier.height(80.dp))
            }

            // Кнопки внизу
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FloatingBottomButton(
                    text = "Акции",
                    onclick = { uriHandler.openUri(actionsUrl) },
                    modifier = Modifier.weight(1f)
                )
                FloatingBottomButton(
                    text = "Мотивация",
                    onclick = { uriHandler.openUri(motivationUrl) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun FloatingBottomButton(
    text: String,
    onclick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onclick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f),
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Text(text = text)
    }
}