package com.github.tera330.apps.chatgpt.ui

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InputField(
    modifier: Modifier = Modifier
        .fillMaxSize()
    ) {
    var inputWord by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        OutlinedTextField(
            value = inputWord,
            onValueChange = { inputWord = it },
            label = { Text(text = "入力してください") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
@Preview
private fun A(modifier: Modifier = Modifier) {
    Column {
        Spacer(modifier = modifier.weight(1f))
        InputField()
    }
}