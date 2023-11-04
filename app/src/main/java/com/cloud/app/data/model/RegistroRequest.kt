package com.cloud.app.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegistroRequest(
    var name: String? =null,
    var email: String? = null,
    var password: String? = null,
    var phone:Int?=0,
    var imei:String?=null,
    var model:String?=null
): Parcelable
