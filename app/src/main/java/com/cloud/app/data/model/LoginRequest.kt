package com.cloud.app.data.model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginRequest(
    var email: String? = null,
    var password: String? = null
):Parcelable