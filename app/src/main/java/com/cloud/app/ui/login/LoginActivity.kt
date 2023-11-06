package com.cloud.app.ui.login

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.cloud.app.AppPreferences
import com.cloud.app.R
import com.cloud.app.data.ApiHelper
import com.cloud.app.data.RetrofitBuilder
import com.cloud.app.data.model.LoginRequest
import com.cloud.app.databinding.ActivityLoginBinding
import com.cloud.app.ui.base.ViewModelFactory
import com.cloud.app.ui.login.viewmodel.LoginViewModel
import com.cloud.app.ui.principal.PrincipalActivity
import com.cloud.app.util.Utils
import com.cloud.app.util.Status.*
import com.cloud.app.util.UtilsMessage
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var viewLoading: Dialog

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        setupInit()
        setupViewModel()
        Listeners()
    }

    private fun Listeners() {
        binding.etLoginUser.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                if(s!!.length>0){
                    binding.lLoginUser.error=""
                }
            }

        })

        binding.etLoginPass.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(s!!.length==8){
                    binding.lLoginPass.error=""
                }
            }

        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(LoginViewModel::class.java)
    }

    private fun setupInit() {
        binding.btnLogin.setOnClickListener(onClickListener)
        binding.ibToolbarBack.setOnClickListener(onClickListener)
        viewLoading = Utils.showLoadingDialog(this)
        AppPreferences.init(this)
    }


    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_login -> login(view)
            R.id.ib_toolbar_back->onBackPressed()
        }
    }

    private fun login(view: View) {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        val usermail = binding.etLoginUser.text!!.toString().trim().lowercase(Locale.getDefault())
        val pass = binding.etLoginPass.text!!.toString().trim()

        if (usermail == "" || !usermail.matches(emailPattern.toRegex())) {
            binding.lLoginUser.error = "Ingresa tu correo"
            binding.etLoginUser.requestFocus()
            return
        }
        if (pass == "") {
            binding.lLoginPass.error = "Ingresa tu clave"
            binding.etLoginPass.requestFocus()
            return
        }
        val loginrequest=LoginRequest()
        loginrequest.email=usermail
        loginrequest.password=pass
        view.hideKeyboard()
        viewLoading.show()
        viewModel.login(loginrequest).observe(this@LoginActivity) {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        resource.data?.let { data ->
                            viewLoading.hide()
                            if(data.id==0){
                                Toast.makeText(this@LoginActivity, ""+resource.data.message, Toast.LENGTH_LONG).show()
                            }else {
                                AppPreferences.usuarioId = data.id!!.toInt()
                                AppPreferences.usuarioName =data.name.toString()
                                AppPreferences.usuarioEmail =data.email.toString()
                                AppPreferences.usuarioPhone =data.phone!!.toInt()
                                val intent = Intent(this@LoginActivity, PrincipalActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                        }
                    }
                    ERROR -> {
                        viewLoading.hide()
                        UtilsMessage.addMsgOK(
                            this@LoginActivity,
                            resource.message
                        )

                    }
                    LOADING -> {
                        viewLoading.show()
                    }
                }
            }
        }

    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}