package com.example.chatapp.respository.firebase

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseHelper {

    private val firebaseFireStore = Firebase.firestore

    fun getUserID() = flow {

        emit("Contacting server..")
        try {
            val signInTask = Firebase.auth.signInAnonymously().await()
            if (signInTask.user != null && signInTask.user!!.uid.isNotEmpty()){
                emit("Connection successful\nCreating profile, please wait...")
                kotlinx.coroutines.delay(10000)
                createUserProfile(signInTask.user!!.uid).collect{
                    Log.e("FirebaseHelper", "getUserId: it is $it ${it == "Failed"} ${signInTask.user!!.uid}")
                    if (it == "Failed"){
                        emit("Profile creation failed!!")
                    } else {
                        emit(signInTask.user!!.uid)
                    }
                }
            } else {
                emit("SignIn failed, please retry")
            }
        } catch (e: Exception){
            emit("Can't connect to the server\nPlease check your internet connection and retry")
        }
    }


    private fun createUserProfile(currentUserId: String) = flow {
        val string =
            "New device ${android.os.Build.MANUFACTURER} ${android.os.Build.PRODUCT} connected here"
        val data = hashMapOf(
            "UserDetails" to string
        )

        try {
            val task = firebaseFireStore.collection("users").document(currentUserId).set(data)
            kotlinx.coroutines.withTimeout(5000){
                task.await()
            }
            if (task.isSuccessful){
                emit("Success")
            } else {
                emit("Failed")
            }
        } catch (e: Exception){
            emit("Failed")
        }
    }
}