package com.github.tera330.apps.chatgpt.model.chatcompletions.child

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val role: String,
    val content: String
)