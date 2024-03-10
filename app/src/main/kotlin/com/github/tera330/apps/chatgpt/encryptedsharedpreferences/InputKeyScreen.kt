package com.github.tera330.apps.chatgpt.encryptedsharedpreferences

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


@Composable
fun InputKeyScreen(
    modifier: Modifier = Modifier,
    navigateHome: () -> Unit) {


    val apiKeyState = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val encryptedSharedPreferences = EncryptedSharedPreferences(LocalContext.current)

    Log.d("result", "キー入力画面")

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
                        scope.launch {
                            encryptedSharedPreferences.saveKey("pass", apiKeyState.value)
                        }
                        navigateHome()
                        Log.d("result", "押下されてます")
                    }
                },
            ) {
                Text("保存")
            }
        }
    }

}

/*
@Composable
@Preview
fun PreviewSaveKey(modifier: Modifier = Modifier.fillMaxSize()) {
    InputScreen(modifier = modifier.fillMaxWidth())
}

 */
