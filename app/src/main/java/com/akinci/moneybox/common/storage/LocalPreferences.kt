package com.akinci.moneybox.common.storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import javax.inject.Inject

class LocalPreferences @Inject constructor(
    val context : Context
) : Preferences {
    private val prefTag  = context.packageName + "_preferences"
    private val prefs = context.getSharedPreferences(prefTag, MODE_PRIVATE)

    override fun getStoredTag(key: String): String? {
        return prefs.getString(key, "")
    }
    override fun setStoredTag(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }
}