package com.github.tera330.apps.chatgpt.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.tera330.apps.chatgpt.A
import com.github.tera330.apps.chatgpt.MessageViewModel
import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Message
import kotlinx.coroutines.launch

@Composable
fun InputField(
    modifier: Modifier,
    messageViewModel: MessageViewModel
) {
    val scope = rememberCoroutineScope()

    Row(
        modifier = modifier.fillMaxWidth(), // このRowを画面いっぱいに拡張する
    ) {
        OutlinedTextField(
            value = messageViewModel.userMessage.value,
            onValueChange = { it ->
                messageViewModel.userMessage.value = it
            },
            label = { Text(text = "入力してください") },
            modifier = Modifier.weight(1f) // OutlinedTextFieldが残りのスペースを使用するように重み付け
                .padding(bottom = 10.dp)
        )
        Button(
            onClick = {
                if (!messageViewModel.userMessage.value.isNullOrBlank()) {
                    scope.launch { A(messageViewModel, messageViewModel.userMessage.value) }
                    val currentList = messageViewModel.messageList.value.toMutableList()
                    currentList.add(Message("user", messageViewModel.userMessage.value))
                    messageViewModel.messageList.value = currentList.toList()
                }
            },
            modifier = Modifier // Buttonの幅を指定せず、内容に合わせる
        ) {
            Text(text = "↑", fontSize = 30.sp)
        }
    }
}



@Composable
@Preview
private fun inputFieldPreview(modifier: Modifier = Modifier.fillMaxWidth()) {
    val messageViewModel = MessageViewModel()
    Column {
        Spacer(modifier = modifier.weight(1f))
        InputField(modifier.fillMaxWidth(), messageViewModel,)
    }
}