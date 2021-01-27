package com.akinci.moneybox.common.storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import javax.inject.Inject

class LocalPreferences @Inject constructor(
    val context : Context
){
    val PREF_TAG  = context.packageName + "_preferences"
    val prefs = context.getSharedPreferences(PREF_TAG, MODE_PRIVATE)

    fun getStoredTag(key: String): String {
        return prefs.getString(key, "")!!
    }
    fun setStoredTag(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }
}