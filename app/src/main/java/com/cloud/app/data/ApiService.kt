package com.cloud.app.data
import com.cloud.app.data.model.LoginRequest
import com.cloud.app.data.model.LoginResponse
import com.cloud.app.data.model.RegistroRequest
import com.cloud.app.data.model.RegistroResponse
import retrofit2.http.*


interface ApiService {

    @Headers("content-type: application/json")
    @POST("api/login")
    suspend fun login(@Body request:LoginRequest): LoginResponse

    @Headers("content-type: application/json")
    @POST("api/user")
    suspend fun registrar(@Body request:RegistroRequest): RegistroResponse
}