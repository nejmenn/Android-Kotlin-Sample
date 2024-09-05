package br.com.jaimenejaim.testedevjrandroidkotlin.domain.usecase

import br.com.jaimenejaim.testedevjrandroidkotlin.domain.ProductDomain

interface GetProductsUseCase {
    suspend operator fun invoke(): List<ProductDomain>
}