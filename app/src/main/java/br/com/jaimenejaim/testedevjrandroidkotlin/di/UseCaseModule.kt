package br.com.jaimenejaim.testedevjrandroidkotlin.di

import br.com.jaimenejaim.testedevjrandroidkotlin.domain.usecase.GetOrdersUseCase
import br.com.jaimenejaim.testedevjrandroidkotlin.domain.usecase.GetOrdersUseCaseImpl
import br.com.jaimenejaim.testedevjrandroidkotlin.domain.usecase.GetProductsUseCase
import br.com.jaimenejaim.testedevjrandroidkotlin.domain.usecase.GetProductsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun provideGetProducts(useCase: GetProductsUseCaseImpl): GetProductsUseCase
    @Binds
    abstract fun provideGetOrders(useCase: GetOrdersUseCaseImpl): GetOrdersUseCase
}