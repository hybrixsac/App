package com.cloud.app.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegistroResponse
(
    var id: Int? =0,
    var name: String? =null,
    var email: String? =null,
    var phone: Int? =0
):Parcelable