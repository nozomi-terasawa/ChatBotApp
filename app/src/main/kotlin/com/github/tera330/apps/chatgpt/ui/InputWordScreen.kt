package com.github.tera330.apps.chatgpt.ui

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.tera330.apps.chatgpt.MessageUiState
import com.github.tera330.apps.chatgpt.apiService
import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Message
import kotlinx.coroutines.launch

@Composable
fun InputField(
    modifier: Modifier,
    uiState: MessageUiState,
    inputText: (String) -> Unit,
    getResponse: (String) -> Unit
) {
    val scope = rememberCoroutineScope()

    Row(
        modifier = modifier.fillMaxWidth(), // このRowを画面いっぱいに拡張する
    ) {
        OutlinedTextField(
            value = uiState.userMessage,
            onValueChange = {
                inputText(it)
            },
            label = { Text(text = "入力してください") },
            modifier = Modifier
                .weight(1f) // OutlinedTextFieldが残りのスペースを使用するように重み付け
                .padding(bottom = 10.dp)
        )
        Button(
            onClick = {
                if (!uiState.userMessage.isNullOrBlank()) {
                    val currentList = uiState.messageList.toMutableList()
                    currentList.add(Message("user", uiState.userMessage))
                    uiState.messageList = currentList.toMutableList()

                    scope.launch { apiService(uiState, uiState.userMessage, getResponse) }
                    Log.d("result", uiState.userMessage)

                    Log.d("result", uiState.userMessage)
                }
            },
            modifier = Modifier // Buttonの幅を指定せず、内容に合わせる
        ) {
            Text(text = "↑", fontSize = 30.sp)
        }
    }
}


/*

@Composable
@Preview
private fun inputFieldPreview(modifier: Modifier = Modifier.fillMaxWidth()) {
    val messageViewModel = MessageViewModel()
    Column {
        Spacer(modifier = modifier.weight(1f))
        InputField(modifier.fillMaxWidth(), messageViewModel,)
    }
}

 */