package br.com.jaimenejaim.testedevjrandroidkotlin.domain.usecase

import br.com.jaimenejaim.testedevjrandroidkotlin.domain.OrderDomain
import br.com.jaimenejaim.testedevjrandroidkotlin.repository.OrderRepository
import javax.inject.Inject

class GetOrdersUseCaseImpl @Inject constructor(
    private val repository: OrderRepository
): GetOrdersUseCase {
    override suspend operator fun invoke(): List<OrderDomain> {
        return repository.getAll()
    }
}