package com.example.lazypizza.screens

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.lazypizza.R
import com.example.lazypizza.repository.LazyPizzaResponse
import com.example.lazypizza.repository.MenuItem
import com.example.lazypizza.repository.Pizza
import com.example.lazypizza.repository.menuCategories
import com.example.lazypizza.ui.theme.BG
import com.example.lazypizza.ui.theme.FontFamily
import com.example.lazypizza.ui.theme.Outline
import com.example.lazypizza.ui.theme.Primary
import com.example.lazypizza.ui.theme.SurfaceHigher
import com.example.lazypizza.ui.theme.SurfaceHighest
import com.example.lazypizza.ui.theme.TextOnPrimary
import com.example.lazypizza.ui.theme.TextPrimary
import com.example.lazypizza.ui.theme.TextSeconday
import com.example.lazypizza.viewmodel.HomeViewModel
import com.example.lazypizza.viewmodel.imageBaseUrl

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    LaunchedEffect(Unit) {
        if (viewModel.menuItems.value == null) {
            viewModel.fetchData()
        }
    }

    HomeScreenContent(
        modifier = modifier,
        menuItems = viewModel.menuItems.value,
    )
}

@Composable
fun HomeScreenContent(modifier: Modifier = Modifier, menuItems: LazyPizzaResponse?) {
    var searchedProduct by remember { mutableStateOf(TextFieldValue("")) }
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp).fillMaxSize().background(color = BG),
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
                            contentDescription = "",
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
                                .padding(top = 15.dp)
                                .border(
                                    color = Outline, shape = RoundedCornerShape(8.dp), width = 1.dp
                                )
                                .clickable {

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
        itemsIndexed(menuItems?.pizzas ?: listOf()) { index, pizza ->
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
                onPizzaSelected = {},
            )
        }
        itemsIndexed(menuItems?.drinks ?: listOf()) { index, drink ->
            if (index == 0) {
                Text(
                    text = "DRINKS",
                    fontSize = 12.sp,
                    fontFamily = FontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSeconday,
                    modifier = Modifier.padding(top = 15.dp)
                )
            }
            MenuItemCard(
                item = drink,
                categoryName = "drink",
                onPizzaSelected = {},
            )
        }
        itemsIndexed(menuItems?.sauces ?: listOf()) { index, sauce ->
            if (index == 0) {
                Text(
                    text = "SAUCES",
                    fontSize = 12.sp,
                    fontFamily = FontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSeconday,
                    modifier = Modifier.padding(top = 15.dp)
                )
            }
            MenuItemCard(
                item = sauce,
                categoryName = "sauce",
                onPizzaSelected = {},
            )
        }
        itemsIndexed(menuItems?.iceCreams ?: listOf()) { index, icecream ->
            if (index == 0) {
                Text(
                    text = "ICE CREAM",
                    fontSize = 12.sp,
                    fontFamily = FontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSeconday,
                    modifier = Modifier.padding(top = 15.dp)
                )
            }
            MenuItemCard(
                item = icecream,
                categoryName = "icecream",
                onPizzaSelected = {},
            )
        }
    }
}

@Composable
fun PizzaCard(
    item: Pizza?,
    onPizzaSelected: () -> Unit
) {
    var isImageLoading by rememberSaveable { mutableStateOf(false) }
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
            val imageUrl = imageBaseUrl + "pizza/${item?.name ?: ""}.png"
            Box(
                modifier = Modifier.size(120.dp).background(color = SurfaceHighest),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .build(),
                    contentDescription = "${item?.name ?: ""} image",
                    modifier = Modifier.fillMaxWidth(),
                    onLoading = {
                        isImageLoading = true
                    },
                    onSuccess = {
                        isImageLoading = false
                    },
                    onError = {
                        Log.e("PizzaCard", "Error loading image: ${it.result.throwable}")
                    }
                )
                if (isImageLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(40.dp),
                        color = Primary
                    )
                }
            }
            Column(modifier = Modifier.padding(15.dp).heightIn(min = 105.dp)) {
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
    onPizzaSelected: () -> Unit
) {
    var isImageLoading by rememberSaveable { mutableStateOf(false) }
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
            val imageUrl = imageBaseUrl + "$categoryName/${item?.name ?: ""}.png"
            Box(
                modifier = Modifier.size(120.dp).background(color = SurfaceHighest),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .build(),
                    contentDescription = "${item?.name ?: ""} image",
                    modifier = Modifier.fillMaxWidth(),
                    onLoading = {
                        isImageLoading = true
                    },
                    onSuccess = {
                        isImageLoading = false
                    },
                    onError = {
                        Log.e(
                            "${categoryName}Card",
                            "Error loading image: ${it.result.throwable} for url:$imageUrl"
                        )
                    }
                )
                if (isImageLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(40.dp),
                        color = Primary
                    )
                }
            }
            Column(
                modifier = Modifier.padding(15.dp).heightIn(min = 105.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item?.name ?: "",
                    fontSize = 16.sp,
                    fontFamily = FontFamily,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary,
                )
                Text(
                    text = item?.price?.let { "$$it" } ?: "",
                    fontSize = 24.sp,
                    fontFamily = FontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary,
                )
            }
        }
    }

}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(modifier = Modifier, menuItems = null)
}
