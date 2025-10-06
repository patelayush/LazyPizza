package com.example.lazypizza.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lazypizza.repository.FirebaseRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

enum class Screen {
    HomeScreen,
}

class HomeViewModel : ViewModel() {

    var currentScreen = mutableStateOf<Screen>(Screen.HomeScreen)
        private set

    init {
        viewModelScope.launch {
            async { FirebaseRepository().getPizzaUrls() }
            async { FirebaseRepository().getMenuCategories() }
            async { FirebaseRepository().getSauces() }
        }.invokeOnCompletion {
            Log.d("HomeViewModel", "Finished fetching data")
        }
    }

    fun handleNavigation(screen: Screen) {
        currentScreen.value = screen
    }
}