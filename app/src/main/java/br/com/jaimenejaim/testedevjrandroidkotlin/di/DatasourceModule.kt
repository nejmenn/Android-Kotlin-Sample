package br.com.jaimenejaim.testedevjrandroidkotlin.di

import br.com.jaimenejaim.testedevjrandroidkotlin.data.datasource.OrderRemoteDatasource
import br.com.jaimenejaim.testedevjrandroidkotlin.data.datasource.OrderRemoteDatasourceImpl
import br.com.jaimenejaim.testedevjrandroidkotlin.data.datasource.ProductRemoteDatasource
import br.com.jaimenejaim.testedevjrandroidkotlin.data.datasource.ProductRemoteDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DatasourceModule {
    @Binds
    internal abstract fun providerGetProductDatasource(datasource: ProductRemoteDatasourceImpl): ProductRemoteDatasource

    @Binds
    internal abstract fun providerGetOrderDatasource(datasource: OrderRemoteDatasourceImpl): OrderRemoteDatasource
}