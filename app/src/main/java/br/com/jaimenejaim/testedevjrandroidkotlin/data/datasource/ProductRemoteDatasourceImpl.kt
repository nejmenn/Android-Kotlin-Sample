package br.com.jaimenejaim.testedevjrandroidkotlin.data.datasource

import br.com.jaimenejaim.testedevjrandroidkotlin.data.remote.Api
import br.com.jaimenejaim.testedevjrandroidkotlin.data.remote.dto.toDomain
import br.com.jaimenejaim.testedevjrandroidkotlin.domain.ProductDomain
import javax.inject.Inject

class ProductRemoteDatasourceImpl @Inject constructor(
    private val api: Api
): ProductRemoteDatasource {
    override suspend fun getAll(): List<ProductDomain> {
        return api.getProducts().map { it.toDomain() }
    }
}