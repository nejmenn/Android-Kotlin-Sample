package br.com.jaimenejaim.testedevjrandroidkotlin.ui

import br.com.jaimenejaim.testedevjrandroidkotlin.R

sealed class BottomBarScreen(
    val route: String,
    val title: Int,
    val icon: Int
) {
    data object Product : BottomBarScreen(
        route = "products",
        title = R.string.menu_products_title,
        icon = R.drawable.ic_product
    )
    data object Order : BottomBarScreen(
        route = "orders",
        title = R.string.menu_orders_title,
        icon = R.drawable.ic_order
    )
}