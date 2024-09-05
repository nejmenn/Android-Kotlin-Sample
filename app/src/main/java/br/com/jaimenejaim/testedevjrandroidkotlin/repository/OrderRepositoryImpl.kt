package br.com.jaimenejaim.testedevjrandroidkotlin.repository

import br.com.jaimenejaim.testedevjrandroidkotlin.data.datasource.OrderRemoteDatasource
import br.com.jaimenejaim.testedevjrandroidkotlin.domain.OrderDomain
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val datasource: OrderRemoteDatasource
): OrderRepository {
    override suspend fun getAll(): List<OrderDomain> {
        return datasource.getAll()
    }
}