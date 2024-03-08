package com.github.tera330.apps.chatgpt

import android.util.Log
import com.github.tera330.apps.chatgpt.model.chatcompletions.ChatRequest
import com.github.tera330.apps.chatgpt.model.chatcompletions.ChatResponse
import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Message
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

private const val OPENAI_API_KEY = ""
private const val OPEN_AI_API_BASE_URL = "https://api.openai.com/"


interface OpenAiApiService { // OpenAIのAPIを呼び出すインターフェース
    @Headers( // HTTPリクエストのヘッダー
        "Content-Type: application/json",
        "Authorization: Bearer ${OPENAI_API_KEY}"
    )
    @POST("v1/chat/completions")
    suspend fun chatCompletions(@Body request: ChatRequest): ChatResponse
}

suspend fun A(messageViewModel: MessageViewModel, userMassage: String) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openai.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val openAiApiService = retrofit.create(OpenAiApiService::class.java)

    val request = ChatRequest(
        model = "gpt-3.5-turbo",
        messages = listOf(Message("user", "${userMassage}")),
        temperature = 0.7
    )

    try {
        val response = openAiApiService.chatCompletions(request)
        Log.d("Response", "$response" + "成功")
        val newResponse = response.choices[0].message.content
        messageViewModel.response.value = newResponse
        messageViewModel.userMessage.value = "" // 入力欄をクリア


        val currentList = messageViewModel.messageList.value.toMutableList() // 現在のリストを取得し、変更可能なリストに変換
        currentList.add(Message("assistant", messageViewModel.response.value)) // リストに要素を追加
        messageViewModel.messageList.value = currentList.toList() // 変更されたリストを新しい値としてMutableStateに設定

    } catch (e: Exception) {
        Log.d("Response", "$e.message" + "エラー")
    }
}

/*
@Composable
@Preview
fun ResTest() {
    LaunchedEffect(Unit) {
        A()
    }
}
 */