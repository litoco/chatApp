package com.example.chatapp.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.respository.Repository
import com.example.chatapp.respository.localstorage.AppDatabase
import kotlinx.coroutines.launch

class SearchUserViewModel(application: Application): AndroidViewModel(application) {
    private val localStorageDAO = AppDatabase.getDatabase(application).localStorageDAO()
    private val repository = Repository(localStorageDAO)
    private var usernameList = mutableStateListOf<String>()
    private var textBoxText = mutableStateOf("Click on the button below to show users")

    fun getUserNameList(): SnapshotStateList<String> {
        return usernameList
    }

    fun populateList(){
        textBoxText.value = "Please wait.."
        viewModelScope.launch {
            repository.getAllUsersFlow().collect{
                usernameList.clear()
                usernameList.addAll(it)
                if (usernameList.size == 0){
                    textBoxText.value = "Please check you internet connection \ntry again"
                }
            }
        }
    }

    fun getTextBoxText(): String {
        return textBoxText.value
    }
}