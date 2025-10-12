package com.example.lazypizza.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lazypizza.repository.LazyPizzaResponse
import com.example.lazypizza.repository.Pizza
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.launch

enum class Screen {
    HomeScreen,
    PizzaScreen
}

const val imageBaseUrl = "https://pl-coding.com/wp-content/uploads/lazypizza/"

class HomeViewModel() : ViewModel() {
    val databaseRef = Firebase.database.getReference("menu/")
    val isLoading = mutableStateOf<Boolean>(false)

    val error = mutableStateOf<String>("")

    val menuItems = mutableStateOf<LazyPizzaResponse?>(null)

    val selectedPizza = mutableStateOf<Pizza?>(null)

    var currentScreen = mutableStateOf<Screen>(Screen.HomeScreen)
        private set

    fun fetchData() {
        viewModelScope.launch {
            isLoading.value = true
            getMenuItems()
            // async { FirebaseRepository().getSauces() }
        }.invokeOnCompletion {
            isLoading.value = false
        }
    }

    fun getMenuItems() {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                menuItems.value = snapshot.getValue(LazyPizzaResponse::class.java)
            }

            override fun onCancelled(error: DatabaseError) {
                this@HomeViewModel.error.value = error.message
            }
        })
    }

    fun handleNavigation(screen: Screen) {
        currentScreen.value = screen
    }
}