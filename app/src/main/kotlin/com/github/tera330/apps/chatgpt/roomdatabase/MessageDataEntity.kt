package com.github.tera330.apps.chatgpt.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

// 子、メッセージ一覧
@Entity(
    tableName = "message",
    foreignKeys = [ForeignKey(
        entity = Conversation::class,
        parentColumns = ["conversation_id"], // 外部キー制約の親になるカラム
        childColumns = ["conversation_id"], // 外部キー制約の個になるカラム
        onDelete = ForeignKey.CASCADE // 親エンティティが消去されたときに子も消去
    )]
)
data class MessageData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val role: String = "",
    val message: String = "",
    @ColumnInfo(name = "conversation_id")
    val conversationId: Long = 0
)


// 親、会話一覧
@Entity(tableName = "conversation")
data class Conversation (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "conversation_id")
    val conversationsId: Long = 0
)