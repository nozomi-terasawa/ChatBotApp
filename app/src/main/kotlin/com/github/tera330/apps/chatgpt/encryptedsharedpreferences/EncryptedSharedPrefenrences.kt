package com.github.tera330.apps.chatgpt.encryptedsharedpreferences

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun SaveKey(modifier: Modifier = Modifier) {

    val apiKeyState = remember { mutableStateOf("") }
    val encryptedSharedPreferences = EncryptedSharedPreferences(LocalContext.current)

    Column {
        OutlinedTextField(
            value = apiKeyState.value,
            onValueChange = { apiKeyState.value = it },
            label = { Text("APIキーを入力してください") }
        )
    }
    encryptedSharedPreferences.saveKey("pass", apiKeyState.toString())
    val key = encryptedSharedPreferences.getData("pass")

    Log.d("SaveKey", key.toString() + "けっかだよ")
}

@Composable
@Preview
fun PreviewSaveKey(modifier: Modifier = Modifier.fillMaxSize()) {
    SaveKey(modifier = modifier.fillMaxWidth())
}



