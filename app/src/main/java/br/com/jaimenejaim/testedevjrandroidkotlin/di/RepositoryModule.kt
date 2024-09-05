package br.com.jaimenejaim.testedevjrandroidkotlin.di

import br.com.jaimenejaim.testedevjrandroidkotlin.repository.OrderRepository
import br.com.jaimenejaim.testedevjrandroidkotlin.repository.OrderRepositoryImpl
import br.com.jaimenejaim.testedevjrandroidkotlin.repository.ProductRepository
import br.com.jaimenejaim.testedevjrandroidkotlin.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providerProductRepository(repository: ProductRepositoryImpl): ProductRepository

    @Binds
    abstract fun provideOrderRepository(repositoryImpl: OrderRepositoryImpl): OrderRepository

}