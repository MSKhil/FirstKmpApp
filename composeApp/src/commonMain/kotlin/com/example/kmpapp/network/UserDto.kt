package com.example.kmpapp.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("город") val city: String,
    @SerialName("магазин") val shop: String,
    @SerialName("сотрудник") val employeeName: String,
    @SerialName("пароль") val password: String,
    @SerialName("salary_spreadsheet_id") val salarySheetId: String,
    @SerialName("schedule_spreadsheet_id") val scheduleSheetId: String
)
