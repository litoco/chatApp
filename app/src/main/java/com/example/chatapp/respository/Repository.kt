package com.example.chatapp.respository

import androidx.annotation.WorkerThread
import com.example.chatapp.respository.firebase.FirebaseHelper
import com.example.chatapp.respository.localstorage.LocalStorageDAO
import com.example.chatapp.respository.localstorage.UserId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(private val userDetailsDAO: LocalStorageDAO) {

    private val firebaseHelperClass = FirebaseHelper()

    fun getUserID(): Flow<String> = flow {
        userDetailsDAO.getUserId().collect { userIdFromLocalStorage ->
            if (userIdFromLocalStorage.isNullOrEmpty()) {
                emit("Performing signIn, please wait..")
                firebaseHelperClass.getUserID().collect { messageFromFirebase ->
                    if (messageFromFirebase.split(" ").size == 1 && messageFromFirebase.split(" ")[0].isNotEmpty()) {
                        storeUserIdLocally(messageFromFirebase)
                        emit("Account creation successful")
                    } else {
                        emit(messageFromFirebase)
                    }
                }
            } else {
                emit("Account creation successful")
            }
        }
    }

    @WorkerThread
    private suspend fun storeUserIdLocally(userId: String) {
        val newUserId = UserId(0, userId)
        userDetailsDAO.insertUserId(newUserId)
    }

    fun getSignInStatus(): Boolean {
        return firebaseHelperClass.isSignedIn()
    }
}