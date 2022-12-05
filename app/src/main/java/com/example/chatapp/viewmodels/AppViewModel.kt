package com.example.chatapp.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.respository.Repository
import com.example.chatapp.respository.localstorage.AppDatabase
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {
    var displayMessageState by mutableStateOf("Click the button below to start registration")
        private set

    // instantiate the local database
    private val database by lazy { AppDatabase.getDatabase(application) }
    private val repository by lazy { Repository(database.userDetailsDAO()) }

    fun getUserId() {
        viewModelScope.launch {
            repository.getUserID().collect{ message ->
                displayMessageState = message.ifEmpty {
                    "Failed to get your user id"
                }
            }
        }
    }
}