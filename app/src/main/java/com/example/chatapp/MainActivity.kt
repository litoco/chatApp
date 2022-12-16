package com.example.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.ui.ShowMainScreen
import com.example.chatapp.ui.ShowUI
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.viewmodels.SignInViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                SplashScreen()
            }
        }
    }
}

@Composable
fun SplashScreen(
    viewModel: SignInViewModel = viewModel()
) {
    Surface (
        modifier = Modifier.fillMaxSize(1f).background(MaterialTheme.colorScheme.background)
            ) {
        var isSignedIn by remember{ mutableStateOf(viewModel.getSignInStatus()) }
        if (isSignedIn){
            ShowUI()
        } else {
            SignInScreen({ isSignedIn = true}, viewModel)
        }
    }
}

@Composable
fun SignInScreen(
    // UI Data store
    changeSignInStatus: () -> Unit,
    viewModel: SignInViewModel
) {
    var buttonText by rememberSaveable{ mutableStateOf("SignIn") }
    val text = viewModel.displayMessageState
    val buttonClickBehaviour = {
        viewModel.getUserId()
        buttonText = "Retry SignIn"
    }
    ShowMainScreen(text = text, buttonText, buttonClickBehaviour)
    if (text == "Account creation successful") {
        changeSignInStatus()
    }
}

