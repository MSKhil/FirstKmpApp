package com.example.kmpapp.ui.home

import com.example.kmpapp.network.SalaryDto

data class HomeUiState(
    val currentSalary: SalaryDto? = null,
    val thisWeekSchedule: List<DaySchedule> = emptyList(),
    val nextWeekSchedule: List<DaySchedule> = emptyList(),
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val employeeName: String = "",
    val isBalanceHidden: Boolean = false,
    val salarySheetId: String = "",
    val scheduleSheetId: String = "",
    val alarmConfig: AlarmConfig? = null
)