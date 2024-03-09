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


interface OpenAiApiService { // OpenAIのAPIを呼び出すインターフェース
    @Headers( // HTTPリクエストのヘッダー
        "Content-Type: application/json",
        "Authorization: Bearer ${OPENAI_API_KEY}"
    )
    @POST("v1/chat/completions")
    suspend fun chatCompletions(@Body request: ChatRequest): ChatResponse
}

suspend fun apiService(messageViewModel: MessageViewModel, userMassage: String) {
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

    val messageUiState = messageViewModel.messageUiState

    try {
        val response = openAiApiService.chatCompletions(request)
        Log.d("Response", "$response" + "成功")
        val newResponse = response.choices[0].message.content
        Log.d("result", userMassage)

        // messageUiState.value.response = newResponse

        messageViewModel.getResponse(newResponse)

        val currentList = messageUiState.value.messageList.toMutableList() // 現在のリストを取得し、変更可能なリストに変換
        currentList.add(Message("assistant", newResponse)) // リストに要素を追加
        messageUiState.value.messageList = currentList.toMutableList() // 変更されたリストを新しい値としてMutableStateに設定
        Log.d("result", "APIの中でリストを更新")


    } catch (e: Exception) {
        Log.d("Response", "$e.message" + "エラー")
    }
}

/*

@Composable
@Preview
fun ResTest() {
    val messageViewModel = MessageViewModel()
    LaunchedEffect(Unit) {
        api(messageViewModel = messageViewModel, "こんにちは")
    }
}

 */
