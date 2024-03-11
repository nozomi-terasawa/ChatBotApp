package com.github.tera330.apps.chatgpt

import android.util.Log
import com.github.tera330.apps.chatgpt.model.chatcompletions.ChatRequest
import com.github.tera330.apps.chatgpt.model.chatcompletions.ChatResponse
import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Message
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface OpenAiApiService { // OpenAIのAPIを呼び出すインターフェース

    @POST("v1/chat/completions")
    suspend fun chatCompletions(
        @Body request: ChatRequest,
        @Header("Authorization") str: String,
    ): ChatResponse
}


suspend fun apiService(
    userMassage: String,
    getResponse: (String) -> Unit,
    apiKey: String,
    uiState: MessageUiState,
    createTitle: (String) -> Unit
    ) {
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

    val createTitle = ChatRequest(
        model = "gpt-3.5-turbo",
        messages = listOf(Message("user", "${userMassage}" + "この質問をファイルのタイトルとして扱うために、短めの文章に要約して")),
        temperature = 0.7
    )

    val OPENAI_API_KEY = apiKey


    val header =
        // "Content-Type: application/json",
        "Bearer ${OPENAI_API_KEY}"

    try {

        val response = openAiApiService.chatCompletions(request, header)
        val newResponse = response.choices[0].message.content
        Log.d("Response", "$response" + "成功")


        if (uiState.messageList.size <= 2) {
            val titleResponse = openAiApiService.chatCompletions(createTitle, header)
            Log.d("Response", titleResponse.choices[0].message.content + "要約されたタイトルです。")

            // createTitle(titleResponse.choices[0].message.content)
        }


        getResponse(newResponse)

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
