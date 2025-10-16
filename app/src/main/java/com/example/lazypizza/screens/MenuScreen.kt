package com.example.lazypizza.screens

import MenuImage
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import bottomBarHeight
import com.example.lazypizza.R
import com.example.lazypizza.repository.LazyPizzaResponse
import com.example.lazypizza.repository.MenuItem
import com.example.lazypizza.repository.Pizza
import com.example.lazypizza.repository.menuCategories
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
import com.example.lazypizza.viewmodel.MenuStack
import com.example.lazypizza.viewmodel.Screen
import com.example.lazypizza.widescreens.WideMenuScreenContent
import kotlinx.coroutines.launch

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    isScreenWide: Boolean,
    viewModel: HomeViewModel,
    scrollState: LazyListState,
    gridState: LazyGridState,
) {
    LaunchedEffect(Unit) {
        if (viewModel.menuItems.value == null) {
            viewModel.fetchData()
        }
    }

    if (isScreenWide) {
        WideMenuScreenContent(
            modifier = modifier,
            menuItems = viewModel.menuItems.value,
            gridState = gridState,
            onPizzaSelected = {
                viewModel.selectedPizza.value = viewModel.menuItems.value?.pizzas?.get(it)
                viewModel.handleMenuStackNavigation(MenuStack.PizzaScreen)
            }
        )
    } else {
        MenuScreenContent(
            modifier = modifier,
            menuItems = viewModel.menuItems.value,
            scrollState = scrollState,
            onPizzaSelected = {
                viewModel.selectedPizza.value = viewModel.menuItems.value?.pizzas?.get(it)
                viewModel.handleMenuStackNavigation(MenuStack.PizzaScreen)
            }
        )
    }
}

