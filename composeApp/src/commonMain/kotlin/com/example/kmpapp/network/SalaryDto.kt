package com.example.kmpapp.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SalaryDto(
    @SerialName("month") val month: String,
    @SerialName("employee") val employee: String,
    @SerialName("salaryFact") val salary: Double,
    @SerialName("cashFact") val cash: Double,
    @SerialName("cardFact") val card: Double,
    @SerialName("workShift") val workShift: Int
)