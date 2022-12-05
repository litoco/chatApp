package com.example.chatapp.respository.firebase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseHelper {

    private val firebaseFireStore = Firebase.firestore

    fun getUserId(): Flow<String> = callbackFlow {
        trySend("Signing in..")

        val firebaseAuth = Firebase.auth
        firebaseAuth.signInAnonymously().addOnCompleteListener{ authResult ->
            if (authResult.isSuccessful){
                if (firebaseAuth.currentUser != null) {
                    trySend("Creating profile..")
                    createUserProfile(firebaseAuth.currentUser!!.uid, this)
                } else {
                    trySend("Some problem occurred while performing signIn")
                }
            } else {
                trySend("Failed to signIn")
            }
        }
        awaitClose {this.cancel()}
    }

    private fun createUserProfile(currentUserId: String, producerScope: ProducerScope<String>){
        val string =
            "New device ${android.os.Build.MANUFACTURER} ${android.os.Build.PRODUCT} connected"
        val data = hashMapOf(
            "UserDetails" to string
        )

        firebaseFireStore.collection("users").document(currentUserId).set(data).addOnCompleteListener{ userProfileCreationTask ->
            producerScope.trySend(if (userProfileCreationTask.isSuccessful) {currentUserId} else {"Failed to create profile, please retry"})
        }
    }
}