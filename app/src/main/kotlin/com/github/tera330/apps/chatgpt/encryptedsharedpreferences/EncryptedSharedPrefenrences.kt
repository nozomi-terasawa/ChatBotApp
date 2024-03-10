package com.github.tera330.apps.chatgpt.encryptedsharedpreferences

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class EncryptedSharedPreferences (context: Context) {

    // MasterKey
    private val mainKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    // EncryptedSharedPreferencesの初期化
    private val prefs = EncryptedSharedPreferences.create(
        context,
        "key_file",
        mainKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveKey(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }
    fun getData(key: String): String? {
        return prefs.getString(key, null)
    }
    fun removeData(key: String) {
        prefs.edit().remove(key).apply()
    }
}



