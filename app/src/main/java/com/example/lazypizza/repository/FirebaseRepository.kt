package com.example.lazypizza.repository

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.storage.storage

class FirebaseRepository {
    val storageRef = Firebase.storage.reference.child("images")

    fun getPizzaUrls() {
        storageRef.child("pizza").listAll().addOnSuccessListener {
            Log.d("FirebaseRepository", "Pizza images: ${it.items}")
        }
    }
}