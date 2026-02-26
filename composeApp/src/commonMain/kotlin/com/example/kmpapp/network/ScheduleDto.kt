package com.example.kmpapp.network

import io.ktor.util.date.WeekDay
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleDto(
    @SerialName("week") val week: String,
    @SerialName("date") val date: String,
    @SerialName("day") val day: String,
    @SerialName("employee") val employee: String,
    @SerialName("workTime") val workTime: String,

)