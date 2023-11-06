package com.cloud.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.cloud.app.ui.login.LoginActivity
import com.cloud.app.ui.registro.RegistroActivity

class MainActivity : AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var btnRegistrer:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
    }

    private fun setupUI() {
        btnLogin = findViewById(R.id.btn_login)
        btnRegistrer = findViewById(R.id.btn_register)

        btnLogin.setOnClickListener(onClickListener)
        btnRegistrer.setOnClickListener(onClickListener)
    }

    private val onClickListener = View.OnClickListener { view ->


        when (view.id) {
            R.id.btn_login -> login()
            R.id.btn_register -> registrer()
        }
    }

    private fun registrer() {
        val intent = Intent(this@MainActivity, RegistroActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun login() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


}