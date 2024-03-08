package com.github.tera330.apps.chatgpt.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.tera330.apps.chatgpt.MessageViewModel

@Composable
fun MessageBody(messageViewModel: MessageViewModel, modifier: Modifier = Modifier) {
    val messageList = messageViewModel.messageList.value

    Column() {
    LazyColumn(modifier = modifier.padding(top = 100.dp)) {
        items(messageList.size) { index ->
            val message = messageList[index]
            Box() {
                Text(
                    text = message.content,
                    fontSize = 24.sp,
                    modifier = modifier.padding(20.dp)
                )
            }
        }
    }
        Spacer(Modifier.weight(1f))
        InputField(
            modifier = modifier
                .imePadding()
                .fillMaxWidth(),
            messageViewModel
        )
    }
}