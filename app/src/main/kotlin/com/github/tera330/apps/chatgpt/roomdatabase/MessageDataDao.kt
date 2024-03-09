package com.github.tera330.apps.chatgpt.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ConversationsDao {
    @Query("SELECT * FROM conversation WHERE conversation_id = :conversationId")
    fun getConversationById(conversationId: Long): Conversation

    @Query("SELECT * FROM conversation")
    fun getAllConversations(): List<Conversation>

    @Insert
    suspend fun insertConversation(conversations: Conversation)
}

@Dao
interface MessageDataDao {
    @Query("SELECT * FROM message WHERE conversation_id = :conversationId")
    fun getMessagesByConversationId(conversationId: Long): List<MessageData>

    @Insert
    fun insertMessage(messageData: MessageData)
}