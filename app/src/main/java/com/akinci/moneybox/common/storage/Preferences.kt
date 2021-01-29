package com.akinci.moneybox.common.storage

interface Preferences {
    fun getStoredTag(key: String): String?
    fun setStoredTag(key: String, value: String)
}