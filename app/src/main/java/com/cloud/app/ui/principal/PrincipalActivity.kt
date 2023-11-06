package com.cloud.app.ui.principal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cloud.app.AppPreferences
import com.cloud.app.MainActivity
import com.cloud.app.R
import com.cloud.app.databinding.ActivityPrincipalBinding

class PrincipalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPrincipalBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        setupInit()
    }

    private fun setupInit() {
        AppPreferences.init(this)
        if(AppPreferences.usuarioId==0)
        {
            val intent = Intent(this@PrincipalActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.txtNomUser.text="Bienvenido: "+AppPreferences.usuarioName

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_close -> {
                showLoginScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLoginScreen() {
        AppPreferences.clear()
        val intent = Intent(this@PrincipalActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

   /* override fun onBackPressed() {
        Toast.makeText(this@PrincipalActivity,"GAAA"+AppPreferences.usuarioId, Toast.LENGTH_LONG).show()

        if(AppPreferences.usuarioId==0)
        {
            super.onBackPressed()
        }else{
           finish()
        }

    }*/
}