package com.github.tera330.apps.chatgpt.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tera330.apps.chatgpt.roomdatabase.Conversation
import com.github.tera330.apps.chatgpt.roomdatabase.ConversationRepository
import com.github.tera330.apps.chatgpt.roomdatabase.MessageData
import com.github.tera330.apps.chatgpt.roomdatabase.MessageDataRepository
import kotlinx.coroutines.launch

class SaveMessageViewModel(
    private val conversationRepository: ConversationRepository,
    private val messageDataRepository: MessageDataRepository
): ViewModel() {

    var savedUiState by mutableStateOf(SavedUiState())
        private set

    // val messageUiState by mutableStateOf(MessageData())
    var conversationState by mutableStateOf(Conversation())

    suspend fun saveConversation(): Long {
        var id: Long =0
        val job = viewModelScope.launch {
            id = conversationRepository.insertConversation(conversationState)

        }
        job.join()
        return id
    }

    suspend fun saveMessage(message: MessageData) {
        viewModelScope.launch {
            messageDataRepository.insertMessage(message)
        }
    }
    suspend fun getAllConversations(): List<Conversation> {
        var conversationList = listOf<Conversation>()
        val job = viewModelScope.launch {
            conversationList = conversationRepository.getAllConversations()
        }
        job.join()
        return conversationList
    }

    suspend fun getMessagesByConversationId(conversationId: Long): List<MessageData> {
        var messageList = listOf<MessageData>()
        val job = viewModelScope.launch {
            messageList = messageDataRepository.getMessagesByConversationId(conversationId)
        }
        job.join()
        return messageList
    }

    fun updateConversationList (newList: MutableList<Conversation>) {
        savedUiState = savedUiState.copy(
            conversationList = newList
        )
    }

    fun updateMessage(message: MutableList<MessageData>) {
        savedUiState = savedUiState.copy(
            messageDataList = message
        )
    }

    fun createTitle(title: String) {
        conversationState = conversationState.copy(
            title = title
        )
    }

    fun deleteConversationById(id: Long) {
        viewModelScope.launch {
            conversationRepository.deleteConversationById(id)
        }
    }

    fun deleteAllConversation(newList: MutableList<Conversation>) {
        viewModelScope.launch {
            conversationRepository.deleteAllConversation()
        }
        updateConversationList(newList)
    }
}

data class SavedUiState(
    val conversationList: MutableList<Conversation> = mutableListOf(),
    val messageDataList: MutableList<MessageData> = mutableListOf(),
    val title: String = ""
)
