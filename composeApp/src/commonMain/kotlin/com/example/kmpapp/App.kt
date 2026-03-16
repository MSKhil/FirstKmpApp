package com.example.kmpapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kmpapp.ui.home.HomeScreen
import com.example.kmpapp.ui.home.HomeViewModel
import com.example.kmpapp.ui.login.LoginScreen
import com.example.kmpapp.ui.login.LoginState
import com.example.kmpapp.ui.login.LoginViewModel
import com.example.kmpapp.ui.theme._1GWorkAppTheme
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject

@Serializable
object LoginRoute

@Serializable
object HomeRoute

@Composable
fun App() {
    _1GWorkAppTheme {
        val navController = rememberNavController()
        val loginViewModel = koinInject<LoginViewModel>()
        val homeViewModel = koinInject<HomeViewModel>()
        val loginUiState by loginViewModel.uiState.collectAsState()
        val isReady = loginUiState.loginState != LoginState.LOADING_AUTO

        LaunchedEffect(loginUiState.loggedInUser) {
            loginUiState.loggedInUser?.let { user ->
                if (user.salarySheetId.isNotBlank() && user.scheduleSheetId.isNotBlank()) {
                    homeViewModel.start(
                        salarySheetId = user.salarySheetId,
                        scheduleSheetId = user.scheduleSheetId,
                        employeeName = user.employeeName,
                    )
                }
            }
        }

        if (isReady) {
            val startDest = if (loginUiState.loginState == LoginState.SUCCESS) HomeRoute else LoginRoute

            NavHost(
                navController = navController,
                startDestination = startDest
            ) {
                composable<LoginRoute> {
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
                    LaunchedEffect(loginUiState.loginState) {
                        if (loginUiState.loginState == LoginState.SUCCESS) {
                            navController.navigate(HomeRoute) {
                                popUpTo<LoginRoute> { inclusive = true }
                            }
                        }
                    }
                }

                composable<HomeRoute> {
                    val homeUiState by homeViewModel.uiState.collectAsState()

                    HomeScreen(
                        state = homeUiState,
                        onRefresh = homeViewModel::refresh,
                        onLogoutClick = {
                            loginViewModel.logout()
                            navController.navigate(LoginRoute) {
                                popUpTo<HomeRoute> { inclusive = true }
                            }
                        },
                        onToggleBalance = homeViewModel::toggleBalanceVisibility
                    )
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background))
        }
    }
}