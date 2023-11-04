package com.cloud.app.ui.registro

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cloud.app.data.ApiHelper
import com.cloud.app.data.RetrofitBuilder
import com.cloud.app.data.model.RegistroRequest
import com.cloud.app.databinding.ActivityRegistroBinding
import com.cloud.app.ui.base.ViewModelFactory
import com.cloud.app.ui.login.viewmodel.LoginViewModel
import com.cloud.app.ui.registro.viewmodel.RegistroViewModel
import com.cloud.app.util.Status
import com.cloud.app.util.Utils
import com.cloud.app.util.UtilsMessage

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var viewLoading: Dialog
    private lateinit var viewModel: RegistroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupInit()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(RegistroViewModel::class.java)
    }

    private fun setupInit() {
        viewLoading = Utils.showLoadingDialog(this)
        binding.btnRegister.setOnClickListener{
            v->
            validarEntradas()
        }
    }

    private fun validarEntradas() {
        viewLoading.show()
        var nombre = binding.etNameUser.text?.toString()?.trim()
        var email =binding.etEmailUser.text?.toString()?.trim()
        var userpass =binding.etPassUser.text?.toString()?.trim()
        var usertelef=binding.etTelefUser.text?.toString()?.trim()
        var imei=""
        var model=""

        if (nombre == "") {
            viewLoading.hide()
            binding.lEtNameUser.error = "Debe de ingresar un nombre"
            return
        }

        if(email ==""){
            viewLoading.hide()
            binding.lEtEmailUser.error = "Debe de ingresar un correo electronico"
            return
        }

        if(userpass ==""){
            viewLoading.hide()
            binding.lEtPassUser.error = "Debe de ingresar una clave"
            return
        }

        if(userpass!!.length<8){
            viewLoading.hide()
            binding.lEtPassUser.error = "La clave no debe ser menor de 8 digitos"
            return
        }

        if(usertelef==""){
            viewLoading.hide()
            binding.lEtTelefUser.error = "Debe de ingresar un número de teléfono"
            return
        }

        if(usertelef!!.length<9){
            viewLoading.hide()
            binding.lEtTelefUser.error = "Debe ingresar un número de teléfono válido"
            return
        }

        var request =RegistroRequest()
        request.name=nombre
        request.email=email
        request.password=userpass
        request.imei=imei
        request.model=model

        viewModel.registrar(request).observe(this@RegistroActivity) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { data ->
                            viewLoading.hide()
                            var response=resource.data
                            if(response.id!=0){
                                Toast.makeText(this@RegistroActivity, "Datos guardados correctamente", Toast.LENGTH_LONG).show()
                                finish()
                            }else {
                                Toast.makeText(this@RegistroActivity, "Algo salio mal", Toast.LENGTH_LONG).show()
                            }


                        }
                    }
                    Status.ERROR -> {
                        viewLoading.hide()
                        UtilsMessage.addMsgOK(
                            this@RegistroActivity,
                            resource.message
                        )
                    }
                    Status.LOADING -> {
                        viewLoading.show()
                    }
                }
            }
        }
    }




}