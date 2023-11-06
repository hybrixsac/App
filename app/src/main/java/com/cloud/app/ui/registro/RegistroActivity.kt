package com.cloud.app.ui.registro

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cloud.app.MainActivity
import com.cloud.app.R
import com.cloud.app.data.ApiHelper
import com.cloud.app.data.RetrofitBuilder
import com.cloud.app.data.model.RegistroRequest
import com.cloud.app.databinding.ActivityRegistroBinding
import com.cloud.app.ui.base.ViewModelFactory
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
        Listeners()
    }

    private fun Listeners() {
        binding.etNameUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                if(s!!.length>0){
                    binding.lEtNameUser.error=""
                }
            }

        })

        binding.etEmailUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                if(s!!.length>0){
                    if(!s.matches(emailPattern.toRegex())){
                        binding.lEtEmailUser.error=""
                    }


                }
            }

        })

        binding.etPassUser.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                   if(s!!.length>0){
                       binding.lEtPassUser.error=""
                   }
            }

        })

        binding.etTelefUser.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(s!!.length>0){
                    binding.lEtTelefUser.error=""
                }
            }

        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(RegistroViewModel::class.java)
    }

    private fun setupInit() {
        viewLoading = Utils.showLoadingDialog(this)
        binding.btnRegister.setOnClickListener(onClickListener)
        binding.ibToolbarBack.setOnClickListener(onClickListener)

    }

    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_register -> validarEntradas()
            R.id.ib_toolbar_back->backPressed()
        }
    }

    private fun backPressed() {
        val intent = Intent(this@RegistroActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun validarEntradas() {
        viewLoading.show()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val nombre = binding.etNameUser.text?.toString()?.trim()
        val email =binding.etEmailUser.text?.toString()?.trim()
        val userpass =binding.etPassUser.text?.toString()?.trim()
        val usertelef=binding.etTelefUser.text?.toString()?.trim()
        val imei=""
        val model=""

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

        if(!email!!.matches(emailPattern.toRegex())){
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

        val request =RegistroRequest()
        request.name=nombre
        request.email=email
        request.password=userpass
        request.phone=usertelef.toInt()
        request.imei=imei
        request.model=model

        viewModel.registrar(request).observe(this@RegistroActivity) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { data ->
                            viewLoading.hide()
                            val response=resource.data
                            if(response.id!=0){
                                Toast.makeText(this@RegistroActivity, "Datos guardados correctamente", Toast.LENGTH_LONG).show()
                                finish()
                            }else {
                                Toast.makeText(this@RegistroActivity, response.message, Toast.LENGTH_LONG).show()
                                binding.lEtEmailUser.error=""+response.message
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