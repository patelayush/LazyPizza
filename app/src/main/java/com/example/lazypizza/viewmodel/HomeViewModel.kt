package com.example.lazypizza.viewmodel

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lazypizza.R
import com.example.lazypizza.repository.CartItem
import com.example.lazypizza.repository.LazyPizzaResponse
import com.example.lazypizza.repository.Pizza
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.launch

enum class Tab(val title: String, val icon: Int? = null) {
    MenuScreen("Menu", R.drawable.ic_book),
    CartScreen("Cart", R.drawable.ic_cart),
    OrderHistoryScreen("History", R.drawable.ic_history)
}

enum class MenuStack(val title: String) {
    MenuScreen("Menu"),
    PizzaScreen("Pizza Details"),
}

const val imageBaseUrl = "https://pl-coding.com/wp-content/uploads/lazypizza/"

class HomeViewModel() : ViewModel() {
    val databaseRef = Firebase.database.getReference("menu/")
    val isLoading = mutableStateOf<Boolean>(false)

    val error = mutableStateOf<String>("")

    val menuItems = mutableStateOf<LazyPizzaResponse?>(null)

    val selectedPizza = mutableStateOf<Pizza?>(null)

    var currentMenuStackScreen = mutableStateOf<MenuStack>(MenuStack.MenuScreen)
        private set

    var currentTabSelected = mutableStateOf<Tab>(Tab.MenuScreen)
        private set

    var cartTotal = mutableFloatStateOf(0f)
    var cartItems = mutableStateListOf<CartItem>()

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

    fun handleMenuStackNavigation(screen: MenuStack) {
        currentMenuStackScreen.value = screen
    }

    fun switchTab(tab: Tab) {
        currentTabSelected.value = tab
        currentMenuStackScreen.value = MenuStack.MenuScreen
    }

    fun addToCart(cartItem: CartItem) {
        cartItems.add(cartItem)
        //  cartItems.add(CartItem(item, itemTotal, toppings = ))
    }

    fun getCheckoutPrice(): Float {
       return cartItems.sumOf { it.itemTotal.toDouble() }.toFloat()
    }
}