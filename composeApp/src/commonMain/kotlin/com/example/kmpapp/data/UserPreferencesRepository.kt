package com.example.kmpapp.data

import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class UserCredentials(
    val city: String,
    val shop: String,
    val employeeName: String,
    val password: String,
    val salarySheetId: String,
    val scheduleSheetId: String
)

class UserPreferencesRepository(
    private val settings: Settings
) {
    private val CREDENTIALS_KEY = "user_credentials_json"

    private val _userCredentialsFlow = MutableStateFlow<UserCredentials?>(readFromDisk())
    val userCredentialsFlow: Flow<UserCredentials?> = _userCredentialsFlow.asStateFlow()

    private fun readFromDisk(): UserCredentials? {
        val jsonString = settings.getStringOrNull(CREDENTIALS_KEY)
        return if (jsonString != null) {
            try {
                Json.decodeFromString<UserCredentials>(jsonString)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    suspend fun fetchInitialCredentials(): UserCredentials? {
        return _userCredentialsFlow.value
    }

    suspend fun saveCredentials(credentials: UserCredentials) {
        val jsonString = Json.encodeToString(credentials)
        settings.putString(CREDENTIALS_KEY, jsonString)

        _userCredentialsFlow.value = credentials
    }

    suspend fun clearCredentials() {
        settings.remove(CREDENTIALS_KEY)
        _userCredentialsFlow.value = null
    }
}