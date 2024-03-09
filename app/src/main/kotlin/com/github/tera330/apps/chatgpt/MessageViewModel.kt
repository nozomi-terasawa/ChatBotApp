package com.github.tera330.apps.chatgpt

import androidx.lifecycle.ViewModel
import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MessageViewModel: ViewModel() {
    private val _messageUiState = MutableStateFlow(MessageUiState())
    val messageUiState: StateFlow<MessageUiState> = _messageUiState

    fun inputText(userInputText: String) {
        _messageUiState.update { currentState ->
            currentState.copy(
                userMessage = userInputText
            )
        }
    }

    fun getResponse(apiResponse: String) {
        _messageUiState.update { currentState ->
            currentState.copy(
                response = apiResponse
            )
        }
    }

    fun updateList(newList: MutableList<Message>) {
        _messageUiState.update { currentState ->
            currentState.copy(
                messageList = newList
            )
        }
    }
}

data class MessageUiState(
    var userMessage: String = "",
    var response: String = "",
    var messageList: MutableList<Message> = mutableListOf()
)