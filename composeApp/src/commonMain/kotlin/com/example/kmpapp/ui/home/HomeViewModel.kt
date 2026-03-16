package com.example.kmpapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmpapp.data.DataRepository
import com.example.kmpapp.data.UserPreferencesRepository
import com.example.kmpapp.network.ScheduleDto
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ScheduleUiState(
    val thisWeekSchedule: List<DaySchedule> = emptyList(),
    val nextWeekSchedule: List<DaySchedule> = emptyList()
)

data class DaySchedule(
    val dayName: String,
    val date: String,
    val workers: List<String>,
    val workTimes: List<String>,
    val isToday: Boolean = false
)

data class AlarmConfig(
    val phoneNumber: String,
    val armCommand: String,
    val disarmCommand: String
)

class HomeViewModel(
    private val repository: DataRepository,
    private val userPrefs: UserPreferencesRepository
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _currentUserParams = MutableStateFlow<UserParams?>(null)

    val uiState: StateFlow<HomeUiState> = _currentUserParams
        .flatMapLatest { params ->
            if (params == null) {
                flowOf(HomeUiState())
            } else {
                combine(
                    repository.getSalaryStream(params.employeeName),
                    repository.getScheduleStream(),
                    _isRefreshing,
                    _errorMessage
                ) { salaries, rawSchedule, refreshing, error ->
                    val currentSalary = salaries.lastOrNull()
                    val scheduleUiState = processSchedule(rawSchedule)
                    val config = getAlarmConfig(params.employeeName)

                    HomeUiState(
                        currentSalary = currentSalary,
                        thisWeekSchedule = scheduleUiState.thisWeekSchedule,
                        nextWeekSchedule = scheduleUiState.nextWeekSchedule,
                        isRefreshing = refreshing,
                        errorMessage = error,
                        employeeName = params.employeeName,
                        isBalanceHidden = false,
                        salarySheetId = params.salarySheetId,
                        scheduleSheetId = params.scheduleSheetId,
                        alarmConfig = config
                    )
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )

    fun start(salarySheetId: String, scheduleSheetId: String, employeeName: String) {
        val newParams = UserParams(salarySheetId, scheduleSheetId, employeeName)
        if (_currentUserParams.value == newParams) return
        _currentUserParams.value = newParams
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _currentUserParams.value?.let { params ->
                _isRefreshing.value = true
                _errorMessage.value = null

                val success = repository.refreshData(
                    params.salarySheetId,
                    params.scheduleSheetId,
                    params.employeeName
                )

                if (!success) {
                    _errorMessage.value = "Ошибка обновления данных"
                }

                _isRefreshing.value = false
            }
        }
    }

    private fun getAlarmConfig(name: String): AlarmConfig? {
        return when {
            name.equals("ХИЛЬ", ignoreCase = true) -> AlarmConfig(
                phoneNumber = "+79165692278",
                armCommand = "11",
                disarmCommand = "12"
            )
            name.equals("ДРЕВЕТНЯК", ignoreCase = true) -> AlarmConfig(
                phoneNumber = "+79150602841",
                armCommand = "6045 11 1",
                disarmCommand = "6045 12 1"
            )
            else -> null
        }
    }

    private fun processSchedule(rawScheduleList: List<ScheduleDto>): ScheduleUiState {

        val groupedByWeek = rawScheduleList.groupBy { it.week.trim().uppercase() }
        val thisWeek = groupedByWeek["ТЕКУЩАЯ НЕДЕЛЯ"] ?: emptyList()
        val nextWeek = groupedByWeek["НЕКСТ НЕДЕЛЯ"] ?: emptyList()

        return ScheduleUiState(
            thisWeekSchedule = groupScheduleByDay(thisWeek),
            nextWeekSchedule = groupScheduleByDay(nextWeek)
        )
    }

    private fun groupScheduleByDay(scheduleList: List<ScheduleDto>): List<DaySchedule> {
        val groupedByDate = scheduleList.groupBy { it.date }
        return groupedByDate.map { (_, entries) ->
            val firstEntry = entries.first()
            DaySchedule(
                dayName = firstEntry.day,
                date = firstEntry.date,
                workers = entries.map { it.employee },
                workTimes = entries.map { it.workTime },
                isToday = false
            )
        }.sortedBy { it.date }
    }

    private data class UserParams(val salarySheetId: String, val scheduleSheetId: String, val employeeName: String)

    fun toggleBalanceVisibility() {
        println("Клик по глазику! (Логику сохранения допишем позже)")
    }
}