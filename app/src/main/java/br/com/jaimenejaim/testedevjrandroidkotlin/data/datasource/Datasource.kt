package br.com.jaimenejaim.testedevjrandroidkotlin.data.datasource

interface Datasource<T> {
    suspend fun getAll(): List<T>
}