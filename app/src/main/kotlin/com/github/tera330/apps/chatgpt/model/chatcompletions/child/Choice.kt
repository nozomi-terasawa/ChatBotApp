package com.github.tera330.apps.chatgpt.model.chatcompletions.child

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Choice(
    val message: Message,
    val logprobs: Any?, // もしログが提供されている場合は適切なデータ型に変更してください
    @SerialName("finish_reason")
    val finishReason: String,
    val index: Int
)