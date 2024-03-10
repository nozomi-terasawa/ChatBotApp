package com.github.tera330.apps.chatgpt

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.tera330.apps.chatgpt.encryptedsharedpreferences.EncryptedSharedPreferences
import com.github.tera330.apps.chatgpt.encryptedsharedpreferences.InputKeyScreen
import com.github.tera330.apps.chatgpt.model.chatcompletions.child.Message
import com.github.tera330.apps.chatgpt.ui.HomeScreen
import com.github.tera330.apps.chatgpt.ui.Screen


@ExperimentalMaterial3Api
@Composable
fun AppNav(
    modifier: Modifier,
    uiState: MessageUiState,
    inputText: (String) -> Unit,
    getResponse: (String) -> Unit,
    changeList: (MutableList<Message>) -> Unit,
    clearText: () -> Unit,
) {
    val startDestination = Screen.SaveKeyScreen.name
    val navController: NavHostController = rememberNavController()
    val encryptedSharedPreferences = EncryptedSharedPreferences(
        LocalContext.current)
    val key = encryptedSharedPreferences.getData("pass")

    /*
    val startDestination = if (key != null) {
        com.github.tera330.apps.chatgpt.ui.Screen.HomeScreen.name
    } else {
        com.github.tera330.apps.chatgpt.ui.Screen.SaveKeyScreen.name
    }

     */

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = com.github.tera330.apps.chatgpt.ui.Screen.SaveKeyScreen.name) {
            InputKeyScreen(navigateHome = { navController.navigate(Screen.HomeScreen.name) })
        }
        composable(route = com.github.tera330.apps.chatgpt.ui.Screen.HomeScreen.name) {
            HomeScreen(
                uiState = uiState,
                inputText = inputText,
                getResponse = getResponse,
                changeList = changeList,
                clearText = clearText,
                modifier = Modifier
            )
        }
        Log.d(
            "result",
            "Current destination: ${navController.currentDestination?.route.toString()}"
        )
    }
}