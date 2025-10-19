package com.example.lazypizza.screens

import GradientButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lazypizza.repository.CartItem
import com.example.lazypizza.ui.theme.BG
import com.example.lazypizza.ui.theme.FontFamily
import com.example.lazypizza.ui.theme.SurfaceHigher
import com.example.lazypizza.ui.theme.SurfaceHighest
import com.example.lazypizza.ui.theme.TextPrimary
import com.example.lazypizza.ui.theme.TextSeconday
import com.example.lazypizza.viewmodel.HomeViewModel
import com.example.lazypizza.widescreens.WideOrderHistoryScreenContent

@Composable
fun OrderHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    isScreenWide: Boolean
) {
    Box(modifier.fillMaxSize().background(BG)) {
        if (isScreenWide) {
            WideOrderHistoryScreenContent(
                cartItems = mutableListOf(),
                loginClicked = {
                    //TODO()
                }
            )
        } else {
            OrderHistoryScreenContent(
                cartItems = mutableListOf(),
                loginClicked = {
                    //TODO()
                }
            )
        }
    }
}

@Composable
fun OrderHistoryScreenContent(
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

@Composable
fun EmptyHistory(modifier: Modifier, loginClicked: () -> Unit) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Not Signed In",
            fontSize = 24.sp,
            fontFamily = FontFamily,
            fontWeight = FontWeight.Medium,
            color = TextPrimary
        )
        Text(
            text = "Please sign in to view your order history.",
            fontSize = 14.sp,
            fontFamily = FontFamily,
            fontWeight = FontWeight.Normal,
            color = TextSeconday,
        )
        GradientButton(
            modifier = Modifier
                .padding(top = 10.dp),
            text = "Sign In",
            onCLick = loginClicked
        )
    }
}


@Preview
@Composable
private fun OrderHistoryScreenPreview() {
    OrderHistoryScreenContent(
        cartItems = mutableListOf<CartItem>() as SnapshotStateList<CartItem>,
        loginClicked = {}
    )
}