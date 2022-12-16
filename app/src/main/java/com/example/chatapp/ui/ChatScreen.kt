package com.example.chatapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowUI() {
    val navController = rememberNavController()
    var titleText by remember{ mutableStateOf("ChatApp") }

    LaunchedEffect(key1 = navController){
        val navigationDestinationFlow = navController.currentBackStackEntryFlow
        navigationDestinationFlow.collect{
            if (it.destination.route == "ChatApp"){
                titleText = "ChatApp"
            }
        }
    }



    Scaffold (
        topBar = {
            TopAppBar (
                title = { Text(text = titleText)},
                navigationIcon = {
                    if (titleText != "ChatApp"){
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = "BackButton"
                            )
                        }
                    }
                },
                actions = {
                    if (titleText == "ChatApp") {
                        IconButton(onClick = {
                            titleText = "Profile"
                            navController.navigate("Profile") {
                                popUpTo("ChatApp")
                                launchSingleTop = true
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Localized description"
                            )
                        }
                        IconButton(onClick = {
                            titleText = "Support"
                            navController.navigate("Support") {
                                popUpTo("ChatApp")
                                launchSingleTop = true
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                }
            )
        },
        content = {
            MainScreen(it, navController)
        }
    )
}

@Composable
fun MainScreen(paddingValues: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        modifier = Modifier.padding(paddingValues),
        startDestination = "ChatApp"
    ) {
        composable(route = "ChatApp"){
            ChatBody()
        }
        composable(route = "Profile"){
            UserProfile()
        }
        composable(route = "Support"){
            Support()
        }
    }
}

@Composable
fun ChatBody() {
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TODO:
        // This screen will contain the chat histories, profile on top right corner and a new chat button
        // the chats must be sorted chronologically (latest first)
        // clicking on chat should open chat window with all the chats with that person
        // clicking on profile should let you edit your profile and let you sign in if you are using anonymous sign in and preferences
        // clicking on the new chat button should pick a random person

        // create a UI
        // add functionality
        // test

    }
}

