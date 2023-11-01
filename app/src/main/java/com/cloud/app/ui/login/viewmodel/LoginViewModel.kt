package com.cloud.app.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cloud.app.data.model.LoginRequest
import com.cloud.app.data.repository.LoginRepository
import com.cloud.app.util.Resource
import kotlinx.coroutines.Dispatchers
class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel()
{
    fun login(request: LoginRequest)=liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = loginRepository.login(request)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Ocurrio un Error!"))
        }
    }

}