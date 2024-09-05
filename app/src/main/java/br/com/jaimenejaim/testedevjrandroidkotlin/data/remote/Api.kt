package br.com.jaimenejaim.testedevjrandroidkotlin.data.remote

import br.com.jaimenejaim.testedevjrandroidkotlin.data.remote.dto.OrderDto
import br.com.jaimenejaim.testedevjrandroidkotlin.data.remote.dto.ProductDto
import retrofit2.http.GET

interface Api {
    @GET("/products")
    suspend fun getProducts(): List<ProductDto>

    @GET("/orders")
    suspend fun getOrders(): List<OrderDto>
}