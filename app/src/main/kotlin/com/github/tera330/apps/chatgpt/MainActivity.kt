package com.github.tera330.apps.chatgpt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import com.github.tera330.apps.chatgpt.roomdatabase.SaveMessageViewModel


@ExperimentalMaterial3Api
internal class MainActivity : ComponentActivity() {

  private val messageViewModel: MessageViewModel by viewModels()
  private val savedViewModel: SaveMessageViewModel by viewModels()


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {

      AppNav(
        Modifier.fillMaxWidth(),
        messageViewModel.messageUiState,
        inputText = { string -> messageViewModel.inputText(string) },
        getResponse = { string -> messageViewModel.getResponse(string) },
        changeList = { list -> messageViewModel.updateList(list) },
        clearText = { messageViewModel.clearText() },
        createTitle = { string -> savedViewModel.createTitle(string) },
        updateMessageList = { list -> messageViewModel.updateList(list)}
      )

    }
  }
}

