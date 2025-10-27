package com.example.lazypizza.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lazypizza.R
import com.example.lazypizza.repository.CartItem
import com.example.lazypizza.repository.LazyPizzaResponse
import com.example.lazypizza.repository.MenuItem
import com.example.lazypizza.repository.Pizza
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.delay
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

class HomeViewModel() : ViewModel() {
    val databaseRef = Firebase.database.getReference("menu/")
    val isLoading = mutableStateOf<Boolean>(false)

    val error = mutableStateOf<String>("")
    var snackbarMessage = mutableStateOf("")
        private set

    val menuItems = mutableStateOf<LazyPizzaResponse?>(null)

    val selectedPizza = mutableStateOf<Pizza?>(null)

    var currentMenuStackScreen = mutableStateOf<MenuStack>(MenuStack.MenuScreen)
        private set

    var currentTabSelected = mutableStateOf<Tab>(Tab.MenuScreen)
        private set

    var cartBadgeCount = mutableIntStateOf(0)
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
        cartBadgeCount.intValue++
        cartItems.add(cartItem)
    }

    fun getCheckoutPrice(): Float {
        return cartItems.sumOf { it.itemTotal.toDouble() }.toFloat()
    }

    fun increaseQuantity(item: MenuItem) {
        val cartItemToUpdate = cartItems.find { it.item.name == item.name }
        if (cartItemToUpdate != null) {
            val updatedItem = cartItemToUpdate.copy(
                quantity = cartItemToUpdate.quantity + 1,
                itemTotal = cartItemToUpdate.itemTotal + ((cartItemToUpdate.item.price?.toFloat()
                    ?: 0f) + cartItemToUpdate.getPizzaToppingTotalPrice())
            )
            val index = cartItems.indexOf(cartItemToUpdate)
            if (index != -1) {
                cartItems[index] = updatedItem
            }
            cartBadgeCount.intValue++
        } else {
            addToCart(
                CartItem(
                    item = item,
                    itemTotal = item.price?.toFloat() ?: 0f,
                    quantity = 1,
                )
            )
        }
    }

    fun decreaseQuantity(item: MenuItem) {
        val cartItemToUpdate = cartItems.find { it.item.name == item.name }
        if (cartItemToUpdate != null && cartItemToUpdate.quantity > 1) {
            val updatedItem = cartItemToUpdate.copy(
                quantity = cartItemToUpdate.quantity - 1,
                itemTotal = cartItemToUpdate.itemTotal - ((cartItemToUpdate.item.price?.toFloat()
                    ?: 0f) + cartItemToUpdate.getPizzaToppingTotalPrice())
            )
            val index = cartItems.indexOf(cartItemToUpdate)
            if (index != -1) {
                cartItems[index] = updatedItem
            }
            cartBadgeCount.intValue--
        } else if(cartItemToUpdate?.quantity == 1) {
            deleteCartItem(item)
        }
    }

    fun deleteCartItem(item: MenuItem) {
        cartBadgeCount.intValue -= cartItems.find { it.item.name == item.name }?.quantity ?: 0
        cartItems.removeAll { it.item.name == item.name }
    }

    fun showSnackbar(string: String) {
        snackbarMessage.value = string
        viewModelScope.launch {
            delay(2000)
            snackbarMessage.value = ""
        }
    }
}