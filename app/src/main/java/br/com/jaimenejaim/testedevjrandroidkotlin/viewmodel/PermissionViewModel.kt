package br.com.jaimenejaim.testedevjrandroidkotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(): ViewModel() {

    private val _hasPermission: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val hasPermission: StateFlow<Boolean> get() = _hasPermission


    fun setPermission(value: Boolean) {
        viewModelScope.launch {
            _hasPermission.emit(value)
        }
    }


}