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
import com.example.lazypizza.screens.EmptyHistory

@Composable
fun WideOrderHistoryScreenContent(
    modifier: Modifier = Modifier,
    cartItems: MutableList<CartItem>,
    loginClicked: () -> Unit,
) {
    Box(modifier.fillMaxSize()) {
        if (cartItems.isEmpty()) {
            EmptyHistory(
                modifier = Modifier.padding(top = 120.dp),
                loginClicked = loginClicked
            )
        } else {

        }
    }
}


@Preview
@Composable
private fun WideOrderHistoryScreenPreview() {
    WideOrderHistoryScreenContent(
        cartItems = mutableListOf<CartItem>() as SnapshotStateList<CartItem>,
        loginClicked = {}
    )
}