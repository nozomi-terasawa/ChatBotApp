package com.github.tera330.apps.chatgpt.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.tera330.apps.chatgpt.MessageUiState
import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Message
import com.github.tera330.apps.chatgpt.roomdatabase.Conversation
import com.github.tera330.apps.chatgpt.roomdatabase.ConversationRepository
import com.github.tera330.apps.chatgpt.roomdatabase.MessageData
import com.github.tera330.apps.chatgpt.roomdatabase.MessageDataRepository
import com.github.tera330.apps.chatgpt.roomdatabase.MessageDatabase
import com.github.tera330.apps.chatgpt.roomdatabase.SaveMessageViewModel
import kotlinx.coroutines.launch


@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    uiState: MessageUiState,
    modifier: Modifier,
    inputText: (String) -> Unit,
    getResponse: (String) -> Unit,
    changeList: (MutableList<Message>) -> Unit,
    clearText: () -> Unit,
    createTitle: (String) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val conversationRepository = ConversationRepository(MessageDatabase.getDatabase(LocalContext.current).conversationDao())
    val messageDataRepository = MessageDataRepository(MessageDatabase.getDatabase(LocalContext.current).messageDataDao())
    val messageViewModel: SaveMessageViewModel = viewModel {
        SaveMessageViewModel(conversationRepository, messageDataRepository)
    }
    val savedState = messageViewModel.savedUiState
    ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    DrawerContent(conversation = savedState.conversationList)
                }
            },
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("タイトル") },
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
                        },
                        actions = {
                            IconButton(onClick = {
                                scope.launch {
                                    val currentList = uiState.messageList.toList()
                                    val conversationId = messageViewModel.saveConversation().toInt() // conversationのinsert

                                    val conversationList = messageViewModel.getAllConversations()
                                    messageViewModel.updateConversationList(conversationList.toMutableList())
                                    
                                    for (message in currentList) {
                                        messageViewModel.saveMessage(
                                            MessageData(
                                                role = message.role,
                                                message = message.content,
                                                conversationId = conversationId
                                            )
                                        )
                                    }
                                    uiState.messageList.clear()

                                }
                            }) {
                                Icon(Icons.Filled.Edit, contentDescription = "Edit")
                            }
                        }
                    )
                }
            ) { innerPadding ->

                MessageBody(
                    uiState,
                    modifier,
                    inputText,
                    getResponse,
                    changeList,
                    clearText,
                    createTitle
                )
            }
        }
}

// Drawerに表示するコンテンツ
@Composable
fun DrawerContent(conversation: List<Conversation>) {
    Column(modifier = Modifier.padding(16.dp)) {
        // リストアイテムを表示する
        LazyColumn {
            items(conversation) { item ->
                Text(
                    text = item.conversationsId.toString(),
                    fontSize = 30.sp
                )
                Divider() // リストアイテムの間に区切り線を追加
            }
        }
    }
}
