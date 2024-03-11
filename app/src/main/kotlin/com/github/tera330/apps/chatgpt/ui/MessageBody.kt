package com.github.tera330.apps.chatgpt.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.tera330.apps.chatgpt.MessageUiState
import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Message
import com.github.tera330.apps.chatgpt.roomdatabase.SaveMessageViewModel
import kotlinx.coroutines.delay


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

                    if (message.role == "assistant") {
                        ChatBotMessage(
                            text = message.content,
                            modifier = Modifier,
                            textSize = 24.sp
                        )
                    } else {
                        Column(modifier = modifier) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = modifier.padding(start = 20.dp, top = 15.dp)
                            )
                            Text(
                                text = message.content,
                                fontSize = 24.sp,
                                modifier = modifier.padding(start = 20.dp, end = 20.dp, top = 5.dp)
                            )
                        }
                    }
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

@Composable
fun TypingText(text: String, modifier: Modifier = Modifier, textSize: TextUnit) {
    var visibleText by remember { mutableStateOf("") }
    val textFieldValue = remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(text) {
        visibleText = ""
        text.forEach { char ->
            textFieldValue.value = TextFieldValue(text = visibleText + char)
            delay(50) // 文字を表示する速度を調整
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

@Composable
fun ChatBotMessage(text: String, modifier: Modifier, textSize: TextUnit) {
    Column(modifier = modifier) {
        Icon(
            imageVector = Icons.Default.Android,
            contentDescription = null,
            modifier = modifier.padding(start = 20.dp, top = 15.dp)
        )
        TypingText(
            text = text,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 5.dp),
            textSize = textSize
        )
    }
}

@Composable
@Preview
fun BotPreview() {
    val modifier = Modifier
    ChatBotMessage(text = "", modifier = modifier, textSize = 24.sp)
}
