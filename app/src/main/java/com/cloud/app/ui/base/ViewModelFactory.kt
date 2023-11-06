package com.cloud.app.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cloud.app.data.ApiHelper
import com.cloud.app.data.repository.LoginRepository
import com.cloud.app.data.repository.RegistroRepository
import com.cloud.app.ui.login.viewmodel.LoginViewModel
import com.cloud.app.ui.registro.viewmodel.RegistroViewModel


class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(LoginRepository(apiHelper)) as T
        }

        if(modelClass.isAssignableFrom(RegistroViewModel::class.java)){
            return RegistroViewModel(RegistroRepository(apiHelper)) as T
        }


        throw IllegalArgumentException("Unknown class name")
    }

}


