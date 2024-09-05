package br.com.jaimenejaim.testedevjrandroidkotlin.domain.usecase

import br.com.jaimenejaim.testedevjrandroidkotlin.domain.OrderDomain

interface GetOrdersUseCase {
    suspend operator fun invoke(): List<OrderDomain>
}