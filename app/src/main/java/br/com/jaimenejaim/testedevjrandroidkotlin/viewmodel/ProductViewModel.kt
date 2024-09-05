package br.com.jaimenejaim.testedevjrandroidkotlin.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jaimenejaim.testedevjrandroidkotlin.di.IoDispatcher
import br.com.jaimenejaim.testedevjrandroidkotlin.domain.toModel
import br.com.jaimenejaim.testedevjrandroidkotlin.domain.usecase.GetProductsUseCase
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.components.UiState
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.model.Product
import br.com.jaimenejaim.testedevjrandroidkotlin.util.CsvWriter
import br.com.jaimenejaim.testedevjrandroidkotlin.util.PRODUCT_CSV_FILE_NAME_WITH_EXTENSION
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val useCase: GetProductsUseCase,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _products: MutableStateFlow<UiState<List<Product>>> = MutableStateFlow(UiState.Idle())
    val products: StateFlow<UiState<List<Product>>>
        get() = _products

    private val _loadCsvSaveState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loadCsvSaveState: StateFlow<Boolean>
        get() = _loadCsvSaveState

    fun getAll() {
        viewModelScope.launch(dispatcher) {
            try {
                _products.emit(UiState.Loading())
                _products.emit(UiState.Success(data = useCase.invoke().map { it.toModel() }.sortedBy { it.description }))
            } catch (e: Exception) {
                _products.emit(UiState.Error(e))
            }
        }
    }

    fun exportToCsv(context: Context) {
        viewModelScope.launch(dispatcher) {
            if (_products.value is UiState.Success) {
                _loadCsvSaveState.value = true
                val list = (_products.value as UiState.Success<List<Product>>).data ?: arrayListOf()
                val csvWriter = CsvWriter(context, PRODUCT_CSV_FILE_NAME_WITH_EXTENSION, Product::class.java)
                csvWriter.writeData(list)
                _loadCsvSaveState.value = false
            }
        }
    }
}