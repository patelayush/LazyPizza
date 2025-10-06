package com.example.lazypizza.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await

data class Image(
    val name: String,
    val downloadUrl: Uri?
)

class FirebaseRepository {
    val storageRef = Firebase.storage.reference.child("images")
    val databaseRef = Firebase.firestore

    suspend fun getPizzaUrls() {
        val list = mutableListOf<Image>()
        val items = storageRef.child("pizza").listAll().await().items
        items.forEach { item ->
            list.add(
                Image(
                    name = item.name,
                    downloadUrl = item.downloadUrl.await()
                )
            )
        }
        Log.d(
            "FirebaseRepository",
            "Pizza images: $list"
        )
    }

    suspend fun getMenuCategories() {
        val categories = databaseRef.collection("menu")
                .get().await().documents.firstOrNull()?.get("categories")
        Log.d(
            "FirebaseRepository",
            "Categories: $categories"
        )
    }

    suspend fun getSauces() {
        val sauces = databaseRef.collection("sauces")
            .get().await().documents.firstOrNull()?.get("sauces")
        Log.d(
            "FirebaseRepository",
            "Sauces: $sauces"
        )
    }
}