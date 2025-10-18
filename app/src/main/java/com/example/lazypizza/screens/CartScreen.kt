package com.example.lazypizza.screens

import GradientButton
import MenuImage
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.lazypizza.R
import com.example.lazypizza.repository.CartItem
import com.example.lazypizza.repository.MenuItem
import com.example.lazypizza.ui.theme.BG
import com.example.lazypizza.ui.theme.FontFamily
import com.example.lazypizza.ui.theme.Outline
import com.example.lazypizza.ui.theme.Primary
import com.example.lazypizza.ui.theme.SurfaceHigher
import com.example.lazypizza.ui.theme.SurfaceHighest
import com.example.lazypizza.ui.theme.TextPrimary
import com.example.lazypizza.ui.theme.TextSeconday
import com.example.lazypizza.viewmodel.HomeViewModel
import com.example.lazypizza.viewmodel.Tab
import com.example.lazypizza.widescreens.WideCartScreenContent

@Composable
fun CartScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel, isScreenWide: Boolean) {
    Box(modifier.fillMaxSize().background(BG)) {
        if (isScreenWide) {
            WideCartScreenContent(
                cartItems = viewModel.cartItems,
                shuffledRecs = remember(viewModel.cartItems.size) {viewModel.menuItems.value?.getShuffledRecs()?.toMutableList()
                    ?.subtract(viewModel.cartItems.map { it.item }.toList())?.toList()},
                backToMenu = {
                    viewModel.switchTab(Tab.MenuScreen)
                },
                proceedToCheckout = {

                },
                quantityAdded = { index ->
                    val item = viewModel.cartItems[index]
                    viewModel.cartItems[index] = item.copy(
                        quantity = item.quantity + 1,
                        itemTotal = item.itemTotal + ((item.item.price?.toFloat()
                            ?: 0f) + item.getPizzaToppingTotalPrice())
                    )
                },
                quantityRemoved = { index ->
                    val item = viewModel.cartItems[index]
                    viewModel.cartItems[index] = item.copy(
                        quantity = item.quantity - 1,
                        itemTotal = item.itemTotal - ((item.item.price?.toFloat()
                            ?: 0f) + item.getPizzaToppingTotalPrice())
                    )
                },
                deleteCart = { index ->
                    viewModel.cartItems.removeAt(index)
                },
                addToCart = { item ->
                    viewModel.addToCart(
                        CartItem(
                            item = item,
                            itemTotal = item.price?.toFloat() ?: 0f,
                            quantity = 1,
                        )
                    )
                }
            )
        } else {
            CartScreenContent(
                modifier = Modifier.padding(bottom = 80.dp),
                cartItems = viewModel.cartItems,
                shuffledRecs = remember(viewModel.cartItems.size) {viewModel.menuItems.value?.getShuffledRecs()?.toMutableList()
                    ?.subtract(viewModel.cartItems.map { it.item }.toList())?.toList()},
                backToMenu = {
                    viewModel.switchTab(Tab.MenuScreen)
                },
                proceedToCheckout = {

                },
                quantityAdded = { index ->
                    val item = viewModel.cartItems[index]
                    viewModel.cartItems[index] = item.copy(
                        quantity = item.quantity + 1,
                        itemTotal = item.itemTotal + ((item.item.price?.toFloat()
                            ?: 0f) + item.getPizzaToppingTotalPrice())
                    )
                },
                quantityRemoved = { index ->
                    val item = viewModel.cartItems[index]
                    viewModel.cartItems[index] = item.copy(
                        quantity = item.quantity - 1,
                        itemTotal = item.itemTotal - ((item.item.price?.toFloat()
                            ?: 0f) + item.getPizzaToppingTotalPrice())
                    )
                },
                deleteCart = { index ->
                    viewModel.cartItems.removeAt(index)
                },
                addToCart = { item ->
                    viewModel.addToCart(
                        CartItem(
                            item = item,
                            itemTotal = item.price?.toFloat() ?: 0f,
                            quantity = 1,
                        )
                    )
                }
            )
        }
    }
}

