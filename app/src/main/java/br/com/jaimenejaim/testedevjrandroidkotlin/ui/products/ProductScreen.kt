package br.com.jaimenejaim.testedevjrandroidkotlin.ui.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.model.Product
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.theme.PrimaryColor
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.theme.SilverBackground
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.theme.SpaceBetweenRows
import br.com.jaimenejaim.testedevjrandroidkotlin.util.BrazilianCurrency

@Composable
fun ProductScreen(
    onListTextSearch: String,
    modifier: Modifier,
    productsState: UiState<List<Product>>,
    loadProducts: () -> Unit
) {

    LaunchedEffect(Unit) {
        loadProducts.invoke()
    }

    when (productsState) {
        is UiState.Success -> {
            Column(modifier = modifier) {

                val searchedItems = (productsState.data ?: emptyList()).filter {
                    (it.description ?: "").contains(onListTextSearch, ignoreCase = true)
                }

                SuccessLoading(
                    products = searchedItems,
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
private fun SuccessLoading(
    products: List<Product>,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(
                start = 10.dp,
                end = 10.dp
            )
    ) {
        items(products) {
            ProductItem(
                modifier = Modifier
                    .background(SilverBackground),
                product = it
            ) {

            }
        }
    }
}

@Composable
fun ProductItem(modifier: Modifier,
                product: Product,
                onClick: (Product) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = modifier
            .padding(
                vertical = SpaceBetweenRows
            ),
        onClick = {
            onClick.invoke(product)
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
                    text = product.description,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    color = Color.Gray,
                    text = "Estoque: " + product.quantity.toString(),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 10.dp)
                )

            }
            Text(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp),
                color = Color.Black,
                text = BrazilianCurrency(product.unitPrice).format(),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductItem_Preview() {
    ProductItem(
        modifier = Modifier
            .background(SilverBackground),
        product = mockProducts().first()
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun ProductScreenSuccess_Preview() {
    ProductScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(SilverBackground),
        productsState = UiState.Success(data = mockProducts()),
        loadProducts = {

        },
        onListTextSearch = ""
    )
}

@Preview(showBackground = true)
@Composable
fun ProductScreenLoading_Preview() {
    ProductScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(SilverBackground),
        productsState = UiState.Loading(),
        loadProducts = {

        },
        onListTextSearch = ""
    )
}

@Preview(showBackground = true)
@Composable
fun ProductScreenError_Preview() {
    ProductScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(SilverBackground),
        productsState = UiState.Error(throwable = Throwable("Error")),
        loadProducts = {

        },
        onListTextSearch = ""
    )
}

fun mockProducts() = listOf(
    Product(
        id = 1,
        description = "Martelo",
        unitPrice = BrazilianCurrency(100.59).value,
        quantity = 100
    ),
    Product(
        id = 2,
        description = "Chave inglesa",
        unitPrice = BrazilianCurrency(55.99).value,
        quantity = 15
    ),
    Product(
        id = 3,
        description = "Chave de fenda",
        unitPrice = BrazilianCurrency(75.33).value,
        quantity = 55
    ),
)