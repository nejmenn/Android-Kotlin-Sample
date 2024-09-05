package br.com.jaimenejaim.testedevjrandroidkotlin.di

import android.content.Context
import br.com.jaimenejaim.testedevjrandroidkotlin.android.adapters.DiskPermissionAdapter
import br.com.jaimenejaim.testedevjrandroidkotlin.android.adapters.DiskPermissionAdapterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
open class AndroidModule {

    @Provides
    open fun provideBluetoothPermissionAdapter(@ApplicationContext context: Context): DiskPermissionAdapter {
        return DiskPermissionAdapterImpl(context = context)
    }
}