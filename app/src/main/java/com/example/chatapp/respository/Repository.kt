package com.example.chatapp.respository

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.chatapp.respository.firebase.FirebaseHelper
import com.example.chatapp.respository.localstorage.LocalStorageDAO
import com.example.chatapp.respository.localstorage.UserId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class Repository(private val userDetailsDAO: LocalStorageDAO) {

    private val firebaseHelperClass = FirebaseHelper()

    fun getUserID(): Flow<String> = flow {
        val userIdFromLocalStorageFlow = userDetailsDAO.getUserId()
        userIdFromLocalStorageFlow.collect{ userIdFromLocalStorage ->
            if (userIdFromLocalStorage.isNullOrEmpty()){
                // send display message
                emit("Performing signIn, please wait..")
                firebaseHelperClass.getUserId().collect{ messageFromFirebase ->
                    if (messageFromFirebase.split(" ").size == 1 && messageFromFirebase.split(" ")[0].isNotEmpty()) {
                        storeUserIdLocally(messageFromFirebase)
                        // send display message
                        emit("Account creation successful")
                    } else{
                        // send display message
                        emit(messageFromFirebase)
                    }
                }
            } else {
                // send display message
                emit("Account creation successful")
            }
        }
    }

    @WorkerThread
    private suspend fun storeUserIdLocally(userId: String) {
        val newUserId = UserId(0, userId)
        userDetailsDAO.insertUserId(newUserId)
    }
}