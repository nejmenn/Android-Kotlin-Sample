package br.com.jaimenejaim.testedevjrandroidkotlin.data.remote.interceptors

import br.com.jaimenejaim.testedevjrandroidkotlin.BuildConfig
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

object LogInterceptor {
    fun httpLoggingInterceptor(): Interceptor {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }
}