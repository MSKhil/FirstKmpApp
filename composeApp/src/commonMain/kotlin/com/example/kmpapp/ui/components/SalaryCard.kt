package com.example.kmpapp.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Зарплата
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SalaryCard(
    salary: Int,
    cash: Int,
    card: Int,
    isHidden: Boolean,
    onToggleHidden: () -> Unit,
    onDoubleTap: () -> Unit
) {
    val blurRadius by animateDpAsState(targetValue = if (isHidden) 10.dp else 0.dp)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .combinedClickable(
                onClick = {},
                onLongClick = {},
                onDoubleClick = onDoubleTap
            ),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)
        ) {
            // Твой баланс
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Твой баланс:",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = onToggleHidden) {
                    Icon(
                        imageVector = if (isHidden) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "Toggle Balance",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = "$salary ₽",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.blur(blurRadius)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Наличные
            SalaryRow(
                icon = Icons.Default.AttachMoney,
                label = "Наличные:",
                amount = cash,
                isHidden = isHidden,
                blurRadius = blurRadius
            )

            Spacer(modifier = Modifier.height(8.dp))

            // На карту
            SalaryRow(
                icon = Icons.Default.CreditCard,
                label = "В зарплату:",
                amount = card,
                isHidden = isHidden,
                blurRadius = blurRadius
            )
        }
    }
}

@Composable
fun SalaryRow(
    icon: ImageVector,
    label: String,
    amount: Int,
    isHidden: Boolean,
    blurRadius: Dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(35.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = "$amount ₽",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.blur(blurRadius)
        )
    }
}