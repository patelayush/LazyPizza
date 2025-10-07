package com.example.lazypizza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lazypizza.screens.HomeScreen
import com.example.lazypizza.ui.theme.FontFamily
import com.example.lazypizza.ui.theme.LazyPizzaTheme
import com.example.lazypizza.ui.theme.Primary
import com.example.lazypizza.ui.theme.TextPrimary
import com.example.lazypizza.ui.theme.TextSeconday
import com.example.lazypizza.viewmodel.HomeViewModel
import com.example.lazypizza.viewmodel.Screen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        var keepSplashScreen = true
        super.onCreate(savedInstanceState)
        splashscreen.setKeepOnScreenCondition { keepSplashScreen }
        lifecycleScope.launch {
            keepSplashScreen = false
        }
        enableEdgeToEdge()
        setContent {
            LazyPizzaTheme {
                val homeViewModel = viewModel<HomeViewModel>()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.logo_bold),
                                        contentDescription = "",
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = "Lazy Pizza",
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                }
                            },
                            actions = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                                    modifier = Modifier.padding(end = 16.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Phone,
                                        contentDescription = "",
                                        tint = TextSeconday,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = "+1 (555) 321-7890",
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily,
                                        fontWeight = FontWeight.Normal,
                                        color = TextPrimary
                                    )
                                }

                            }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        Modifier.fillMaxSize().padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        when (homeViewModel.currentScreen.value) {
                            Screen.HomeScreen -> HomeScreen(
                                modifier = Modifier,
                                viewModel = homeViewModel
                            )
                        }

                        if (homeViewModel.isLoading.value) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(50.dp),
                                color = Primary
                            )
                        }
                    }
                }
            }
        }
    }
}