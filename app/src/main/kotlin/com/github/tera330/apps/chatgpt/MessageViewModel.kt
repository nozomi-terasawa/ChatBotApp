package com.github.tera330.apps.chatgpt

import android.util.Log
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
        Log.d("result", "リストを更新します")
        messageUiState = messageUiState.copy(
            messageList = newList
        )
    }
    fun updateLoad() {
        messageUiState = messageUiState.copy(
            apiUiState = ResponseUiState.Load
        )
    }

    fun updateSuccess() {
        messageUiState = messageUiState.copy(
            apiUiState = ResponseUiState.Success
        )
    }

    fun updateStr(newStr: String) {
        messageUiState = messageUiState.copy(
            responseContent = newStr
        )
    }
    fun updateNotYet() {
        messageUiState = messageUiState.copy(
            apiUiState = ResponseUiState.NotYet
        )
    }
}

data class MessageUiState(
    var userMessage: String = "",
    var response: String = "",
    var messageList: MutableList<Message> = mutableListOf(),
    var apiUiState: ResponseUiState = ResponseUiState.NotYet,
    var responseContent: String = ""
)

sealed interface ResponseUiState {
    object Success: ResponseUiState
    object NotYet: ResponseUiState
    object Load: ResponseUiState
}