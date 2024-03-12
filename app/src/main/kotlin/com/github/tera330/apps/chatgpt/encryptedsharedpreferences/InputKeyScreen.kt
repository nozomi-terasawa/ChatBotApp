package com.github.tera330.apps.chatgpt.encryptedsharedpreferences

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun InputKeyScreen(
    modifier: Modifier = Modifier,
    navigateHome: () -> Unit) {


    val apiKeyState = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val encryptedSharedPreferences = EncryptedSharedPreferences(LocalContext.current)

    Column(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        ChatBotMessage(text = "ChatGPTへようこそ！", modifier = modifier.padding(10.dp), textSize = 30.sp)

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
                modifier = modifier.padding(10.dp),
                onClick = {
                    if (!apiKeyState.value.isNullOrBlank()) {
                        scope.launch {
                            encryptedSharedPreferences.saveKey("pass", apiKeyState.value)
                        }
                        navigateHome()
                    }
                },
                shape = RoundedCornerShape(35.dp)
            ) {
                Text("保存して始める")
            }
        }
    }
}

@Composable
fun ChatBotMessage(text: String, modifier: Modifier, textSize: TextUnit) {
    Column(modifier = modifier) {
        TypingText(
            text = text,
            modifier = Modifier.padding(10.dp),
            textSize = textSize
        )
    }
}

@Composable
fun TypingText(text: String, modifier: Modifier = Modifier, textSize: TextUnit) {
    var visibleText by remember { mutableStateOf("") }
    val textFieldValue = remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(text) {
        visibleText = ""
        text.forEach { char ->
            textFieldValue.value = TextFieldValue(text = visibleText + char)
            delay(100) // 文字を表示する速度を調整
            visibleText += char
        }
    }

    BasicTextField(
        value = textFieldValue.value,
        onValueChange = {},
        modifier = modifier,
        textStyle = TextStyle(fontSize = textSize)
    )
}



/*
@Composable
@Preview
fun PreviewSaveKey(modifier: Modifier = Modifier.fillMaxSize()) {
    InputScreen(modifier = modifier.fillMaxWidth())
}

 */
