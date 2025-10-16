package com.example.lazypizza.repository

data class Cart(
    val items: List<CartItem>? = null,
    val cartTotal: Float = 0f
)

data class CartItem(
    val item: MenuItem,
    val quantity: Int,
    val toppings: List<MenuItem>? = null
)