package com.example.lazypizza.screens

import GradientButton
import MenuImage
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lazypizza.R
import com.example.lazypizza.repository.MenuItem
import com.example.lazypizza.repository.Pizza
import com.example.lazypizza.ui.theme.FontFamily
import com.example.lazypizza.ui.theme.Outline
import com.example.lazypizza.ui.theme.Primary
import com.example.lazypizza.ui.theme.Primary8
import com.example.lazypizza.ui.theme.SurfaceHigher
import com.example.lazypizza.ui.theme.SurfaceHighest
import com.example.lazypizza.ui.theme.TextPrimary
import com.example.lazypizza.ui.theme.TextSeconday
import com.example.lazypizza.viewmodel.HomeViewModel
import com.example.lazypizza.viewmodel.MenuStack
import com.example.lazypizza.viewmodel.Screen
import com.example.lazypizza.widescreens.WidePizzaScreenContent

@Composable
fun PizzaScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel, isScreenWide: Boolean) {
    Box(modifier.fillMaxSize()) {
        if (isScreenWide) {
            WidePizzaScreenContent(
                pizza = viewModel.selectedPizza.value,
                toppings = viewModel.menuItems.value?.toppings
            )
        } else {
            PizzaScreenContent(
                pizza = viewModel.selectedPizza.value,
                toppings = viewModel.menuItems.value?.toppings
            )
        }
    }
    BackHandler {
        viewModel.handleMenuStackNavigation(MenuStack.MenuScreen)
    }
}

@Composable
fun PizzaScreenContent(
    modifier: Modifier = Modifier,
    pizza: Pizza?,
    toppings: List<MenuItem?>? = null,
) {
    Box(modifier.fillMaxSize()) {
        var cartTotal by rememberSaveable { mutableFloatStateOf(pizza?.price?.toFloat() ?: 0f) }
        Column {
            MenuImage(
                modifier = Modifier.fillMaxWidth().height(240.dp).background(SurfaceHighest),
                categoryName = "pizza",
                itemName = pizza?.name
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(
                        color = SurfaceHigher,
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    )
                    .padding(horizontal = 15.dp)
            ) {
                Text(
                    text = pizza?.name ?: "",
                    fontSize = 24.sp,
                    fontFamily = FontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary,
                    modifier = Modifier.padding(top = 15.dp)
                )
                Text(
                    text = pizza?.ingredients?.joinToString(", ") ?: "",
                    fontSize = 14.sp,
                    fontFamily = FontFamily,
                    fontWeight = FontWeight.Normal,
                    color = TextSeconday,
                )
                Text(
                    text = "ADD EXTRA TOPPINGS",
                    fontSize = 12.sp,
                    fontFamily = FontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSeconday,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.fillMaxWidth().background(color = SurfaceHigher)
                    .padding(horizontal = 10.dp)
            ) {
                items(toppings ?: emptyList<MenuItem?>()) { topping ->
                    var quantity by rememberSaveable { mutableIntStateOf(0) }
                    var cardSelected by rememberSaveable { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 5.dp, vertical = 5.dp)
                            .shadow(4.dp, shape = RoundedCornerShape(12.dp))
                            .background(color = SurfaceHigher, RoundedCornerShape(12.dp))
                            .clickable {
                                if (quantity == 0) {
                                    quantity = 1
                                    cartTotal += (topping?.price?.toFloat() ?: 0f)
                                }
                                cardSelected = true
                            }.border(
                                width = 1.dp,
                                color = if (cardSelected) Primary else Outline,
                                shape = RoundedCornerShape(12.dp),
                            )
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 15.dp, horizontal = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            MenuImage(
                                modifier = Modifier.background(Primary8, CircleShape)
                                    .padding(5.dp)
                                    .size(56.dp),
                                categoryName = "toppings",
                                itemName = topping?.name
                            )
                            Text(
                                text = topping?.name ?: "",
                                fontSize = 14.sp,
                                fontFamily = FontFamily,
                                fontWeight = FontWeight.Normal,
                                color = TextSeconday,
                            )
                            Row(
                                modifier = Modifier.padding(top = 5.dp),
                                horizontalArrangement = Arrangement.spacedBy(5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .alpha(if (quantity > 0 && cardSelected) 1f else 0f)
                                        .clickable(enabled = quantity > 0 && cardSelected) {
                                            quantity--
                                            cardSelected = quantity != 0
                                            cartTotal -= (topping?.price?.toFloat() ?: 0f)
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
                                        painter = painterResource(R.drawable.ic_minus),
                                        contentDescription = "Minus Icon",
                                        tint = TextSeconday,
                                        modifier = Modifier
                                    )
                                }

                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = topping?.price?.let { "$$it" }
                                            ?: "",
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextPrimary,
                                        modifier = Modifier.alpha(if (quantity == 0) 1f else 0f)
                                    )
                                    Text(
                                        text = quantity.toString(),
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextPrimary,
                                        modifier = Modifier.alpha(if (quantity > 0) 1f else 0f)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .alpha(if (quantity > 0 && cardSelected) 1f else 0f)
                                        .clickable(
                                            enabled = quantity > 0 && cardSelected && quantity < 3
                                        ) {
                                            quantity++
                                            cartTotal += (topping?.price?.toFloat() ?: 0f)
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
                                        painter = painterResource(R.drawable.ic_plus),
                                        contentDescription = "Plus Icon",
                                        tint = if (quantity < 3) TextSeconday else Outline,
                                        modifier = Modifier
                                    )
                                }
                            }

                        }
                    }
                }
                item { Spacer(Modifier.height(100.dp)) }
            }
        }
        GradientButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(15.dp)
                .padding(bottom = 10.dp),
            text = "Add to Cart for $${cartTotal}",
            onCLick = {}
        )
    }
}


@Preview
@Composable
private fun PizzaScreenPreview() {
    PizzaScreenContent(
        pizza = null
    )
}