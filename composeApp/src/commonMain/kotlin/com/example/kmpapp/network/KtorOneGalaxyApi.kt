package com.example.kmpapp.network

import com.example.kmpapp.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorOneGalaxyApi {
    private val BASE_URL = BuildConfig.SCRIPT_URL

    private val client = HttpClient {
        install(ContentNegotiation){
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    suspend fun getUsers(): List<UserDto> {
        println("DEBUG: Sending request to: $BASE_URL")
        return client.get(BASE_URL) {
            parameter("action", "getUsers")
        }.body()
    }

    suspend fun getSalary(spreadsheetId: String, employeeName: String): List<SalaryDto> {
        return client.get(BASE_URL){
            parameter("action", "getSalary")
            parameter("spreadsheetId", spreadsheetId)
            parameter("employeeName", employeeName)
        }.body()
    }

    suspend fun getSchedule(spreadsheetId: String): List<ScheduleDto> {
        return client.get(BASE_URL) {
            parameter("action", "getSchedule")
            parameter("spreadsheetId", spreadsheetId)
        }.body()
    }
}