package com.example.lazypizza.repository

data class LazyPizzaResponse(
    val drinks: List<MenuItem?>? = null,
    val iceCreams: List<MenuItem?>? = null,
    val pizzas: List<Pizza?>? = null,
    val sauces: List<MenuItem?>? = null,
    val toppings: List<MenuItem?>? = null
)

val menuCategories = listOf(
    "Pizza",
    "Drinks",
    "Sauces",
    "Ice Cream"
)
