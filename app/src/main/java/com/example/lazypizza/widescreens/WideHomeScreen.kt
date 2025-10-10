package com.example.lazypizza.widescreens

import MenuImage
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.lazypizza.R
import com.example.lazypizza.repository.LazyPizzaResponse
import com.example.lazypizza.repository.MenuItem
import com.example.lazypizza.repository.Pizza
import com.example.lazypizza.repository.menuCategories
import com.example.lazypizza.screens.MenuItemCard
import com.example.lazypizza.screens.PizzaCard
import com.example.lazypizza.ui.theme.BG
import com.example.lazypizza.ui.theme.FontFamily
import com.example.lazypizza.ui.theme.Outline
import com.example.lazypizza.ui.theme.Primary
import com.example.lazypizza.ui.theme.Primary8
import com.example.lazypizza.ui.theme.SurfaceHigher
import com.example.lazypizza.ui.theme.SurfaceHighest
import com.example.lazypizza.ui.theme.TextOnPrimary
import com.example.lazypizza.ui.theme.TextPrimary
import com.example.lazypizza.ui.theme.TextSeconday
import com.example.lazypizza.viewmodel.HomeViewModel
import com.example.lazypizza.viewmodel.Screen
import kotlinx.coroutines.launch

@Composable
fun WideHomeScreenContent(
    modifier: Modifier = Modifier,
    menuItems: LazyPizzaResponse?,
    gridState: LazyGridState,
    onPizzaSelected: (Int) -> Unit
) {
    var searchedProduct by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()
    var noProductFound by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier
            .padding(horizontal = 16.dp).fillMaxSize().background(color = BG)
    ) {
       /* Image(
            painter = painterResource(R.drawable.banner),
            contentDescription = "banner",
            modifier = Modifier.fillMaxWidth().height(150.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.FillWidth
        )

        TextField(
            value = searchedProduct,
            onValueChange = { newValue ->
                searchedProduct = newValue
                noProductFound = !((menuItems?.pizzas?.any {
                    it?.name?.contains(
                        searchedProduct.text
                    ) == true
                } == true) ||
                        (menuItems?.iceCreams?.any {
                            it?.name?.contains(
                                searchedProduct.text
                            ) == true
                        } == true) ||
                        (menuItems?.drinks?.any {
                            it?.name?.contains(
                                searchedProduct.text
                            ) == true
                        } == true) ||
                        (menuItems?.sauces?.any {
                            it?.name?.contains(
                                searchedProduct.text
                            ) == true
                        } == true))
            },
            modifier = Modifier.padding(top = 15.dp).fillMaxWidth()
                .clip(RoundedCornerShape(28.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = TextOnPrimary,
                unfocusedContainerColor = TextOnPrimary,
                unfocusedIndicatorColor = TextOnPrimary,
                focusedIndicatorColor = TextOnPrimary
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_search_refraction),
                    contentDescription = "Search",
                    tint = Primary,
                    modifier = Modifier.size(15.dp)
                )
            },
            placeholder = {
                Text(
                    text = "Search for delicious food...",
                    fontSize = 16.sp,
                    fontFamily = FontFamily,
                    fontWeight = FontWeight.Normal,
                    color = TextSeconday
                )
            }
        )*/


        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            menuCategories.forEach {
                Box(
                    modifier = Modifier
                        .padding(top = 15.dp, bottom = 5.dp)
                        .border(
                            color = Outline, shape = RoundedCornerShape(8.dp), width = 1.dp
                        )
                        .clickable {
                            coroutineScope.launch {
                                when (it) {
                                    "Pizza" -> gridState.animateScrollToItem(0)
                                    "Drinks" -> gridState.animateScrollToItem(
                                        menuItems?.pizzas?.size ?: 1
                                    )

                                    "Sauces" -> gridState.animateScrollToItem(
                                        (menuItems?.pizzas?.size
                                            ?: 1) + (menuItems?.drinks?.size ?: 1)
                                    )

                                    "Ice Cream" -> gridState.animateScrollToItem(
                                        (menuItems?.pizzas?.size
                                            ?: 1) + (menuItems?.drinks?.size
                                            ?: 1) + (menuItems?.sauces?.size ?: 1)
                                    )
                                }
                            }
                        }
                ) {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        fontFamily = FontFamily,
                        fontWeight = FontWeight.Medium,
                        color = TextPrimary,
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 6.dp)
                    )
                }
            }
        }

        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier,
        ) {
            itemsIndexed(
                menuItems?.pizzas?.filter { it?.name?.contains(searchedProduct.text, true) == true }
                    ?: listOf()) { index, pizza ->
                Column {
                    if(index ==0 || index == 1) {
                        Text(
                            text = "PIZZA",
                            fontSize = 12.sp,
                            fontFamily = FontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = TextSeconday,
                            modifier = Modifier.alpha(if (index == 0) 1f else 0f).fillMaxWidth()
                        )
                    }
                    PizzaCard(
                        item = pizza,
                        onPizzaSelected = { onPizzaSelected(index) },
                    )
                }
            }
            itemsIndexed(
                menuItems?.drinks?.filter { it?.name?.contains(searchedProduct.text, true) == true }
                    ?: listOf()) { index, drink ->
                Column {
                    if(index ==0 || index == 1) {
                        Text(
                            text = "DRINKS",
                            fontSize = 12.sp,
                            fontFamily = FontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = TextSeconday,
                            modifier = Modifier.alpha(if (index == 0) 1f else 0f).fillMaxWidth()
                        )
                    }

                    MenuItemCard(
                        item = drink,
                        categoryName = "drink",
                        quantityAdded = {},
                        quantityRemoved = {},
                        deleteCart = {},
                    )
                }
            }
            itemsIndexed(
                menuItems?.sauces?.filter { it?.name?.contains(searchedProduct.text, true) == true }
                    ?: listOf()) { index, sauce ->
                Column {
                    if(index ==0 || index == 1) {
                        Text(
                            text = "SAUCES",
                            fontSize = 12.sp,
                            fontFamily = FontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = TextSeconday,
                            modifier = Modifier.alpha(if (index == 0) 1f else 0f).fillMaxWidth()
                        )
                    }

                    MenuItemCard(
                        item = sauce,
                        categoryName = "sauce",
                        quantityAdded = {},
                        quantityRemoved = {},
                        deleteCart = {},
                    )
                }
            }
            itemsIndexed(
                menuItems?.iceCreams?.filter {
                    it?.name?.contains(
                        searchedProduct.text, true
                    ) == true
                }
                    ?: listOf()) { index, icecream ->
                Column {
                    if(index ==0 || index == 1) {
                        Text(
                            text = "ICE CREAM",
                            fontSize = 12.sp,
                            fontFamily = FontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = TextSeconday,
                            modifier = Modifier.alpha(if (index == 0) 1f else 0f).fillMaxWidth()
                        )
                    }

                    MenuItemCard(
                        item = icecream,
                        categoryName = "ice cream",
                        quantityAdded = {},
                        quantityRemoved = {},
                        deleteCart = {},
                    )
                }
            }
            item {
                if (noProductFound) {
                    Text(
                        text = "No results found for your query.",
                        fontSize = 16.sp,
                        fontFamily = FontFamily,
                        fontWeight = FontWeight.Normal,
                        color = TextSeconday,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun WideHomeScreenPreview() {
    WideHomeScreenContent(
        modifier = Modifier,
        menuItems = null,
        gridState = rememberLazyGridState(),
        onPizzaSelected = {})
}
