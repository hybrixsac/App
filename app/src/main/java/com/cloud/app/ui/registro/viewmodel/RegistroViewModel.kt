package com.cloud.app.ui.registro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cloud.app.data.model.RegistroRequest
import com.cloud.app.data.repository.RegistroRepository
import com.cloud.app.util.Resource
import kotlinx.coroutines.Dispatchers

class RegistroViewModel(private val registroRepository: RegistroRepository) : ViewModel() {
    fun registrar(request: RegistroRequest)= liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = registroRepository.registrar(request)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Ocurrio un Error!"))
        }
    }
}