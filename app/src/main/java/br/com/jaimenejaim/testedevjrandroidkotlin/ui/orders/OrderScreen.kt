package br.com.jaimenejaim.testedevjrandroidkotlin.ui.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.components.AppCircularProgressIndicator
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.components.ErrorComponent
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.components.UiState
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.model.Order
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.theme.PrimaryColor
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.theme.SilverBackground
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.theme.SpaceBetweenRows
import br.com.jaimenejaim.testedevjrandroidkotlin.util.BrazilianCurrency

@Composable
fun OrderScreen(modifier: Modifier,
                onListTextSearch: String,
                ordersState: UiState<List<Order>>,
                loadOrders: () -> Unit) {

    LaunchedEffect(Unit) {
        loadOrders()
    }

    when (ordersState) {
        is UiState.Success -> {
            Column(modifier = modifier) {

                val searchedItems = (ordersState.data ?: emptyList()).filter {
                    (it.description ?: "").contains(onListTextSearch, ignoreCase = true)
                }

                SuccessLoad(
                    orders = searchedItems,
                    modifier = Modifier
                )
            }
        }
        is UiState.Loading -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppCircularProgressIndicator(
                    color = PrimaryColor,
                    modifier = Modifier
                )
            }
        }
        is UiState.Error -> {
            ErrorComponent(modifier = Modifier
                .fillMaxSize()
                .background(SilverBackground))
        }
        else -> {

        }
    }
}

@Composable
private fun SuccessLoad(
    orders: List<Order>,
    modifier: Modifier
) {
    val ordersMap = orders.groupBy { it.saleCode }

    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    end = 10.dp
                )
        ) {

            itemsIndexed(ordersMap.values.toMutableStateList()) { index, orders ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "No: ${orders.groupBy { it.saleCode }.values.first().first().saleCode}",
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                for (order in orders) {
                    OrderItem(
                        modifier = Modifier
                            .background(SilverBackground),
                        order = order
                    ) {

                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 20.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Total: ${BrazilianCurrency(orders.sumOf { it.unitPrice * it.saleQuantity }).format()}",
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}


@Composable
fun OrderItem(modifier: Modifier,
              order: Order,
              onClick: (Order) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = modifier
            .padding(
                vertical = SpaceBetweenRows
            ),
        onClick = {
            onClick.invoke(order)
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(
                    all = 30.dp
                )
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .weight(1f)
            ) {
                Text(
                    color = Color.Black,
                    text = order.description,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    color = Color.Gray,
                    text = "Quantidade: " + order.saleQuantity.toString(),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 10.dp)
                 )

            }
            Text(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp),
                color = Color.Black,
                text = BrazilianCurrency(order.unitPrice).format(),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderItem_Preview() {
    OrderItem(
        modifier = Modifier
            .background(SilverBackground),
        order = mockOrders().first()
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun OrderScreenSuccess_Preview() {
    OrderScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(SilverBackground),
        ordersState = UiState.Success(data = mockOrders()),
        loadOrders = {

        },
        onListTextSearch = ""
    )
}

@Preview(showBackground = true)
@Composable
fun OrderScreenLoading_Preview() {
    OrderScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(SilverBackground),
        ordersState = UiState.Loading(),
        loadOrders = {

        },
        onListTextSearch = ""
    )
}

@Preview(showBackground = true)
@Composable
fun OrderScreenError_Preview() {
    OrderScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(SilverBackground),
        ordersState = UiState.Error(throwable = Throwable("Error")),
        loadOrders = {

        },
        onListTextSearch = ""
    )
}

fun mockOrders() = listOf(
    Order(
        id = 1,
        saleCode = 202409051147,
        description = "Martelo",
        unitPrice = BrazilianCurrency(100.00).value,
        saleQuantity = 100
    ),
    Order(
        id = 2,
        saleCode = 202409051147,
        description = "Chave inglesa",
        unitPrice = BrazilianCurrency(55.00).value,
        saleQuantity = 15
    ),
    Order(
        id = 3,
        saleCode = 202409051145,
        description = "Chave de fenda",
        unitPrice = BrazilianCurrency(75.00).value,
        saleQuantity = 55
    ),
    Order(
        id = 2,
        saleCode = 202409051154,
        description = "Chave inglesa",
        unitPrice = BrazilianCurrency(55.00).value,
        saleQuantity = 5
    ),
    Order(
        id = 3,
        saleCode = 202409051154,
        description = "Chave de fenda",
        unitPrice = BrazilianCurrency(75.00).value,
        saleQuantity = 30
    )
)