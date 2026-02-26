package com.example.kmpapp.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    state: LoginUiState,
    onCityMenuExpandedChange: (Boolean) -> Unit,
    onShopMenuExpandedChange: (Boolean) -> Unit,
    onEmployeeMenuExpandedChange: (Boolean) -> Unit,
    onCitySelected: (String) -> Unit,
    onShopSelected: (String) -> Unit,
    onEmployeeSelected: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Лого
            Text(
                text = "1Galaxy",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(48.dp))

            //Меню выбора города
            ExposedDropdownMenuBox(
                expanded = state.isCityMenuExpanded,
                onExpandedChange = { onCityMenuExpandedChange(it) }
            ) {
                TextField(
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    value = state.selectedCity,
                    onValueChange = {},
                    label = { Text("Город") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.isCityMenuExpanded) },
                    shape = RoundedCornerShape(50),
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    textStyle = MaterialTheme.typography.labelLarge
                )
                ExposedDropdownMenu(
                    expanded = state.isCityMenuExpanded,
                    onDismissRequest = { onCityMenuExpandedChange(false) }
                ) {
                    state.cities.forEach { city ->
                        DropdownMenuItem(
                            text = { Text(text = city) },
                            onClick = {
                                onCitySelected(city)
                                onCityMenuExpandedChange(false)
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            //Меню выбора магазина
            ExposedDropdownMenuBox(
                expanded = state.isShopMenuExpanded,
                onExpandedChange = { onShopMenuExpandedChange(it) }
            ) {
                TextField(
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    value = state.selectedShop,
                    onValueChange = {},
                    label = { Text("Магазин") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.isShopMenuExpanded) },
                    shape = RoundedCornerShape(50),
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    textStyle = MaterialTheme.typography.labelLarge
                )

                ExposedDropdownMenu(
                    expanded = state.isShopMenuExpanded,
                    onDismissRequest = { onShopMenuExpandedChange(false) }
                ) {
                    state.shops.forEach { shop ->
                        DropdownMenuItem(
                            text = { Text(text = shop) },
                            onClick = {
                                onShopSelected(shop)
                                onShopMenuExpandedChange(false)
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            //Меню выбора сотрудника
            ExposedDropdownMenuBox(
                expanded = state.isEmployeeMenuExpanded,
                onExpandedChange = { onEmployeeMenuExpandedChange(it) }
            ) {
                TextField(
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    value = state.selectedEmployee,
                    onValueChange = {},
                    label = { Text("Сотрудник") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.isEmployeeMenuExpanded) },
                    shape = RoundedCornerShape(50),
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    textStyle = MaterialTheme.typography.labelLarge
                )
                ExposedDropdownMenu(
                    expanded = state.isEmployeeMenuExpanded,
                    onDismissRequest = { onEmployeeMenuExpandedChange(false) }
                ) {
                    state.employees.forEach { employee ->
                        DropdownMenuItem(
                            text = { Text(text = employee) },
                            onClick = {
                                onEmployeeSelected(employee)
                                onEmployeeMenuExpandedChange(false)
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            //Пароль
            TextField(
                value = state.password,
                onValueChange = { onPasswordChange(it) },
                label = { Text("Пароль") },
                shape = RoundedCornerShape(50),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.labelLarge,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onLoginClick,
                enabled = !state.isLoading,
                shape = RoundedCornerShape(50),
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Text(
                    text = "Войти",
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}