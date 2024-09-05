@file:Suppress("PreviewShouldNotBeCalledRecursively")

package br.com.jaimenejaim.testedevjrandroidkotlin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.jaimenejaim.testedevjrandroidkotlin.R
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.components.AppCircularProgressIndicator
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.components.SearchWidgetState
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.components.UiState
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.model.Order
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.model.Product
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.orders.mockOrders
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.products.mockProducts
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.theme.ActionBarSize
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.theme.Gray
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.theme.PrimaryColor

@Composable
fun HomeScreen(
    currentDestination: NavDestination? = null,
    navController: NavHostController,
    modifier: Modifier,
    ordersState: UiState<List<Order>>,
    productsState: UiState<List<Product>>,
    loadOrders: () -> Unit,
    loadProducts: () -> Unit,
    actionExportToCsv: () -> Unit,
    onCsvLoading: Boolean = false
) {

    val searchWidgetState = remember {
        mutableStateOf(SearchWidgetState.CLOSED)
    }

    var searchTextState by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier,
        topBar = {
            MainAppBar(
                searchWidgetState = searchWidgetState.value,
                searchTextState = searchTextState,
                onTextChange = {
                    searchTextState = it
                },
                onCloseClicked = {
                    searchWidgetState.value = SearchWidgetState.CLOSED
                },
                onSearchTriggered = {
                    searchWidgetState.value = SearchWidgetState.OPENED
                },
                onCsvExportClick = actionExportToCsv,
                onCsvLoading = onCsvLoading
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                BottomNavGraph(
                    navController = navController,
                    ordersState = ordersState,
                    productsState = productsState,
                    loadOrders = loadOrders,
                    loadProducts = loadProducts,
                    onListTextSearch = searchTextState
                )
            }
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                currentDestination = currentDestination
            )
        }
    )
}

@Composable
fun BottomBar(
    currentDestination: NavDestination? = null,
    navController: NavHostController) {

    val screens = arrayListOf(
        BottomBarScreen.Product,
        BottomBarScreen.Order
    )

    Row(
        modifier = Modifier
            .height(ActionBarSize)
            .background(Color.White)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    NavigationBarItem(
        label = {
            Text(
                text = stringResource(id = screen.title),
                softWrap = false,
                color = getColorFromItemsNavigationBar(screen.route, currentDestination?.route ?: "")
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = screen.route,
                tint = getColorFromItemsNavigationBar(screen.route, currentDestination?.route ?: "")
            )
        },
        selected = currentDestination?.route == screen.route,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.White
        )
    )
}


@Composable
fun MainAppBar(
    onCsvLoading: Boolean = false,
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchTriggered: () -> Unit,
    onCsvExportClick: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                onCsvLoading = onCsvLoading,
                onCsvExportClick = onCsvExportClick,
                onSearchClicked = onSearchTriggered
            )
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(onCsvLoading: Boolean,
                  onCsvExportClick: () -> Unit,
                  onSearchClicked: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                onSearchClicked()
            }) {
                Icon(
                    tint = Color.White,
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null
                )
            }
        },
        title = {

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PrimaryColor,
            titleContentColor = Color.White,
        ),
        actions = {
            if (onCsvLoading.not()) {
                IconButton(onClick = {
                    onCsvExportClick()
                }) {
                    Icon(
                        tint = Color.White,
                        painter = painterResource(id = R.drawable.ic_csv),
                        contentDescription = null
                    )
                }
            } else {
                IconButton(onClick = {}) {
                    AppCircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier
                            .size(25.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit
) {

    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        color = PrimaryColor
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (isFocused) {
                    keyboard?.show()
                }
            },
            interactionSource = interactionSource,
            colors = TextFieldDefaults.colors(
                focusedPlaceholderColor = Color.White,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = PrimaryColor,
                unfocusedContainerColor = PrimaryColor
            ),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    text = "Pesquise aqui...",
                    color = Color.White
                )
            },
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {

            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            )
        )
    }
}


@Composable
fun getColorFromItemsNavigationBar(route: String, currentRoute: String): Color {
    return if (route == currentRoute) PrimaryColor else Gray
}

@Preview(showBackground = true)
@Composable
fun HomeScreen_Preview() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    HomeScreen(
        modifier = Modifier,
        ordersState = UiState.Success(data = mockOrders()),
        productsState = UiState.Success(data = mockProducts()),
        loadOrders = {

        },
        loadProducts = {

        },
        actionExportToCsv = {

        },
        navController = navController,
        currentDestination = currentDestination
    )
}