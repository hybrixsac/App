package com.cloud.app.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.cloud.app.R
import java.io.IOException
import java.lang.StringBuilder
import java.math.BigDecimal
import java.text.DateFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.*
import java.text.SimpleDateFormat as SDFormat



object Utils {

    const val SOLES = "S/"

    fun showLoadingDialog(context: Context): Dialog {
        val dialog = Dialog(context)
        val inflate = LayoutInflater.from(context)
            .inflate(R.layout.progress_dialog, null)
        dialog.setContentView(inflate)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        return dialog
    }


    fun formatCoin(coin: Double): String? {
        return String.format(Locale.ENGLISH, "%,.2f", coin, 2)
    }

    private fun triTexto(n: Int): StringBuilder? {
        val result = StringBuilder()
        val centenas = n / 100
        val decenas = n % 100 / 10
        val unidades = n % 10
        when (centenas) {
            0 -> {
            }
            1 -> if (decenas == 0 && unidades == 0) {
                result.append("Cien ")
                return result
            } else result.append("Ciento ")
            2 -> result.append("Doscientos ")
            3 -> result.append("Trescientos ")
            4 -> result.append("Cuatrocientos ")
            5 -> result.append("Quinientos ")
            6 -> result.append("Seiscientos ")
            7 -> result.append("Setecientos ")
            8 -> result.append("Ochocientos ")
            9 -> result.append("Novecientos ")
        }
        when (decenas) {
            0 -> {
            }
            1 -> if (unidades == 0) {
                result.append("Diez ")
                return result
            } else if (unidades == 1) {
                result.append("Once ")
                return result
            } else if (unidades == 2) {
                result.append("Doce ")
                return result
            } else if (unidades == 3) {
                result.append("Trece ")
                return result
            } else if (unidades == 4) {
                result.append("Catorce ")
                return result
            } else if (unidades == 5) {
                result.append("Quince ")
                return result
            } else result.append("Dieci")
            2 -> if (unidades == 0) {
                result.append("Veinte ")
                return result
            } else result.append("Veinti")
            3 -> result.append("Treinta ")
            4 -> result.append("Cuarenta ")
            5 -> result.append("Cincuenta ")
            6 -> result.append("Sesenta ")
            7 -> result.append("Setenta ")
            8 -> result.append("Ochenta ")
            9 -> result.append("Noventa ")
        }
        if (decenas > 2 && unidades > 0) result.append("y ")
        when (unidades) {
            0 -> {
            }
            1 -> result.append("Un ")
            2 -> result.append("Dos ")
            3 -> result.append("Tres ")
            4 -> result.append("Cuatro ")
            5 -> result.append("Cinco ")
            6 -> result.append("Seis ")
            7 -> result.append("Siete ")
            8 -> result.append("Ocho ")
            9 -> result.append("Nueve ")
        }
        return result
    }

    fun priceToLetter(price: Double): String? {
        var entero: Long = 0
        var decimal = ""
        val doubleAsText =
            String.format(Locale.ENGLISH, "%.2f", price, 2)
        entero = price.toLong()
        decimal = doubleAsText.split("\\.".toRegex()).toTypedArray()[1]
        val result = StringBuilder()
        val totalBigDecimal = BigDecimal(entero)
        val parteEntera = totalBigDecimal.toBigInteger().toLong()
        val triUnidades = (parteEntera % 1000).toInt()
        val triMiles = (parteEntera / 1000 % 1000).toInt()
        val triMillones = (parteEntera / 1000000 % 1000).toInt()
        val triMilMillones = (parteEntera / 1000000000 % 1000).toInt()
        if (parteEntera == 0L) {
            result.append("Cero ")
            return result.toString()
        }
        if (triMilMillones > 0) result.append(
            triTexto(
                triMilMillones
            ).toString() + "Mil "
        )
        if (triMillones > 0) result.append(
            triTexto(triMillones).toString()
        )
        if (triMilMillones == 0 && triMillones == 1) result.append("Millón ") else if (triMilMillones > 0 || triMillones > 0) result.append(
            "Millones "
        )
        if (triMiles > 0) result.append(
            triTexto(triMiles).toString() + "Mil "
        )
        if (triUnidades > 0) result.append(
            triTexto(triUnidades).toString()
        )
        val a = result.toString().toLowerCase()
        val b = a.substring(0, 1).toUpperCase() + a.substring(1)
        return "$b con $decimal/100"
    }


    // No line break
    fun getBase64(input: String): String? {
        return Base64.encodeToString(input.toByteArray(), Base64.NO_WRAP)
    }


    @Throws(ParseException::class)
    fun toNumberFormatEnglish(value: String): Double {
        val nf = NumberFormat.getInstance(Locale.ENGLISH)
        return nf.parse(value.replace(",", ".")).toDouble()
    }

    fun porcent(value: Int): String? {
        return "${value}%"
    }

    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }


    fun toDate(date: String): Date{
        val format: DateFormat = SDFormat("yyyy-MM-dd'T'HH:mm:ss")
        return format.parse(date)
    }

    fun toString(date: Date): String{
        val dateFormat: DateFormat = SDFormat("yyyy-MM-dd'T'HH:mm:ss")
       return dateFormat.format(date)
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }



}