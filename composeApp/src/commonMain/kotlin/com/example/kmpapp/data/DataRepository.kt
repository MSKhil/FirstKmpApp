package com.example.kmpapp.data

import com.example.kmpapp.network.KtorOneGalaxyApi
import com.example.kmpapp.network.SalaryDto
import com.example.kmpapp.network.ScheduleDto
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DataRepository (
    private val apiService: KtorOneGalaxyApi,
    private val settings: Settings
) {
    private val SALARY_KEY = "cached_salary_json"
    private val SCHEDULE_KEY = "cached_schedule_json"

    private val _salaryFlow = MutableStateFlow<List<SalaryDto>>(readSalaryFromDisk())
    private val _scheduleFlow = MutableStateFlow<List<ScheduleDto>>(readScheduleFromDisk())

    fun getSalaryStream(employeeName: String): Flow<List<SalaryDto>> = _salaryFlow.asStateFlow()
    fun getScheduleStream(): Flow<List<ScheduleDto>> = _scheduleFlow.asStateFlow()

    private fun readSalaryFromDisk(): List<SalaryDto>{
        val jsonString = settings.getStringOrNull(SALARY_KEY) ?: return emptyList()
        return try { Json.decodeFromString(jsonString) } catch (e: Exception) { emptyList() }
    }

    private fun readScheduleFromDisk(): List<ScheduleDto> {
        val jsonString = settings.getStringOrNull(SCHEDULE_KEY) ?: return emptyList()
        return try { Json.decodeFromString(jsonString) } catch (e: Exception) { emptyList() }
    }

    suspend fun refreshData(salarySheetId: String, scheduleSheetId: String, employeeName: String): Boolean {
        return try {
            val freshSalaries = apiService.getSalary(salarySheetId, employeeName)
            val freshSchedule = apiService.getSchedule(scheduleSheetId)

            settings.putString(SALARY_KEY, Json.encodeToString(freshSalaries))
            settings.putString(SCHEDULE_KEY, Json.encodeToString(freshSchedule))

            _salaryFlow.value = freshSalaries
            _scheduleFlow.value = freshSchedule

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun clearData() {
        settings.remove(SALARY_KEY)
        settings.remove(SCHEDULE_KEY)
        _salaryFlow.value = emptyList()
        _scheduleFlow.value = emptyList()
    }
}