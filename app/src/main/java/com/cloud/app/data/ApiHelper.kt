package com.cloud.app.data

import com.cloud.app.data.model.LoginRequest
import com.cloud.app.data.model.RegistroRequest

class ApiHelper(private val apiService: ApiService)
{
    suspend fun login(request:LoginRequest)=apiService.login(request)

    suspend fun registrar(request:RegistroRequest)=apiService.registrar(request)
}