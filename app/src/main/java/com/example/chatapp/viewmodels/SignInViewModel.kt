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

class SignInViewModel(application: Application) : AndroidViewModel(application) {
    var displayMessageState by mutableStateOf("Click the button below to start registration")
        private set

    // instantiate the local database
    private val database by lazy { AppDatabase.getDatabase(application) }
    private val repository by lazy { Repository(database.localStorageDAO()) }

    fun getUserId() {
        viewModelScope.launch {
            repository.getUserID().collect{ message ->
                displayMessageState = message.ifEmpty {
                    "Failed to get your user id"
                }
            }
        }
    }

    fun getSignInStatus(): Boolean {
        return repository.getSignInStatus()
    }
}