package com.github.tera330.apps.chatgpt.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MessageData::class, Conversation::class], version = 1, exportSchema = false)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun messageDataDao(): MessageDataDao
    abstract fun conversationDao(): ConversationsDao


    companion object {
        @Volatile
        private var instance: MessageDatabase? = null

        fun getDatabase(context: Context): MessageDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    MessageDatabase::class.java,
                    "message_database")
                .build()
                .also{ instance = it }
                }
        }
    }
}