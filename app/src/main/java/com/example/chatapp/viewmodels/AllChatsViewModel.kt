package com.example.chatapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.models.AllChatsModel
import com.example.chatapp.respository.Repository
import com.example.chatapp.respository.localstorage.AppDatabase
import kotlinx.coroutines.launch

class AllChatsViewModel(application: Application): AndroidViewModel(application) {

    private val localStorageDAO = AppDatabase.getDatabase(application).localStorageDAO()
    private val repository = Repository(localStorageDAO)

    private var allChatsLists: List<AllChatsModel> = mutableListOf()

    fun getAllChats(): List<AllChatsModel> {
        viewModelScope.launch {
            repository.getAllChatsFlow().collect{
                if (it != null){
                    allChatsLists = it
                }
            }
        }
        return allChatsLists
    }

}