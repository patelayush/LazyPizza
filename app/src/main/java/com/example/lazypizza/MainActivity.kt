package com.example.lazypizza

import BottomBarIcon
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lazypizza.screens.CartScreen
import com.example.lazypizza.screens.MenuScreen
import com.example.lazypizza.screens.OrderHistoryScreen
import com.example.lazypizza.screens.PizzaScreen
import com.example.lazypizza.ui.theme.FontFamily
import com.example.lazypizza.ui.theme.LazyPizzaTheme
import com.example.lazypizza.ui.theme.Primary
import com.example.lazypizza.ui.theme.SurfaceHigher
import com.example.lazypizza.ui.theme.SurfaceHighest
import com.example.lazypizza.ui.theme.TextPrimary
import com.example.lazypizza.ui.theme.TextSecondary8
import com.example.lazypizza.ui.theme.TextSeconday
import com.example.lazypizza.viewmodel.HomeViewModel
import com.example.lazypizza.viewmodel.MenuStack
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
                val context = LocalContext.current
                val isScreenWide = LocalConfiguration.current.screenWidthDp > 840
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                when (homeViewModel.currentTabSelected.value) {
                                    Screen.MenuScreen -> {
                                        if (homeViewModel.currentMenuStackScreen.value == MenuStack.MenuScreen) {
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
                                        }
                                    }

                                    Screen.CartScreen -> {
                                        Text(
                                            text = "Cart",
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily,
                                            fontWeight = FontWeight.Medium,
                                            color = TextPrimary,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }

                                    Screen.OrderHistoryScreen -> {
                                        Text(
                                            text = "Order History",
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily,
                                            fontWeight = FontWeight.Medium,
                                            color = TextPrimary,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            },
                            actions = {
                                if (homeViewModel.currentTabSelected.value == Screen.MenuScreen
                                    && homeViewModel.currentMenuStackScreen.value == MenuStack.MenuScreen
                                ) {
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
                                            color = TextPrimary,
                                            modifier = Modifier.clickable {
                                                val phoneNumber = "tel:5553217890"
                                                val intent = Intent(
                                                    Intent.ACTION_DIAL,
                                                    phoneNumber.toUri()
                                                )
                                                context.startActivity(intent)
                                            }
                                        )
                                    }
                                }
                            },
                            navigationIcon = {
                                if (homeViewModel.currentMenuStackScreen.value == MenuStack.PizzaScreen) {
                                    Box(
                                        modifier = Modifier.padding(start = 15.dp).background(
                                            color = TextSecondary8, shape = CircleShape
                                        ).clickable {
                                            homeViewModel.handleMenuStackNavigation(
                                                MenuStack.MenuScreen
                                            )
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "",
                                            tint = TextSeconday,
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = SurfaceHighest
                            )

                        )
                    },
                    bottomBar = {
                        if (homeViewModel.currentMenuStackScreen.value != MenuStack.PizzaScreen) {
                            BottomAppBar(
                                containerColor = SurfaceHigher,
                                tonalElevation = 8.dp
                            ) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    Screen.entries.forEach { screen ->
                                        BottomBarIcon(
                                            modifier = Modifier.weight(1f),
                                            tabName = screen,
                                            currentTabSelected = homeViewModel.currentTabSelected.value,
                                            tabIcon = screen.icon,
                                            onTabClick = { selectedTab ->
                                                if (screen == Screen.MenuScreen) {
                                                    homeViewModel.handleMenuStackNavigation(
                                                        MenuStack.MenuScreen
                                                    )
                                                }
                                                homeViewModel.switchTab(selectedTab)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    },
                ) { innerPadding ->
                    val scrollState = rememberLazyListState()
                    val gridState = rememberLazyGridState()
                    Box(
                        Modifier.fillMaxSize().padding(top = innerPadding.calculateTopPadding()),
                        contentAlignment = Alignment.Center
                    ) {
                        when (homeViewModel.currentTabSelected.value) {
                            Screen.MenuScreen ->
                                when (homeViewModel.currentMenuStackScreen.value) {
                                    MenuStack.MenuScreen -> MenuScreen(
                                        modifier = Modifier,
                                        isScreenWide = isScreenWide,
                                        viewModel = homeViewModel,
                                        scrollState = scrollState,
                                        gridState = gridState,
                                    )

                                    MenuStack.PizzaScreen -> PizzaScreen(
                                        modifier = Modifier,
                                        viewModel = homeViewModel,
                                        isScreenWide = isScreenWide,
                                    )
                                }

                            Screen.CartScreen -> CartScreen(
                                modifier = Modifier,
                                viewModel = homeViewModel,
                                isScreenWide = isScreenWide,
                            )

                            Screen.OrderHistoryScreen -> OrderHistoryScreen(
                                modifier = Modifier,
                                viewModel = homeViewModel,
                                isScreenWide = isScreenWide,
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