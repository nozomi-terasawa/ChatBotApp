package com.github.tera330.apps.chatgpt

import android.util.Log
import com.github.tera330.apps.chatgpt.model.chatcompletions.ChatRequest
import com.github.tera330.apps.chatgpt.model.chatcompletions.ChatResponse
import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
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
    createTitle: (String) -> Unit,
    scope: CoroutineScope,
    updateSuccess: () -> Unit,
    updateStr: (String) -> Unit,
    updateNotYet: () -> Unit

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

    val order = """
        あなたは文章を要約して、どのような内容か一目でわかるようにタイトルを作成するボットです。
        下記のuserMessageを要約し、簡潔にタイトルを付けてください。
        
        例：Android開発をしたいのですが、XMLとComposeではどのような違いがあるのでしょうか？
        タイトル生成：XMLとComposeの違い
        
        上記の例のように簡潔にまとめてください。実際にタイトルを作成する際は、例の"XMLとComposeの違い"の部分だけでよいです。"タイトル生成："入りません。
        
        "userMessage: ${userMassage}"
        
    """.trimIndent()

    val title = ChatRequest(
        model = "gpt-3.5-turbo",
        messages = listOf(Message(
            "user",
            order)),
        temperature = 0.7
    )

    val OPENAI_API_KEY = apiKey


    val header =
        // "Content-Type: application/json",
        "Bearer ${OPENAI_API_KEY}"

    try {
        var response: ChatResponse? = null
        var newResponse = ""
        val job = scope.launch {
            response = openAiApiService.chatCompletions(request, header)
        }
        updateSuccess()
        job.join()

        response?.let {
            newResponse = it.choices[0].message.content
            // newResponseを使用する他の処理
        }
        Log.d("Response", "$response" + "成功")

        /*
        val title = if (newResponse.length > 15) {
            newResponse.substring(0, 15) + "..."
        } else {
            newResponse
        }
         */

        if (uiState.messageList.size <= 2) {
            val titleResponse = openAiApiService.chatCompletions(title, header)
            Log.d("Response", titleResponse.choices[0].message.content + "要約されたタイトル。")
            val t = titleResponse.choices[0].message.content

            // createTitle(titleResponse.choices[0].message.content)
            createTitle(t)

        }
        updateStr(newResponse)// アニメーション表示用のstr
        newResponse.let {
            getResponse(newResponse)
        }
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
