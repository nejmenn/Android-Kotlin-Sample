package br.com.jaimenejaim.testedevjrandroidkotlin.repository

import br.com.jaimenejaim.testedevjrandroidkotlin.data.datasource.ProductRemoteDatasource
import br.com.jaimenejaim.testedevjrandroidkotlin.domain.ProductDomain
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val datasource: ProductRemoteDatasource
): ProductRepository {
    override suspend fun getAll(): List<ProductDomain> {
        return datasource.getAll()
    }
}