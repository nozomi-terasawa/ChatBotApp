package com.github.tera330.apps.chatgpt.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.tera330.apps.chatgpt.MessageUiState
import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Message
import com.github.tera330.apps.chatgpt.roomdatabase.SaveMessageViewModel


@Composable
fun MessageBody(
    uiState: MessageUiState,
    modifier: Modifier = Modifier,
    inputText: (String) -> Unit,
    getResponse : (String) -> Unit,
    changeList: (MutableList<Message>) -> Unit,
    clearText: () -> Unit,
    createTitle: (String) -> Unit,
    messageViewModel: SaveMessageViewModel
    ) {
    Column {
        LaunchedEffect(Unit) {
            val conversationList = messageViewModel.getAllConversations()
            messageViewModel.updateConversationList(conversationList.toMutableList())
        }

        LazyColumn(
            modifier = modifier
                .padding(top = 100.dp)
                .weight(1f),
            content = {
                items(uiState.messageList) { message ->
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
            uiState,
            inputText,
            getResponse,
            changeList,
            clearText,
            createTitle
        )
    }
}