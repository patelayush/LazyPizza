package com.example.lazypizza.widescreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazypizza.repository.CartItem
import com.example.lazypizza.screens.EmptyCart

@Composable
fun WideCartScreenContent(
    modifier: Modifier = Modifier,
    cartItems: SnapshotStateList<CartItem>,
    backToMenu: () -> Unit,
    proceedToCheckout: () -> Unit
) {
    Box(modifier.fillMaxSize()) {
        if (cartItems.isEmpty()) {
            EmptyCart(
                modifier = Modifier.padding(top = 120.dp),
                backToMenu = backToMenu
            )
        } else {

        }
    }
}


@Preview
@Composable
private fun WideCartScreenPreview() {
    WideCartScreenContent(
        cartItems = mutableListOf<CartItem>() as SnapshotStateList<CartItem>,
        backToMenu = {},
        proceedToCheckout = {}
    )
}