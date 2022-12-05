package com.example.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.ui.ShowMainScreen
import com.example.chatapp.ui.ShowUI
import com.example.chatapp.viewmodels.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppsScreen()
            }
        }
    }
}

@Composable
fun AppsScreen(
    // UI Data store
    viewModel: AppViewModel = viewModel()
) {
    var buttonText by rememberSaveable{ mutableStateOf("SignIn") }
    val text = viewModel.displayMessageState
    val buttonClickBehaviour = {
        viewModel.getUserId()
        buttonText = "Retry SignIn"
    }
    ShowMainScreen(text = text, buttonText, buttonClickBehaviour)
    if (text == "Account creation successful"){
        ShowUI()
    }

    // There should be a splash screen that will decide if the screen to show will sign in screen or chat screen
}

