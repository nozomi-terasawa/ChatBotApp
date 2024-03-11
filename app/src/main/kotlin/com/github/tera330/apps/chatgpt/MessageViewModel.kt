package com.github.tera330.apps.chatgpt

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Message

class MessageViewModel: ViewModel() {
    /*
    private val _messageUiState = MutableStateFlow(MessageUiState())
    val messageUiState: StateFlow<MessageUiState> = _messageUiState
     */

    var messageUiState by mutableStateOf(MessageUiState())
    private set

    fun inputText(userInputText: String) {
        messageUiState = messageUiState.copy(
            userMessage = userInputText
        )
    }

    fun getResponse(apiResponse: String) {
        messageUiState = messageUiState.copy(
            response = apiResponse,
        )
        val currentList = messageUiState.messageList.toMutableList() // 現在のリストを取得し、変更可能なリストに変換
        currentList.add(Message("assistant", apiResponse)) // リストに要素を追加
        updateList(currentList)
    }

    fun clearText() {
        messageUiState = messageUiState.copy(
            userMessage = ""
        )
    }

    fun updateList(newList: MutableList<Message>) {
        messageUiState = messageUiState.copy(
            messageList = newList
        )
    }
}

data class MessageUiState(
    var userMessage: String = "",
    var response: String = "",
    var messageList: MutableList<Message> = mutableListOf()
)