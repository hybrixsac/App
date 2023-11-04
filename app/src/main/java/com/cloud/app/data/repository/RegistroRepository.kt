package com.cloud.app.data.repository

import com.cloud.app.data.ApiHelper
import com.cloud.app.data.model.LoginRequest
import com.cloud.app.data.model.RegistroRequest

class RegistroRepository(private val apiHelper: ApiHelper) {
    suspend fun registrar(request: RegistroRequest) = apiHelper.registrar(request)
}