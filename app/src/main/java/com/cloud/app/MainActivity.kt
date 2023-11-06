package com.cloud.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
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
            R.id.btn_login -> login(view)
            R.id.btn_register -> registrer(view)
        }
    }

    private fun registrer(view: View?) {
        val intent = Intent(this@MainActivity, RegistroActivity::class.java)
        startActivity(intent)
    }

    private fun login(view: View?) {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
    }


}