package br.com.jaimenejaim.testedevjrandroidkotlin.data.remote.dto

import br.com.jaimenejaim.testedevjrandroidkotlin.domain.ProductDomain
import br.com.jaimenejaim.testedevjrandroidkotlin.util.BrazilianCurrency
import com.google.gson.annotations.SerializedName

data class ProductDto(
    val id: Int,
    val description: String,
    @SerializedName("unit_price")
    val unitPrice: Double,
    val quantity: Int
)

fun ProductDto.toDomain() = ProductDomain(
    id = this.id,
    description = this.description,
    unitPrice = BrazilianCurrency(this.unitPrice),
    quantity = this.quantity
)