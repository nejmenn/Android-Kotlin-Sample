package br.com.jaimenejaim.testedevjrandroidkotlin.data.remote.dto

import br.com.jaimenejaim.testedevjrandroidkotlin.domain.OrderDomain
import br.com.jaimenejaim.testedevjrandroidkotlin.util.BrazilianCurrency
import com.google.gson.annotations.SerializedName

data class OrderDto(
    val id: Long,
    @SerializedName("sale_code")
    val saleCode: Long,
    val description: String,
    @SerializedName("unit_price")
    val unitPrice: Double,
    @SerializedName("sale_quantity")
    val saleQuantity: Int
)

fun OrderDto.toDomain() = OrderDomain(
    id = this.id,
    saleCode = this.saleCode,
    description = this.description,
    unitPrice = BrazilianCurrency(this.unitPrice),
    saleQuantity = this.saleQuantity
)