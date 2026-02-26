package com.example.kmpapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmpapp.data.UserCredentials
import com.example.kmpapp.data.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.kmpapp.network.KtorOneGalaxyApi
import com.example.kmpapp.network.UserDto

class LoginViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val apiService: KtorOneGalaxyApi
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private var allUsers: List<UserDto> = emptyList()

    init {
        tryAutoLogin()
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                allUsers = apiService.getUsers()
                val cities = allUsers.map { it.city }.distinct().filter { it.isNotBlank() }
                _uiState.update { it.copy(cities = cities, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Ошибка сети: ${e.message}") }
            }
        }
    }

    private fun tryAutoLogin() {
        viewModelScope.launch {
            val credentials = userPreferencesRepository.fetchInitialCredentials()

            if (credentials != null) {
                _uiState.update {
                    it.copy(
                        selectedCity = credentials.city,
                        selectedShop = credentials.shop,
                        selectedEmployee = credentials.employeeName,
                        password = credentials.password
                    )
                }

                val cachedUser = UserDto(
                    city = credentials.city,
                    shop = credentials.shop,
                    employeeName = credentials.employeeName,
                    password = credentials.password,
                    salarySheetId = credentials.salarySheetId,
                    scheduleSheetId = credentials.scheduleSheetId
                )

                _uiState.update {
                    it.copy(loginState = LoginState.SUCCESS, loggedInUser = cachedUser)
                }
            } else {
                _uiState.update { it.copy(loginState = LoginState.IDLE) }
            }
        }
    }

    fun login() {
        val currentState = _uiState.value
        val targetUser = allUsers.find {
            it.city == currentState.selectedCity &&
                    it.shop == currentState.selectedShop &&
                    it.employeeName == currentState.selectedEmployee
        }

        if (targetUser != null && targetUser.password == currentState.password) {
            viewModelScope.launch {
                userPreferencesRepository.saveCredentials(
                    UserCredentials(
                        city = targetUser.city,
                        shop = targetUser.shop,
                        employeeName = targetUser.employeeName,
                        password = targetUser.password,
                        salarySheetId = targetUser.salarySheetId,
                        scheduleSheetId = targetUser.scheduleSheetId
                    )
                )
                _uiState.update {
                    it.copy(loginState = LoginState.SUCCESS, loggedInUser = targetUser)
                }
            }
        } else {
            _uiState.update { it.copy(loginState = LoginState.FAILURE) }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferencesRepository.clearCredentials()
            _uiState.update { LoginUiState(cities = it.cities, loginState = LoginState.IDLE) }
        }
    }

    fun onCitySelected(city: String) {
        val shops = allUsers.filter { it.city == city }.map { it.shop }.distinct().filter { it.isNotBlank() }
        _uiState.update {
            it.copy(selectedCity = city, selectedShop = "", selectedEmployee = "", shops = shops, employees = emptyList(), isCityMenuExpanded = false, loginState = LoginState.IDLE)
        }
    }

    fun onShopSelected(shop: String) {
        val currentCity = _uiState.value.selectedCity
        val employees = allUsers.filter { it.city == currentCity && it.shop == shop }.map { it.employeeName }.distinct().filter { it.isNotBlank() }
        _uiState.update {
            it.copy(selectedShop = shop, selectedEmployee = "", employees = employees, isShopMenuExpanded = false, loginState = LoginState.IDLE)
        }
    }

    fun onEmployeeSelected(employee: String) {
        _uiState.update { it.copy(selectedEmployee = employee, isEmployeeMenuExpanded = false, loginState = LoginState.IDLE) }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword, loginState = LoginState.IDLE) }
    }

    fun onCityMenuExpandedChange(isExpanded: Boolean) = _uiState.update { it.copy(isCityMenuExpanded = isExpanded) }
    fun onShopMenuExpandedChange(isExpanded: Boolean) = _uiState.update { it.copy(isShopMenuExpanded = isExpanded) }
    fun onEmployeeMenuExpandedChange(isExpanded: Boolean) = _uiState.update { it.copy(isEmployeeMenuExpanded = isExpanded) }
}