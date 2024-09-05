package br.com.jaimenejaim.testedevjrandroidkotlin.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jaimenejaim.testedevjrandroidkotlin.di.IoDispatcher
import br.com.jaimenejaim.testedevjrandroidkotlin.domain.toModel
import br.com.jaimenejaim.testedevjrandroidkotlin.domain.usecase.GetOrdersUseCase
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.components.UiState
import br.com.jaimenejaim.testedevjrandroidkotlin.ui.model.Order
import br.com.jaimenejaim.testedevjrandroidkotlin.util.CsvWriter
import br.com.jaimenejaim.testedevjrandroidkotlin.util.ORDER_CSV_FILE_NAME_WITH_EXTENSION
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val useCase: GetOrdersUseCase,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _orders: MutableStateFlow<UiState<List<Order>>> = MutableStateFlow(UiState.Idle())
    val orders: StateFlow<UiState<List<Order>>>
        get() = _orders

    private val _loadCsvSaveState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loadCsvSaveState: StateFlow<Boolean>
        get() = _loadCsvSaveState

    fun getAll() {
        viewModelScope.launch(dispatcher) {
            try {
                _orders.emit(UiState.Loading())
                _orders.emit(UiState.Success(data = useCase.invoke().map { it.toModel() }.sortedBy { it.description }))
            } catch (e: Exception) {
                _orders.emit(UiState.Error(e))
            }
        }
    }

    fun exportToCsv(context: Context) {
        SupervisorJob(
            viewModelScope.launch(dispatcher) {
                if (orders.value is UiState.Success) {
                    _loadCsvSaveState.value = true
                    val list = (orders.value as UiState.Success<List<Order>>).data ?: arrayListOf()
                    val csvWriter = CsvWriter(context, ORDER_CSV_FILE_NAME_WITH_EXTENSION, Order::class.java)
                    csvWriter.writeData(list)
                    _loadCsvSaveState.value = false
                }
            }
        )
    }
}