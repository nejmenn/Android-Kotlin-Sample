package br.com.jaimenejaim.testedevjrandroidkotlin.domain

import br.com.jaimenejaim.testedevjrandroidkotlin.ui.model.Order
import br.com.jaimenejaim.testedevjrandroidkotlin.util.BrazilianCurrency

data class OrderDomain(
    val id: Long,
    val saleCode: Long,
    val description: String,
    val unitPrice: BrazilianCurrency,
    val saleQuantity: Int
)

fun OrderDomain.toModel() = Order(
    id = this.id,
    saleCode = this.saleCode,
    description = this.description,
    unitPrice = this.unitPrice.value,
    saleQuantity = this.saleQuantity
)