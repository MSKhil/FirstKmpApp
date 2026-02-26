package com.example.kmpapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.kmpapp.ui.login.LoginScreen
import com.example.kmpapp.ui.login.LoginViewModel
import com.example.kmpapp.ui.theme._1GWorkAppTheme
import org.koin.compose.koinInject

@Composable
fun App() {
    _1GWorkAppTheme {
        val loginViewModel = koinInject<LoginViewModel>()

        val loginUiState by loginViewModel.uiState.collectAsState()

        LoginScreen(
            state = loginUiState,
            onCityMenuExpandedChange = loginViewModel::onCityMenuExpandedChange,
            onShopMenuExpandedChange = loginViewModel::onShopMenuExpandedChange,
            onEmployeeMenuExpandedChange = loginViewModel::onEmployeeMenuExpandedChange,
            onCitySelected = loginViewModel::onCitySelected,
            onShopSelected = loginViewModel::onShopSelected,
            onEmployeeSelected = loginViewModel::onEmployeeSelected,
            onPasswordChange = loginViewModel::onPasswordChange,
            onLoginClick = loginViewModel::login
        )
    }
}