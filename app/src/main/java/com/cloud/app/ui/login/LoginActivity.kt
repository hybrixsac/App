package com.cloud.app.ui.login

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.cloud.app.AppPreferences
import com.cloud.app.R
import com.cloud.app.data.ApiHelper
import com.cloud.app.data.RetrofitBuilder
import com.cloud.app.data.model.LoginRequest
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
    private lateinit var etUser: TextInputEditText
    private lateinit var etPass: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var viewLoading: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupUI()
        setupInit()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(LoginViewModel::class.java)
    }

    private fun setupInit() {
        btnLogin.setOnClickListener(onClickListener)
        viewLoading = Utils.showLoadingDialog(this)
    }

    private fun setupUI() {
        etUser = findViewById(R.id.et_login_user)
        etPass = findViewById(R.id.et_login_pass)
        btnLogin = findViewById(R.id.btn_login)
    }

    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_login -> login(view)

        }
    }

    private fun login(view: View) {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        val usermail = etUser.text!!.toString().trim().lowercase(Locale.getDefault())
        val pass = etPass.text!!.toString().trim()

        if (usermail == "" || !usermail.matches(emailPattern.toRegex())) {
            etUser.error = "Ingresa tu correo"
            etUser.requestFocus()
            return
        }
        if (pass == "") {
            etPass.error = "Ingresa tu clave"
            etPass.requestFocus()
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

                            AppPreferences.usuarioId = data.id.toString()
                            AppPreferences.usuarioName = data.name.toString()
                            AppPreferences.usuarioEmail = data.email.toString()
                            AppPreferences.usuarioPhone = data.phone.toString()
                            val intent = Intent(this@LoginActivity, PrincipalActivity::class.java)
                            startActivity(intent)

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