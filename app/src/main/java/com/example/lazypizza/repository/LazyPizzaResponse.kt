package com.example.lazypizza.repository

data class LazyPizzaResponse(
    val drinks: List<MenuItem?>? = null,
    val iceCreams: List<MenuItem?>? = null,
    val pizzas: List<Pizza?>? = null,
    val sauces: List<MenuItem?>? = null,
    val toppings: List<MenuItem>? = null
) {
    fun getShuffledRecs(): List<MenuItem?> {
        val recs = sauces?.toMutableList()
        recs?.addAll(drinks ?: listOf())
        return recs?.shuffled()?.toList() ?: listOf()
    }
}

val menuCategories = listOf(
    "Pizza",
    "Drinks",
    "Sauces",
    "Ice Cream"
)
