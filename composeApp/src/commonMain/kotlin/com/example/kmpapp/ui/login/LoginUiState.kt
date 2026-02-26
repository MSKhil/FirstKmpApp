package com.example.kmpapp.ui.login

import com.example.kmpapp.network.UserDto

data class LoginUiState(
    val cities: List<String> = emptyList(),
    val shops: List<String> = emptyList(),
    val employees: List<String> = emptyList(),

    val selectedCity: String = "",
    val selectedShop: String = "",
    val selectedEmployee: String = "",
    val password: String = "",

    val isCityMenuExpanded: Boolean = false,
    val isShopMenuExpanded: Boolean = false,
    val isEmployeeMenuExpanded: Boolean = false,

    val isLoading: Boolean = false,
    val loginState: LoginState = LoginState.LOADING_AUTO,
    val errorMessage: String? = null,

    val loggedInUser: UserDto? = null
)

enum class LoginState {
    IDLE,
    LOADING_AUTO,
    SUCCESS,
    FAILURE
}