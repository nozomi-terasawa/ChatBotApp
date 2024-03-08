package com.github.tera330.apps.chatgpt

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Message

class MessageViewModel: ViewModel()  {

    val userMessage: MutableState<String> = mutableStateOf("")
    val response: MutableState<String> = mutableStateOf("")

    val messageList: MutableState<List<Message>> = mutableStateOf(mutableListOf())



}