@Composable
fun CartScreenContent(
    modifier: Modifier = Modifier,
    shuffledRecs: List<MenuItem?>?,
    cartItems: SnapshotStateList<CartItem>,
    backToMenu: () -> Unit,
    proceedToCheckout: () -> Unit,
    quantityAdded: (Int) -> Unit,
    quantityRemoved: (Int) -> Unit,
    deleteCart: (Int) -> Unit,
    addToCart: (MenuItem) -> Unit,
) {
    Box(modifier.fillMaxSize()) {
        if (cartItems.isEmpty()) {
            EmptyCart(
                modifier = Modifier.padding(top = 120.dp),
                backToMenu = backToMenu
            )
        } else {
            LazyColumn {
                itemsIndexed(cartItems) { index, cartItem ->
                    MenuItemCard(
                        modifier = Modifier.padding(horizontal = 15.dp),
                        item = cartItem.item,
                        quantity = cartItems[index].quantity,
                        itemTotal = cartItems[index].itemTotal,
                        forCart = true,
                        toppingsTotalPrice = cartItem.getPizzaToppingTotalPrice(),
                        toppings = cartItem.toppings,
                        quantityAdded = {
                            quantityAdded(index)
                        },
                        quantityRemoved = {
                            quantityRemoved(index)
                        },
                        deleteCart = {
                            deleteCart(index)
                        },
                    )
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text(
                            text = "Recommended to add to your order".uppercase(),
                            fontSize = 12.sp,
                            fontFamily = FontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = TextSeconday,
                            modifier = Modifier.padding(top = 20.dp).padding(horizontal = 15.dp)
                        )
                        LazyRow(contentPadding = PaddingValues(horizontal = 15.dp)) {
                            itemsIndexed(shuffledRecs ?: listOf()) { index, item ->
                                Card(
                                    modifier = Modifier.width(160.dp).padding(end = 10.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = SurfaceHigher
                                    ),
                                    elevation = CardDefaults.cardElevation(1.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {
                                        MenuImage(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(120.dp)
                                                .background(SurfaceHighest),
                                            imageUrl = item?.imageUrl ?: ""
                                        )
                                        Column(Modifier.padding(10.dp)) {
                                            Text(
                                                text = item?.name ?: "",
                                                fontSize = 16.sp,
                                                lineHeight = 1.em,
                                                fontFamily = FontFamily,
                                                fontWeight = FontWeight.Normal,
                                                color = TextSeconday,
                                            )
                                            Row(
                                                modifier = Modifier.fillMaxWidth()
                                                    .padding(top = 5.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "${item?.price?.toFloat()}",
                                                    fontSize = 24.sp,
                                                    fontFamily = FontFamily,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = TextPrimary,
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .clickable {
                                                            item?.let { addToCart(it) }
                                                        }
                                                        .border(
                                                            width = 1.dp,
                                                            color = Outline,
                                                            shape = RoundedCornerShape(8.dp)
                                                        )
                                                        .size(22.dp),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(
                                                        painter = painterResource(
                                                            R.drawable.ic_plus
                                                        ),
                                                        contentDescription = "Plus Icon",
                                                        tint = Primary,
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
                item {
                    Spacer(Modifier.height(120.dp))
                }
            }
            GradientButton(
                modifier = Modifier.padding(15.dp).padding(bottom = 30.dp)
                    .align(Alignment.BottomCenter).fillMaxWidth(),
                text = "Proceed to Checkout ($${
                    "%.2f".format(
                        cartItems.sumOf { it.itemTotal.toDouble() })
                })",
                onCLick = proceedToCheckout
            )
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
        backToMenu = {},
        proceedToCheckout = {},
        quantityAdded = {},
        quantityRemoved = {},
        shuffledRecs = null,
        deleteCart = {},
        addToCart = {}
    )
}