package com.drapshop.utils

import android.app.Activity
import android.content.Context
import com.google.android.material.snackbar.Snackbar
import java.io.IOException

object Utilities {
    @JvmStatic
    fun showSnackbar(activity: Activity, message: String) {
        val snackBar = Snackbar.make(
            activity.findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT
        )
        snackBar.show()

    }
    @JvmStatic
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
}