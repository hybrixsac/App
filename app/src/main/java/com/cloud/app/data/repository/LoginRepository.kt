package com.cloud.app.data.repository

import com.cloud.app.data.ApiHelper
import com.cloud.app.data.model.LoginRequest

class LoginRepository(private val apiHelper: ApiHelper)
{
    suspend fun login(request:LoginRequest) = apiHelper.login(request)
}