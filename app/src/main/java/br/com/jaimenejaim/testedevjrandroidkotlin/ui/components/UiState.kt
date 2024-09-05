package br.com.jaimenejaim.testedevjrandroidkotlin.ui.components

sealed class UiState<T> {
    data class Success<T>(var data: T?) : UiState<T>()
    data class Error<T>(val throwable: Throwable?) : UiState<T>()
    class Loading<T>() : UiState<T>()
    class Idle<T> : UiState<T>()
}