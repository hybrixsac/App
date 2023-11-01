package com.cloud.app.data

import com.cloud.app.data.model.LoginRequest

class ApiHelper(private val apiService: ApiService)
{
    suspend fun login(request:LoginRequest)=apiService.login(request)
}