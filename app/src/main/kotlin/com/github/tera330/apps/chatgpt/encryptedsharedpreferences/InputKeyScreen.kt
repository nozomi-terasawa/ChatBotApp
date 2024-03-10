package com.github.tera330.apps.chatgpt.encryptedsharedpreferences

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputScreen(modifier: Modifier = Modifier.fillMaxSize()) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        InputKey()
    }
}

@Composable
fun InputKey(modifier: Modifier = Modifier) {

    val apiKeyState = remember { mutableStateOf("") }
    val encryptedSharedPreferences = EncryptedSharedPreferences(LocalContext.current)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "トークンを入力",
            fontSize = 30.sp,
            modifier = modifier.padding(top = 30.dp)
        )
        OutlinedTextField(
            value = apiKeyState.value,
            onValueChange = { apiKeyState.value = it },
            label = { Text("APIキーを入力してください") },
            modifier = modifier
                .fillMaxWidth()
                .padding(15.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
            ) {
            Button(
                onClick = {
                    if (!apiKeyState.value.isNullOrBlank()) {

                    }
                },
            ) {
                Text("保存")
            }
        }
    }

    encryptedSharedPreferences.saveKey("pass", apiKeyState.toString())
    val key = encryptedSharedPreferences.getData("pass")

    Log.d("SaveKey", key.toString() + "けっかだよ")
}

@Composable
@Preview
fun PreviewSaveKey(modifier: Modifier = Modifier.fillMaxSize()) {
    InputScreen(modifier = modifier.fillMaxWidth())
}
