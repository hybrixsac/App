package com.cloud.app.data.model
import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponse(
    val id: Int? = 0,
    val name: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val password: String? = null,
    val imei: String? = null,
    val modelo: String? = null,
    val fecha_creacion: String? = null,
    val fecha_ult_conex: Int? = null,
    val estado_registro: Int? = null,
    val message:String? = null
):Parcelable


