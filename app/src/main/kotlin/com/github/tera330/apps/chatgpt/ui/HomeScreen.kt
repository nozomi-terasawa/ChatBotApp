package com.github.tera330.apps.chatgpt.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontSynthesis.Companion.All
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.tera330.apps.chatgpt.viewmodel.MessageUiState
import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Message
import com.github.tera330.apps.chatgpt.roomdatabase.Conversation
import com.github.tera330.apps.chatgpt.roomdatabase.ConversationRepository
import com.github.tera330.apps.chatgpt.roomdatabase.MessageData
import com.github.tera330.apps.chatgpt.roomdatabase.MessageDataRepository
import com.github.tera330.apps.chatgpt.roomdatabase.MessageDatabase
import com.github.tera330.apps.chatgpt.viewmodel.SaveMessageViewModel
import com.github.tera330.apps.chatgpt.viewmodel.SavedUiState
import kotlinx.coroutines.CoroutineScope
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
    updateMessageList: (MutableList<Message>) -> Unit,
    updateLoad: () -> Unit,
    updateSuccess: () -> Unit,
    updateStr: (String) -> Unit,
    updateNotYet: () -> Unit,

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
                    DrawerContent(
                        conversation = savedState.conversationList,
                        onItemCLicked = { selectedItem ->
                            val id = selectedItem.conversationsId
                            scope.launch {
                                val messageData = messageViewModel.getMessagesByConversationId(id)
                                val messageList = messageData.map { messageData ->
                                    Message(
                                        role = messageData.role,
                                        content = messageData.message
                                    )
                                }
                                updateMessageList(messageList.toMutableList())
                            }
                        },
                        drawerState,
                        modifier,
                        scope,
                        allClear = { list -> messageViewModel.deleteAllConversation(list) },
                    )

                }
            },
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(
                            text = "ChatGPT",
                            modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center, )},
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
                    createTitle = { string -> messageViewModel.createTitle(string) },
                    messageViewModel,
                    updateLoad,
                    updateSuccess,
                    updateStr,
                    updateNotYet
                )
            }
        }
}

// Drawerに表示するコンテンツ
@ExperimentalMaterial3Api
@Composable
fun DrawerContent(
    conversation: List<Conversation>,
    onItemCLicked: (Conversation) -> Unit,
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    allClear: (MutableList<Conversation>) -> Unit,
    ) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "ChatGPT", fontSize = 35.sp, modifier = Modifier.padding(bottom = 10.dp))
        Divider()
        // リストアイテムを表示する
        LazyColumn(modifier = modifier.weight(1f)) {
            items(conversation) { item ->
                Text(
                    text = "#" + item.title,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable {
                            onItemCLicked(item)
                            scope.launch {
                                drawerState.close()
                            }
                        }
                )
                Divider() // リストアイテムの間に区切り線を追加
            }
        }
        Divider()
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 10.dp)) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = null,
                    modifier = modifier.size(25.dp)
                )
            }
            Text(text = "Setting", fontSize = 25.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 10.dp)) {
            IconButton(onClick = {
                allClear(mutableListOf()) }) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = null,
                    modifier = modifier.size(25.dp)
                )
            }
            Text(text = "All Clear", fontSize = 25.sp)
        }
    }
}
