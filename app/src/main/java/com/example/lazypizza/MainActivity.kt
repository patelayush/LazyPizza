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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lazypizza.screens.CartScreen
import com.example.lazypizza.screens.MenuScreen
import com.example.lazypizza.screens.OrderHistoryScreen
import com.example.lazypizza.screens.PizzaScreen
import com.example.lazypizza.ui.theme.BG
import com.example.lazypizza.ui.theme.FontFamily
import com.example.lazypizza.ui.theme.LazyPizzaTheme
import com.example.lazypizza.ui.theme.Primary
import com.example.lazypizza.ui.theme.Primary8
import com.example.lazypizza.ui.theme.SurfaceHigher
import com.example.lazypizza.ui.theme.TextOnPrimary
import com.example.lazypizza.ui.theme.TextPrimary
import com.example.lazypizza.ui.theme.TextSecondary8
import com.example.lazypizza.ui.theme.TextSeconday
import com.example.lazypizza.viewmodel.HomeViewModel
import com.example.lazypizza.viewmodel.MenuStack
import com.example.lazypizza.viewmodel.Tab
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
                Row {
                    if(isScreenWide) {
                        NavigationRail(
                            modifier = Modifier.fillMaxHeight(),
                            containerColor = SurfaceHigher,
                            contentColor = Primary8
                        ) {
                            Spacer(Modifier.weight(1f))
                            Tab.entries.forEach { tab ->
                                NavigationRailItem(
                                    colors = NavigationRailItemDefaults.colors(
                                        indicatorColor = Primary8
                                    ),
                                    selected = tab == homeViewModel.currentTabSelected.value,
                                    alwaysShowLabel = true,
                                    icon = {
                                        Box {
                                            tab.icon?.let {
                                                Box(
                                                    modifier = Modifier.background(
                                                        color = if (homeViewModel.currentTabSelected.value == tab) Primary8 else SurfaceHigher,
                                                        shape = CircleShape
                                                    ).padding(10.dp)
                                                ) {
                                                    Image(
                                                        painter = painterResource(it),
                                                        contentDescription = "Menu",
                                                        colorFilter = ColorFilter.tint(
                                                            if (homeViewModel.currentTabSelected.value == tab) Primary else TextSeconday
                                                        ),
                                                        modifier = Modifier.size(22.dp)
                                                    )
                                                }
                                            }
                                            if (tab == Tab.CartScreen) {
                                                homeViewModel.cartItems.takeIf { it.isNotEmpty() }
                                                    ?.let {
                                                        Box(
                                                            modifier = Modifier
                                                                .align(Alignment.TopEnd)
                                                                .background(
                                                                    color = Primary,
                                                                    shape = CircleShape
                                                                ).padding(
                                                                    horizontal = 6.dp,
                                                                    vertical = 2.dp
                                                                )
                                                        ) {
                                                            Text(
                                                                text = homeViewModel.cartItems.size.toString(),
                                                                fontSize = 11.sp,
                                                                fontFamily = FontFamily,
                                                                lineHeight = 1.em,
                                                                fontWeight = FontWeight.Medium,
                                                                color = TextOnPrimary
                                                            )
                                                        }
                                                    }
                                            }
                                        }
                                    },
                                    label = {
                                        Text(
                                            text = tab.title.toString(),
                                            fontSize = 11.sp,
                                            fontFamily = FontFamily,
                                            lineHeight = 1.em,
                                            fontWeight = FontWeight.Medium,
                                            color = if (tab == homeViewModel.currentTabSelected.value) TextPrimary else TextSeconday
                                        )
                                    },
                                    onClick = {
                                        if (tab == Tab.MenuScreen) {
                                            homeViewModel.handleMenuStackNavigation(
                                                MenuStack.MenuScreen
                                            )
                                        }
                                        homeViewModel.switchTab(tab)
                                    },
                                )
                            }
                            Spacer(Modifier.weight(1f))
                        }
                    }
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                title = {
                                    when (homeViewModel.currentTabSelected.value) {
                                        Tab.MenuScreen -> {
                                            if (homeViewModel.currentMenuStackScreen.value == MenuStack.MenuScreen) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(
                                                        6.dp
                                                    )
                                                ) {
                                                    Image(
                                                        painter = painterResource(
                                                            R.drawable.logo_bold
                                                        ),
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

                                        Tab.CartScreen -> {
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

                                        Tab.OrderHistoryScreen -> {
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
                                    if (homeViewModel.currentTabSelected.value == Tab.MenuScreen
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
                                    containerColor = BG
                                )

                            )
                        },
                        bottomBar = {
                            if (homeViewModel.currentMenuStackScreen.value != MenuStack.PizzaScreen && !isScreenWide) {
                                BottomAppBar(
                                    containerColor = SurfaceHigher,
                                    tonalElevation = 8.dp
                                ) {
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceAround
                                    ) {
                                        Tab.entries.forEach { tab ->
                                            BottomBarIcon(
                                                modifier = Modifier.weight(1f),
                                                tabName = tab,
                                                currentTabSelected = homeViewModel.currentTabSelected.value,
                                                subLabel = if (tab == Tab.CartScreen) homeViewModel.cartItems.size.takeIf { it > 0 } else null,
                                                tabIcon = tab.icon,
                                                onTabClick = { selectedTab ->
                                                    if (tab == Tab.MenuScreen) {
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
                            Modifier
                                .fillMaxSize()
                                .padding(top = innerPadding.calculateTopPadding()),
                            contentAlignment = Alignment.Center
                        ) {
                            when (homeViewModel.currentTabSelected.value) {
                                Tab.MenuScreen ->
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

                                Tab.CartScreen -> {
                                    CartScreen(
                                        modifier = Modifier,
                                        viewModel = homeViewModel,
                                        isScreenWide = isScreenWide,
                                    )
                                }

                                Tab.OrderHistoryScreen -> OrderHistoryScreen(
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
}