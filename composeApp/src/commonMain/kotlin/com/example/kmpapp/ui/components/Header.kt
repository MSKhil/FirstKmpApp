package com.example.kmpapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kmpapp.ui.home.AlarmConfig

@Composable
fun Header(
    workName: String,
    month: String,
    dayCount: Int,
    alarmConfig: AlarmConfig?,
    onLogoutClick: () -> Unit,
    onSendSms: (phone: String, command: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Блок сигнализации
            if (alarmConfig != null) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(
                        onClick = { onSendSms(alarmConfig.phoneNumber, alarmConfig.disarmCommand) },
                        modifier = Modifier.background(Color(0xFFE8F5E9), CircleShape).size(40.dp)
                    ) {
                        Icon(Icons.Default.LockOpen, "Снять", tint = Color(0xFF2E7D32))
                    }
                    IconButton(
                        onClick = { onSendSms(alarmConfig.phoneNumber, alarmConfig.armCommand) },
                        modifier = Modifier.background(Color(0xFFFFEBEE), CircleShape).size(40.dp)
                    ) {
                        Icon(Icons.Default.Lock, "Поставить", tint = Color(0xFFC62828))
                    }
                }
            } else {
                Spacer(modifier = Modifier.width(1.dp))
            }

            TextButton(onClick = onLogoutClick) {
                Text("Выйти", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = workName, style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.onBackground)
        Column(modifier = Modifier.padding(top = 8.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(text = "Месяц: $month", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = "Кол-во смен: $dayCount", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}