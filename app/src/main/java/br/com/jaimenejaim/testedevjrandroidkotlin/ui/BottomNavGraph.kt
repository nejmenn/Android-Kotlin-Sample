package br.com.jaimenejaim.testedevjrandroidkotlin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.components.UiState
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.model.Order
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.model.Product
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.orders.OrderScreen
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.products.ProductScreen
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.theme.SilverBackground

@Composable
fun BottomNavGraph(
    onListTextSearch: String,
    navController: NavHostController,
    ordersState: UiState<List<Order>>,
    loadOrders: () -> Unit,
    productsState: UiState<List<Product>>,
    loadProducts: () -> Unit
) {

    val modifier = Modifier
        .fillMaxSize()
        .background(SilverBackground)

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Product.route,
    ) {
        composable(route = BottomBarScreen.Product.route) {
            ProductScreen(
                onListTextSearch = onListTextSearch,
                modifier = modifier,
                productsState = productsState,
                loadProducts = loadProducts
            )
        }
        composable(route = BottomBarScreen.Order.route) {
            OrderScreen(
                onListTextSearch = onListTextSearch,
                modifier = modifier,
                ordersState = ordersState,
                loadOrders = loadOrders
            )
        }
    }
}