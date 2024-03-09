package com.github.tera330.apps.chatgpt.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.tera330.apps.chatgpt.MessageViewModel


@Composable
fun MessageBody(messageViewModel: MessageViewModel, modifier: Modifier = Modifier) {

    val messageUiState by messageViewModel.messageUiState.collectAsState()

    Column {
        LazyColumn(
            modifier = modifier.padding(top = 100.dp).weight(1f),
            content = {
                items(messageUiState.messageList) { message ->
                    Text(
                        text = message.content,
                        fontSize = 24.sp,
                        modifier = modifier.padding(20.dp)
                    )
                }
            }
        )

        InputField(
            modifier = modifier
                .imePadding()
                .fillMaxWidth(),
            messageViewModel
        )
    }
}