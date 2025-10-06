package com.example.lazypizza.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lazypizza.R
import com.example.lazypizza.ui.theme.BG
import com.example.lazypizza.ui.theme.FontFamily
import com.example.lazypizza.ui.theme.Primary
import com.example.lazypizza.ui.theme.TextOnPrimary
import com.example.lazypizza.ui.theme.TextPrimary
import com.example.lazypizza.ui.theme.TextSeconday

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var searchedProduct by remember { mutableStateOf(TextFieldValue("")) }
    Column(
        modifier = modifier.padding(horizontal = 16.dp).fillMaxSize().background(color = BG),
    ) {
        Image(
            painter = painterResource(R.drawable.banner),
            contentDescription = "banner",
            modifier = Modifier.fillMaxWidth().height(150.dp).clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.FillWidth
        )
        TextField(
            value = searchedProduct,
            onValueChange = { newValue ->
                searchedProduct = newValue
            },
            modifier = Modifier.padding(top = 15.dp).fillMaxWidth().clip(RoundedCornerShape(28.dp)),
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
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
