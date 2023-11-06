package com.cloud.app

import android.content.Context
import android.content.SharedPreferences


object AppPreferences {

    private const val NAME = "AppPref"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val USUARIO_ID = Pair("usuario_id", "")
    private val USUARIO_EMAIL = Pair("usuario_email", "")
    private val USUARIO_NAME = Pair("usuario_name", "")
    private val USUARIO_PHONE = Pair("usuario_phone", "")



    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    fun clear() {
        preferences.edit().clear().apply()
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }



    var usuarioId: Int
        get() = preferences.getInt(USUARIO_ID.first, 0)
        set(value) = preferences.edit {
            it.putInt(USUARIO_ID.first, value)
        }

    var usuarioEmail: String?
        get() = preferences.getString(USUARIO_EMAIL.first, USUARIO_EMAIL.second)
        set(value) = preferences.edit {
            it.putString(USUARIO_EMAIL.first, value)
        }

    var usuarioName: String?
        get() = preferences.getString(USUARIO_NAME.first, USUARIO_NAME.second)
        set(value) = preferences.edit {
            it.putString(USUARIO_NAME.first, value)
        }

    var usuarioPhone: Int
        get() = preferences.getInt(USUARIO_PHONE.first, 0)
        set(value) = preferences.edit {
            it.putInt(USUARIO_PHONE.first, value)
        }



}