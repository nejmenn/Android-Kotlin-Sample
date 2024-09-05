package br.com.jaimenejaim.testedevjrandroidkotlin.di

import android.content.Context
import br.com.jaimenejaim.testedevjrandroidkotlin.BuildConfig
import br.com.jaimenejaim.testedevjrandroidkotlin.data.remote.Api
import br.com.jaimenejaim.testedevjrandroidkotlin.data.remote.interceptors.CacheInterceptor
import br.com.jaimenejaim.testedevjrandroidkotlin.data.remote.interceptors.CacheInterceptor.Companion.httpCache
import br.com.jaimenejaim.testedevjrandroidkotlin.data.remote.interceptors.LogInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providerRetrofit(
        @ApplicationContext context: Context
    ): Api {

        val okHttpClientBuilder = OkHttpClient().newBuilder()

        val okHttpClient = okHttpClientBuilder
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(LogInterceptor.httpLoggingInterceptor())
            .addInterceptor(CacheInterceptor())
            .cache(httpCache(context))
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(Api::class.java)
    }
}