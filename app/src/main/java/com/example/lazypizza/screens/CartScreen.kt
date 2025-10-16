package com.example.lazypizza.screens

import GradientButton
import androidx.activity.compose.BackHandler
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
import com.example.lazypizza.ui.theme.FontFamily
import com.example.lazypizza.ui.theme.SurfaceHigher
import com.example.lazypizza.ui.theme.SurfaceHighest
import com.example.lazypizza.ui.theme.TextPrimary
import com.example.lazypizza.ui.theme.TextSeconday
import com.example.lazypizza.viewmodel.HomeViewModel
import com.example.lazypizza.viewmodel.Screen
import com.example.lazypizza.widescreens.WideCartScreenContent

@Composable
fun CartScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel, isScreenWide: Boolean) {
    Box(modifier.fillMaxSize().background(SurfaceHighest)) {
        if (isScreenWide) {
            WideCartScreenContent(
                cartItems = viewModel.cartItems,
                backToMenu = {
                    viewModel.switchTab(Screen.MenuScreen)
                }
            )
        } else {
            CartScreenContent(
                cartItems = viewModel.cartItems,
                backToMenu = {
                    viewModel.switchTab(Screen.MenuScreen)
                }
            )
        }
    }
}

@Composable
fun CartScreenContent(
    modifier: Modifier = Modifier,
    cartItems: SnapshotStateList<CartItem>,
    backToMenu: () -> Unit,
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

@Composable
fun EmptyCart(modifier: Modifier, backToMenu: () -> Unit) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your Cart Is Empty",
            fontSize = 24.sp,
            fontFamily = FontFamily,
            fontWeight = FontWeight.Medium,
            color = TextPrimary
        )
        Text(
            text = "Head back to the menu and grab a pizza you love.",
            fontSize = 14.sp,
            fontFamily = FontFamily,
            fontWeight = FontWeight.Normal,
            color = TextSeconday,
        )
        GradientButton(
            modifier = Modifier
                .padding(top = 10.dp),
            text = "Back to Menu",
            onCLick = backToMenu
        )
    }
}


@Preview
@Composable
private fun CartScreenPreview() {
    CartScreenContent(
        cartItems = mutableListOf<CartItem>() as SnapshotStateList<CartItem>,
        backToMenu = {}
    )
}