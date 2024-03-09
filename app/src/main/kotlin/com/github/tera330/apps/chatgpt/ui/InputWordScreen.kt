package com.github.tera330.apps.chatgpt.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.tera330.apps.chatgpt.MessageViewModel
import com.github.tera330.apps.chatgpt.apiService
import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Message
import kotlinx.coroutines.launch

@Composable
fun InputField(
    modifier: Modifier,
    messageViewModel: MessageViewModel
) {
    val scope = rememberCoroutineScope()
    val messageUiState = messageViewModel.messageUiState

    Row(
        modifier = modifier.fillMaxWidth(), // このRowを画面いっぱいに拡張する
    ) {
        OutlinedTextField(
            value = messageViewModel.messageUiState.collectAsState().value.userMessage,
            onValueChange = {
                messageViewModel.inputText(it)
            },
            label = { Text(text = "入力してください") },
            modifier = Modifier
                .weight(1f) // OutlinedTextFieldが残りのスペースを使用するように重み付け
                .padding(bottom = 10.dp)
        )
        Button(
            onClick = {
                if (!messageUiState.value.userMessage.isNullOrBlank()) {
                    val userRequest = messageUiState.value.userMessage

                    messageUiState.value.userMessage = "" // 入力欄をクリア

                    val currentList = messageUiState.value.messageList.toMutableList() // 現在のリストを取得し、変更可能なリストに変換
                    currentList.add(Message("user", userRequest)) // リストに要素を追加
                    messageUiState.value.messageList = currentList.toMutableList() // 変更されたリストを新しい値としてMutableStateに設定
                    Log.d("result", "すぐにリストを更新")



                    scope.launch { apiService(messageViewModel, userRequest) }

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