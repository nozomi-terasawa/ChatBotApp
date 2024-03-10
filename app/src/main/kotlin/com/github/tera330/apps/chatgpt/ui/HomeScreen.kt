package com.github.tera330.apps.chatgpt.ui

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.github.tera330.apps.chatgpt.MessageUiState
import com.github.tera330.apps.chatgpt.encryptedsharedpreferences.EncryptedSharedPreferences
import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Message
import com.github.tera330.apps.chatgpt.roomdatabase.ConversationRepository
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
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val repository = ConversationRepository(MessageDatabase.getDatabase(LocalContext.current).conversationDao())
    val messageViewModel: SaveMessageViewModel = viewModel {
        SaveMessageViewModel(repository)
    }
    val navController: NavHostController = rememberNavController()




    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { ModalDrawerSheet {
            // todo DrawerContent
        } },
    ) {
        Scaffold(
            topBar = {
                    Log.d("result", "Current destination: ${navController.currentDestination?.route.toString()}")

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
                                    messageViewModel.saveConversation()
                                }
                            }) {
                                Icon(Icons.Filled.Edit, contentDescription = "Edit")
                            }
                        }
                    )
            }
        ) { innerPadding ->

            MessageBody(uiState, modifier, inputText, getResponse, changeList, clearText)
        }
    }
}

enum class Screen {
    SaveKeyScreen,
    HomeScreen
}
