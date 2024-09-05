package br.com.jaimenejaim.testedevjrandroidkotlin.domain.usecase

import br.com.jaimenejaim.testedevjrandroidkotlin.domain.ProductDomain
import br.com.jaimenejaim.testedevjrandroidkotlin.repository.ProductRepository
import javax.inject.Inject

class GetProductsUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
): GetProductsUseCase {
    override suspend fun invoke(): List<ProductDomain> {
        return repository.getAll()
    }
}