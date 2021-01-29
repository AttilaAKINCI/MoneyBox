package com.akinci.moneybox.common.storage

class FakeLocalPreferences : Preferences {
    private val storage : HashMap<String, String?> = hashMapOf()

    override fun getStoredTag(key: String): String? { return storage[key] }
    override fun setStoredTag(key: String, value: String) { storage[key] = value }
}