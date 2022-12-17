package com.example.chatapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.R
import com.example.chatapp.utils.forwardingPainter
import com.example.chatapp.viewmodels.AllChatsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowUI(
    allChatsViewModel: AllChatsViewModel = viewModel()
) {
    val navController = rememberNavController()
    var titleText by remember{ mutableStateOf(allChatsViewModel.getCurrentTitle()) }

    LaunchedEffect(key1 = navController){
        val navigationDestinationFlow = navController.currentBackStackEntryFlow
        navigationDestinationFlow.collect{
            if (it.destination.route == "ChatApp"){
                titleText = "ChatApp"
                allChatsViewModel.currentTitle("ChatApp")
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
                            allChatsViewModel.currentTitle("Profile")
                            navController.navigate("Profile") {
                                popUpTo("ChatApp")
                                launchSingleTop = true
                            }
                        }) {
                            Icon(
                                painter = forwardingPainter(painter = painterResource(id = R.drawable.ic_baseline_person),
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)),
                                contentDescription = "Profile",
                                modifier = Modifier.border(width = 1.dp, shape = CircleShape, color = MaterialTheme.colorScheme.onPrimary)
                            )
                        }
                        IconButton(onClick = {
                            titleText = "Support"
                            allChatsViewModel.currentTitle("Support")
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
            NavHost(
                navController = navController,
                modifier = Modifier.padding(it),
                startDestination = "ChatApp"
            ) {
                composable(route = "ChatApp"){
                    ChatBody(navigateToSearchUser = {
                            allChatsViewModel.currentTitle("Select User")
                            titleText = "Select User"
                            navController.navigate("SelectUser") {
                                popUpTo("ChatApp")
                                launchSingleTop = true
                            }
                        },
                        allChatsViewModel
                    )
                }
                composable(route = "Profile"){
                    UserProfile()
                }
                composable(route = "Support"){
                    Support()
                }
                composable(route = "SelectUser"){
                    RandomlySearchUser()
                }
            }
        }
    )
}

@Composable
fun ChatBody(
    navigateToSearchUser:() -> Unit,
    viewModel: AllChatsViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(MaterialTheme.colorScheme.background)
    ) {

        val allChatList  = remember {
            viewModel.getAllChats()
        }

        if (allChatList.isEmpty()){
            Text(
                text = "Click on the button below\nto start", textAlign = TextAlign.Center,
                modifier = Modifier.align(alignment = Alignment.Center), color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            // Add all the chats with different person here
        }
        IconButton(
            onClick = {
                navigateToSearchUser()
            },
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.BottomEnd)
        ) {
            Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

