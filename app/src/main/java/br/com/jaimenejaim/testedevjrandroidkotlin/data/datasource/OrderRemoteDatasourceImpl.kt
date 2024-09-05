package br.com.jaimenejaim.testedevjrandroidkotlin.data.datasource

import br.com.jaimenejaim.testedevjrandroidkotlin.data.remote.Api
import br.com.jaimenejaim.testedevjrandroidkotlin.data.remote.dto.toDomain
import br.com.jaimenejaim.testedevjrandroidkotlin.domain.OrderDomain
import javax.inject.Inject

class OrderRemoteDatasourceImpl @Inject constructor(
    private val api: Api
): OrderRemoteDatasource {
    override suspend fun getAll(): List<OrderDomain> {
        return api.getOrders().map { it.toDomain() }
    }
}