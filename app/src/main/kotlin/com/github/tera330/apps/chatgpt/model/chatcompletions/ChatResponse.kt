package com.github.tera330.apps.chatgpt.model.chatcompletions

import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Choice
import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Usage
import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val usage: Usage,
    val choices: List<Choice>
)