package com.github.tera330.apps.chatgpt.roomdatabase

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SaveMessageViewModel(
    private val conversationRepository: ConversationRepository
): ViewModel() {
    val messageUiState by mutableStateOf(MessageData())
    val conversationState by mutableStateOf(Conversation())

    suspend fun saveConversation() {
        viewModelScope.launch {
            conversationRepository.insertConversation(conversationState)
        }
    }

}

