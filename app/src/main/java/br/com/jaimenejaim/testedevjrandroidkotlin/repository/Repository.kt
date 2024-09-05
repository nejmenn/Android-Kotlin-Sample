package br.com.jaimenejaim.testedevjrandroidkotlin.repository

interface Repository<T> {
    suspend fun getAll(): List<T>
}