@Composable
fun MenuScreenContent(
    modifier: Modifier = Modifier,
    menuItems: LazyPizzaResponse?,
    scrollState: LazyListState,
    onPizzaSelected: (Int) -> Unit
) {
    var searchedProduct by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()
    var noProductFound by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        state = scrollState,
        modifier = modifier
            .padding(horizontal = 16.dp).fillMaxSize().background(color = BG),
    ) {
        stickyHeader {
            Column(Modifier.background(color = BG)) {
                Image(
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
                                searchedProduct.text, true
                            ) == true
                        } == true) ||
                                (menuItems?.iceCreams?.any {
                                    it?.name?.contains(
                                        searchedProduct.text, true
                                    ) == true
                                } == true) ||
                                (menuItems?.drinks?.any {
                                    it?.name?.contains(
                                        searchedProduct.text, true
                                    ) == true
                                } == true) ||
                                (menuItems?.sauces?.any {
                                    it?.name?.contains(
                                        searchedProduct.text, true
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
                )


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
                                            "Pizza" -> scrollState.animateScrollToItem(0)
                                            "Drinks" -> scrollState.animateScrollToItem(
                                                (menuItems?.pizzas?.size ?: 1) - 1
                                            )

                                            "Sauces" -> scrollState.animateScrollToItem(
                                                (menuItems?.pizzas?.size
                                                    ?: 1) + (menuItems?.drinks?.size ?: 1) - 1
                                            )

                                            "Ice Cream" -> scrollState.animateScrollToItem(
                                                (menuItems?.pizzas?.size
                                                    ?: 1) + (menuItems?.drinks?.size
                                                    ?: 1) + (menuItems?.sauces?.size ?: 1) - 1
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
            }
        }
        itemsIndexed(
            menuItems?.pizzas?.filter { it?.name?.contains(searchedProduct.text, true) == true }
                ?: listOf()) { index, pizza ->
            if (index == 0) {
                Text(
                    text = "PIZZA",
                    fontSize = 12.sp,
                    fontFamily = FontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSeconday,
                    modifier = Modifier.padding(top = 15.dp)
                )
            }
            PizzaCard(
                item = pizza,
                onPizzaSelected = { onPizzaSelected(index) },
            )
        }
        itemsIndexed(
            menuItems?.drinks?.filter { it?.name?.contains(searchedProduct.text, true) == true }
                ?: listOf()) { index, drink ->
            if (index == 0) {
                Text(
                    text = "DRINKS",
                    fontSize = 12.sp,
                    fontFamily = FontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSeconday,
                    modifier = Modifier.padding(top = 20.dp)
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
        itemsIndexed(
            menuItems?.sauces?.filter { it?.name?.contains(searchedProduct.text, true) == true }
                ?: listOf()) { index, sauce ->
            if (index == 0) {
                Text(
                    text = "SAUCES",
                    fontSize = 12.sp,
                    fontFamily = FontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSeconday,
                    modifier = Modifier.padding(top = 20.dp)
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
        itemsIndexed(
            menuItems?.iceCreams?.filter { it?.name?.contains(searchedProduct.text, true) == true }
                ?: listOf()) { index, icecream ->
            if (index == 0) {
                Text(
                    text = "ICE CREAM",
                    fontSize = 12.sp,
                    fontFamily = FontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSeconday,
                    modifier = Modifier.padding(top = 20.dp)
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
        item {
            Spacer(Modifier.height(bottomBarHeight + 70.dp))
        }
    }
}

@Composable
fun PizzaCard(
    item: Pizza?,
    onPizzaSelected: () -> Unit
) {
    Card(
        modifier = Modifier.padding(top = 10.dp).fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceHigher
        ),
        onClick = onPizzaSelected
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MenuImage(
                modifier = Modifier.size(120.dp).background(SurfaceHighest),
                categoryName = "pizza",
                itemName = item?.name
            )
            Column(modifier = Modifier.padding(15.dp).heightIn(min = 90.dp)) {
                Text(
                    text = item?.name ?: "",
                    fontSize = 16.sp,
                    fontFamily = FontFamily,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary,
                )
                Text(
                    text = item?.ingredients?.joinToString(", ") ?: "",
                    fontSize = 14.sp,
                    lineHeight = 1.em,
                    fontFamily = FontFamily,
                    fontWeight = FontWeight.Normal,
                    color = TextSeconday,
                )
                Text(
                    text = item?.price?.let { "$$it" } ?: "",
                    fontSize = 24.sp,
                    fontFamily = FontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary,
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        }
    }
}

@Composable
fun MenuItemCard(
    item: MenuItem?,
    categoryName: String,
    quantityAdded: () -> Unit,
    quantityRemoved: () -> Unit,
    deleteCart: () -> Unit
) {
    var quantity by rememberSaveable { mutableIntStateOf(0) }
    var itemTotal by rememberSaveable { mutableFloatStateOf(0f) }
    Card(
        modifier = Modifier.padding(top = 10.dp).fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceHigher
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MenuImage(
                modifier = Modifier.size(120.dp).background(SurfaceHighest),
                categoryName = categoryName,
                itemName = item?.name
            )
            Column(
                modifier = Modifier.padding(15.dp).heightIn(min = 90.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item?.name ?: "",
                        fontSize = 16.sp,
                        fontFamily = FontFamily,
                        fontWeight = FontWeight.Medium,
                        color = TextPrimary,
                    )
                    Box(
                        modifier = Modifier
                            .alpha(if (quantity > 0) 1f else 0f)
                            .clickable(enabled = quantity > 0) {
                                quantity = 0
                                itemTotal = 0f
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
                            painter = painterResource(R.drawable.ic_trash),
                            contentDescription = "Trash Icon",
                            tint = Primary,
                            modifier = Modifier
                                .size(14.dp)
                        )
                    }

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (quantity == 0) {
                        Text(
                            text = item?.price?.let { "$$it" } ?: "",
                            fontSize = 24.sp,
                            fontFamily = FontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary,
                        )
                    } else {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .alpha(if (quantity > 0) 1f else 0f)
                                    .clickable(enabled = quantity > 0) {
                                        quantity--
                                        itemTotal -= (item?.price?.toFloat() ?: 0f)
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
                            Text(
                                text = quantity.toString(),
                                fontSize = 24.sp,
                                fontFamily = FontFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                            Box(
                                modifier = Modifier
                                    .alpha(if (quantity > 0) 1f else 0f)
                                    .clickable(enabled = quantity > 0) {
                                        quantity++
                                        itemTotal += (item?.price?.toFloat() ?: 0f)
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
                                    tint = TextSeconday,
                                    modifier = Modifier
                                )
                            }
                        }
                    }
                    Box(contentAlignment = Alignment.BottomEnd) {
                        OutlinedButton(
                            enabled = quantity == 0,
                            border = BorderStroke(1.dp, Primary8),
                            onClick = {
                                if (quantity == 0) {
                                    quantity = 1
                                }
                                itemTotal = item?.price?.toFloat() ?: 0f
                            },
                            modifier = Modifier.alpha(
                                if (quantity == 0) 1f else 0f
                            )
                        ) {
                            Text(
                                text = "Add to Cart",
                                fontSize = 15.sp,
                                fontFamily = FontFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = Primary,
                            )
                        }
                        Column(
                            modifier = Modifier.alpha(if (quantity > 0) 1f else 0f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "$%.2f".format(itemTotal),
                                fontSize = 24.sp,
                                fontFamily = FontFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary,
                            )
                            Text(
                                text = "$quantity x $${item?.price ?: 0f}",
                                fontSize = 12.sp,
                                fontFamily = FontFamily,
                                fontWeight = FontWeight.Normal,
                                color = TextSeconday,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MenuScreenPreview() {
    MenuScreenContent(
        modifier = Modifier,
        menuItems = null,
        scrollState = rememberLazyListState(),
        onPizzaSelected = {})
}
