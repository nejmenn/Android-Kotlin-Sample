package br.com.jaimenejaim.testedevjrandroidkotlin.domain

import br.com.jaimenejaim.testedevjrandroidkotlin.ui.model.Product
import br.com.jaimenejaim.testedevjrandroidkotlin.util.BrazilianCurrency

data class ProductDomain(
    val id: Int,
    val description: String,
    val unitPrice: BrazilianCurrency,
    val quantity: Int
)

fun ProductDomain.toModel() = Product(
    id = this.id,
    description = this.description,
    unitPrice = this.unitPrice.value,
    quantity = this.quantity
)