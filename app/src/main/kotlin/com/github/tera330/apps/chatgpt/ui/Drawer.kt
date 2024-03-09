package com.github.tera330.apps.chatgpt.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.github.tera330.apps.chatgpt.MessageUiState
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun SampleDrawer(
    uiState: MessageUiState,
    modifier: Modifier,
    inputText: (String) -> Unit,
    getResponse: (String) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { ModalDrawerSheet {
            // todo DrawerContent
        } },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("タイトル") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box() {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MessageBody(
                        uiState,
                        modifier,
                        inputText,
                        getResponse)
                }
            }
        }
    }
}

/*
@Composable
@ExperimentalMaterial3Api
@Preview
fun DrawerPreview() {
    val messageViewModel = MessageViewModel()
    SampleDrawer(uiState , Modifier.fillMaxWidth())
}

 */
