package com.example.chatapp.respository.firebase

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FirebaseHelper {

    private val firebaseFireStore = Firebase.firestore

    fun getUserID(): Flow<String> = callbackFlow {
        val firebaseAuth = Firebase.auth
        val flowScope = this
        // send display message
        trySend("Contacting server..")
        firebaseAuth.signInAnonymously().addOnCompleteListener { authProcess ->
            if (authProcess.isSuccessful) {
                if (firebaseAuth.currentUser != null) {
                    // send display message
                    trySend("Creating profile..")
                    flowScope.launch {
                        createUserProfile(firebaseAuth.currentUser!!.uid).collect {
                            if (it.split(" ").size == 1 && it.split(" ")[0].isNotEmpty()) {
                                if (it == "Successful") {
                                    // send userId
                                    trySend(firebaseAuth.currentUser!!.uid)
                                } else {
                                    // send display message
                                    trySend("Some problem occurred while adding you to the database..")
                                }
                            } else {
                                // send display message
                                trySend(it)
                            }
                        }
                    }
                } else {
                    // send display message
                    trySend("Some problem occurred while creating profile")
                }
            } else {
                // send display message
                trySend(" User creation failed!!\nPlease check your internet connection and try again ")
            }
        }
        awaitClose { this.cancel() }
    }.catch {
        Log.e("TAG", "getUserID: Firebase error $this")
    }

    private fun createUserProfile(currentUserId: String): Flow<String> = callbackFlow {
        trySend("Adding you to the database..")
        val string =
            "New device ${android.os.Build.MANUFACTURER} ${android.os.Build.PRODUCT} connected"
        val data = hashMapOf(
            "UserDetails" to string
        )
        firebaseFireStore.collection("users").document(currentUserId).set(data)
            .addOnSuccessListener {
                trySend("Successful")
            }
            .addOnFailureListener {
                trySend("Failed")
            }
        awaitClose { this.cancel() }
    }
}