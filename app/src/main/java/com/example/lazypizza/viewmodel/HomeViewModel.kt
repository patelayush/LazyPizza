package com.example.lazypizza.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

enum class Screen {
    HomeScreen,
}

class HomeViewModel : ViewModel() {

    var currentScreen = mutableStateOf<Screen>(Screen.HomeScreen)
        private set

    fun handleNavigation(screen: Screen) {
        currentScreen.value = screen
    }
}