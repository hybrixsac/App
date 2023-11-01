package com.cloud.app.data
import com.cloud.app.data.model.LoginRequest
import com.cloud.app.data.model.LoginResponse
import retrofit2.http.*


interface ApiService {

    @Headers("content-type: application/json")
    @POST("api/login")
    suspend fun login(@Body request:LoginRequest): LoginResponse
}