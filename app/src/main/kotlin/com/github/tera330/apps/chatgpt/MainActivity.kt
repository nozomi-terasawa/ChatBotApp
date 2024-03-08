package com.github.tera330.apps.chatgpt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import com.github.tera330.apps.chatgpt.ui.SampleDrawer

@ExperimentalMaterial3Api
internal class MainActivity : ComponentActivity() {

  val messageViewModel: MessageViewModel by viewModels()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      SampleDrawer(messageViewModel, Modifier.fillMaxWidth())
    }
  }
}

