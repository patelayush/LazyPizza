package com.example.lazypizza.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lazypizza.repository.FirebaseRepository

enum class Screen {
    HomeScreen,
}

class HomeViewModel : ViewModel() {

    var currentScreen = mutableStateOf<Screen>(Screen.HomeScreen)
        private set

    init {
        FirebaseRepository().getPizzaUrls()
    }

    fun handleNavigation(screen: Screen) {
        currentScreen.value = screen
    }
}