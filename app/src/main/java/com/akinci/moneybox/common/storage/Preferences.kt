package com.akinci.moneybox.common.storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import javax.inject.Inject

class Preferences @Inject constructor(
    val context : Context
) {
    private val prefTag  = context.packageName + "_preferences"
    private val prefs = context.getSharedPreferences(prefTag, MODE_PRIVATE)

    fun getStoredTag(key: String): String? {
        return prefs.getString(key, "")
    }
    fun setStoredTag(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }
}