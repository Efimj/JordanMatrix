package com.example.mapp.viewModel

import ArrayHelper.Companion.arrayToString
import ArrayHelper.Companion.fillTwoDimArrayRandomly
import ArrayHelper.Companion.generateTwoDimArray
import ArrayHelper.Companion.roundArray
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mapp.main.MatrixHandler

data class MainScreenStates(
    val output: String = "none",
    val matrixRows: Int = 3,
    val matrixColumns: Int = 3,

    )

class MainScreenViewModel : ViewModel() {
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

    fun inverseRandomMatrix() {
        val array = generateTwoDimArray(states.value.matrixRows, states.value.matrixColumns, 0.0)
        fillTwoDimArrayRandomly(array, -5.0, +5.0, 3)

        var newOutput = "${states.value.output}\nArray:\n"
        newOutput += arrayToString(array)
        newOutput += "\n" + "Inverse:" + "\n"

        MatrixHandler.inverseMatrix(array).let {
            if (it != null) {
                roundArray(it, 3)
                newOutput += arrayToString(it)
            } else {
                newOutput += "error"
            }
        }
        _states.value = states.value.copy(output = newOutput)
    }

    fun clearOutput() {
        _states.value = states.value.copy(output = "")
    }
}