package com.github.tera330.apps.chatgpt.roomdatabase

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SaveMessageViewModel(
    private val conversationRepository: ConversationRepository,
    private val messageDataRepository: MessageDataRepository
): ViewModel() {

    var savedUiState by mutableStateOf(SavedUiState())
        private set

    // val messageUiState by mutableStateOf(MessageData())
    val conversationState by mutableStateOf(Conversation())

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

    fun updateConversationList (newList: MutableList<Conversation>) {
        savedUiState = savedUiState.copy(
            conversationList = newList
        )
    }

    fun createTitle(title: String) {
        Log.d("result", "呼び出し開始")
        savedUiState = savedUiState.copy(
            title = title
        )
        Log.d("result", "完了")
    }
}

data class SavedUiState(
    val conversationList: MutableList<Conversation> = mutableListOf(),
    val messageDataList: MutableList<MessageData> = mutableListOf(),
    val title: String = ""
)
