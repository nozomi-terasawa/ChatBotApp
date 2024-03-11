package com.github.tera330.apps.chatgpt.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.github.tera330.apps.chatgpt.MessageUiState
import com.github.tera330.apps.chatgpt.apiService
import com.github.tera330.apps.chatgpt.encryptedsharedpreferences.EncryptedSharedPreferences
import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Message
import kotlinx.coroutines.launch

@Composable
fun InputField(
    modifier: Modifier,
    uiState: MessageUiState,
    inputText: (String) -> Unit,
    getResponse: (String) -> Unit,
    changeList: (MutableList<Message>) -> Unit,
    clearText: () -> Unit,
    createTitle: (String) -> Unit,
    updateLoad: () -> Unit,
    updateSuccess: () -> Unit,
    updateStr: (String) -> Unit,
    updateNotYet: () -> Unit

) {
    val scope = rememberCoroutineScope()
    val encryptedSharedPreferences = EncryptedSharedPreferences(LocalContext.current)
    val key = encryptedSharedPreferences.getData("pass") ?: ""


    Row(
        modifier = modifier.fillMaxWidth(), // このRowを画面いっぱいに拡張する
        verticalAlignment = Alignment.CenterVertically // 垂直方向の中央揃え
    ) {
        OutlinedTextField(
            shape = RoundedCornerShape(35.dp),
            value = uiState.userMessage,
            onValueChange = {
                inputText(it)
            },
            label = { Text(text = "入力してください") },
            modifier = Modifier
                .weight(1f) // OutlinedTextFieldが残りのスペースを使用するように重み付け
                .padding(5.dp)
                .padding(bottom = 5.dp)
                .heightIn(min = ButtonDefaults.MinHeight) // Buttonと同じ高さに指定
        )
        IconButton(
            onClick = {
                if (!uiState.userMessage.isNullOrBlank()) {
                    updateLoad()

                    val currentList = uiState.messageList.toMutableList()
                    currentList.add(Message("user", uiState.userMessage))
                    changeList(currentList)
                    clearText()

                    scope.launch {
                        apiService(uiState.userMessage, getResponse, key, uiState, createTitle, scope, updateSuccess, updateStr, updateNotYet)
                    }
                }
            },
            modifier = Modifier // Buttonの幅を指定せず、内容に合わせる
                .heightIn(min = ButtonDefaults.MinHeight)
                .padding(5.dp)
                .size(35.dp)
            //shape = RoundedCornerShape(35.dp)

        ) {
            Icon(Icons.Filled.Send, contentDescription = null)
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