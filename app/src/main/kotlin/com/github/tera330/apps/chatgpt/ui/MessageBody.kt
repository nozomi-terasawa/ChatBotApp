package com.github.tera330.apps.chatgpt.ui

import android.util.Log
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.tera330.apps.chatgpt.MessageUiState
import com.github.tera330.apps.chatgpt.ResponseUiState
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
    messageViewModel: SaveMessageViewModel,
    updateLoad: () -> Unit,
    updateSuccess: () -> Unit,
    updateStr: (String) -> Unit,
    updateNotYet: () -> Unit


    ) {
    Column {
        LaunchedEffect(Unit) {
            val conversationList = messageViewModel.getAllConversations()
            messageViewModel.updateConversationList(conversationList.toMutableList())
        }

        LazyColumn(
            modifier = modifier
                .padding(top = 50.dp)
                .weight(1f),
            content = {
                items(uiState.messageList) { message ->


                    when (uiState.apiUiState) {
                        ResponseUiState.NotYet -> Log.d("result", "NotYetです")
                        ResponseUiState.Load -> Log.d("result", "Loadです")
                        ResponseUiState.Success -> Log.d("result", "Successです")

                        else -> {}
                    }
                    if (message.role == "assistant" && uiState.messageList.indexOf(message) == uiState.messageList.size - 1) {
                        if (uiState.apiUiState == ResponseUiState.Success) {
                            Log.d("result", "SUCCESです")

                            ChatBotMessage(
                                text = uiState.responseContent,
                                modifier = Modifier,
                                textSize = 24.sp,
                                updateNotYet = updateNotYet
                            )
                        } else {
                            Column(modifier = modifier) {
                                Icon(
                                    imageVector = Icons.Default.Android,
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
                    } else {
                        Column(modifier = modifier) {
                            if (message.role == "assistant") {
                                Icon(
                                    imageVector = Icons.Default.Android,
                                    contentDescription = null,
                                    modifier = modifier.padding(start = 20.dp, top = 15.dp)
                                )
                                Text(
                                    text = message.content,
                                    fontSize = 24.sp,
                                    modifier = modifier.padding(
                                        start = 20.dp,
                                        end = 20.dp,
                                        top = 5.dp
                                    )
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = modifier.padding(start = 20.dp, top = 15.dp)
                                )
                                Text(
                                    text = message.content,
                                    fontSize = 24.sp,
                                    modifier = modifier.padding(
                                        start = 20.dp,
                                        end = 20.dp,
                                        top = 5.dp
                                    )
                                )

                            }
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
            createTitle,
            updateLoad,
            updateSuccess,
            updateStr,
            updateNotYet
        )
    }
}

@Composable
fun TypingText(text: String, modifier: Modifier = Modifier, textSize: TextUnit, updateNotYet: () -> Unit) {
    var visibleText by remember { mutableStateOf("") }
    val textFieldValue = remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(text) {
        visibleText = ""
        text.forEach { char ->
            textFieldValue.value = TextFieldValue(text = visibleText + char)
            delay(50) // 文字を表示する速度を調整
            visibleText += char
        }
        updateNotYet()
    }

    BasicTextField(
        value = textFieldValue.value,
        onValueChange = {},
        modifier = modifier,
        textStyle = TextStyle(fontSize = textSize)
    )
}

@Composable
fun ChatBotMessage(text: String, modifier: Modifier, textSize: TextUnit, updateNotYet: () -> Unit) {
    Column(modifier = modifier) {
        Icon(
            imageVector = Icons.Default.Android,
            contentDescription = null,
            modifier = modifier.padding(start = 20.dp, top = 15.dp)
        )
        TypingText(
            text = text,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 5.dp),
            textSize = textSize,
            updateNotYet = updateNotYet
        )
    }
}

/*
@Composable
@Preview
fun BotPreview() {
    val modifier = Modifier
    ChatBotMessage(text = "", modifier = modifier, textSize = 24.sp, updateNotYet = )
}

 */
