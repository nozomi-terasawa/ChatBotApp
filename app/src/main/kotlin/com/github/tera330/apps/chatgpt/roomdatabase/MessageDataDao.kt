package com.github.tera330.apps.chatgpt.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ConversationsDao {
    @Query("SELECT * FROM conversation WHERE conversation_id = :conversationId")
    suspend fun getConversationById(conversationId: Long): Conversation

    @Query("SELECT * FROM conversation")
    suspend fun getAllConversations(): List<Conversation>

    @Insert
    suspend fun insertConversation(conversations: Conversation): Long

    @Query("DELETE FROM conversation WHERE conversation_id = :conversationId")
    suspend fun deleteConversationById(conversationId: Long)

    @Query("DELETE FROM conversation")
    suspend fun deleteAllConversations()
}

@Dao
interface MessageDataDao {
    @Query("SELECT * FROM message WHERE conversation_id = :conversationId")
    suspend fun getMessagesByConversationId(conversationId: Long): List<MessageData>

    @Insert
    suspend fun insertMessage(messageData: MessageData)
}