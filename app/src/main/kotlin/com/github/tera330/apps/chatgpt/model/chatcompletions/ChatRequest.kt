package com.github.tera330.apps.chatgpt.model.chatcompletions

import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Message

data class ChatRequest (
    val model: String,
    val messages: List<Message>,
    val temperature: Double
)