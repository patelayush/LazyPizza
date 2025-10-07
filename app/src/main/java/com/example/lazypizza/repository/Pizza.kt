package com.example.lazypizza.repository

data class Pizza(
    val ingredients: List<String>? = null,
) : MenuItem()