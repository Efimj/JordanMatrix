package com.example.mapp.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class MainScreenStates(
    val output: String = "none",
    val matrixRows: Int = 3,
    val matrixColumns: Int = 3,

    )

class SimpleMainScreenViewModel : ViewModel() {
    private val _states = mutableStateOf(MainScreenStates())
    val states: State<MainScreenStates> = _states

    fun setMatrixRows(matrixRowsString: String) {
        val matrixRows = matrixRowsString.toIntOrNull() ?: return

        if (matrixRows >= 1)
            _states.value = _states.value.copy(matrixRows = matrixRows)
    }

    fun setMatrixColumns(matrixColumnsString: String) {
        val matrixColumns = matrixColumnsString.toIntOrNull() ?: return

        if (matrixColumns >= 1)
            _states.value = _states.value.copy(matrixColumns = matrixColumns)
    }
}