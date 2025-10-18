package com.example.lazypizza.widescreens

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
import com.example.lazypizza.screens.EmptyCart
import com.example.lazypizza.screens.MenuItemCard
import com.example.lazypizza.ui.theme.FontFamily
import com.example.lazypizza.ui.theme.Outline
import com.example.lazypizza.ui.theme.Primary
import com.example.lazypizza.ui.theme.SurfaceHigher
import com.example.lazypizza.ui.theme.SurfaceHighest
import com.example.lazypizza.ui.theme.TextPrimary
import com.example.lazypizza.ui.theme.TextSeconday

@Composable
fun WideCartScreenContent(
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
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
            ) {
                LazyColumn(Modifier.weight(1f)) {
                    itemsIndexed(cartItems) { index, cartItem ->
                        MenuItemCard(
                            modifier = Modifier.padding(end = 15.dp),
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
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
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
                    GradientButton(
                        modifier = Modifier.padding(horizontal = 15.dp).padding(top = 30.dp)
                            .fillMaxWidth(),
                        text = "Proceed to Checkout ($${
                            "%.2f".format(
                                cartItems.sumOf { it.itemTotal.toDouble() })
                        })",
                        onCLick = proceedToCheckout
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun WideCartScreenPreview() {
    WideCartScreenContent(
        cartItems = mutableListOf<CartItem>() as SnapshotStateList<CartItem>,
        backToMenu = {},
        proceedToCheckout = {},
        quantityAdded = {},
        quantityRemoved = {},
        deleteCart = {},
        shuffledRecs = null,
        addToCart = {},
    )
}