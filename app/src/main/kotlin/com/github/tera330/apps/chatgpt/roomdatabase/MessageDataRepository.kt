package com.github.tera330.apps.chatgpt.roomdatabase

class ConversationRepository(private val conversationsDao: ConversationsDao) {

    suspend fun getConversationById(conversationId: Long): Conversation {
        return conversationsDao.getConversationById(conversationId)
    }

    suspend fun getAllConversations(): List<Conversation> {
        return conversationsDao.getAllConversations()
    }

    suspend fun insertConversation(conversations: Conversation) =
        conversationsDao.insertConversation(conversations)

    suspend fun deleteConversationById(conversationId: Long) =
        conversationsDao.deleteConversationById(conversationId)

    suspend fun deleteAllConversation() =
        conversationsDao.deleteAllConversations()
}

class MessageDataRepository(private val messageDataDao: MessageDataDao) {

    suspend fun getMessagesByConversationId(conversationId: Long): List<MessageData> {
        return messageDataDao.getMessagesByConversationId(conversationId)
    }

    suspend fun insertMessage(messageData: MessageData) {
        messageDataDao.insertMessage(messageData)
    }
}
