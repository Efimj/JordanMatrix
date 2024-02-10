package com.example.matrix.viewModel

import ArrayHelper.Companion.arrayToString
import ArrayHelper.Companion.fillTwoDimArrayRandomly
import ArrayHelper.Companion.generateTwoDimArray
import ArrayHelper.Companion.roundArray
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.matrix.main.MatrixHandler
import com.example.matrix.main.MatrixHandler.Companion.getMatrixRank
import kotlin.random.Random

data class MainScreenStates(
    val output: String = "none",
    val matrixRows: Int = 3,
    val matrixColumns: Int = 3,
    val matrixMinValue: Int = -5,
    val matrixMaxValue: Int = 5,
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

    fun setMatrixMinValue(valueString: String) {
        val matrixRows = valueString.toIntOrNull() ?: return

        if (matrixRows < 0)
            _states.value = _states.value.copy(matrixMinValue = matrixRows)
    }

    fun setMatrixMaxValue(valueString: String) {
        val matrixColumns = valueString.toIntOrNull() ?: return

        if (matrixColumns > 0)
            _states.value = _states.value.copy(matrixMaxValue = matrixColumns)
    }

    fun inverseRandomMatrix() {
        val matrix = getRandomMatrix()

        var newOutput = states.value.output
        newOutput += "\n\n-------- Matrix inverse -------\n"
        newOutput += "Matrix:\n"
        newOutput += arrayToString(matrix)
        newOutput += "\n" + "Inverse:" + "\n"

        MatrixHandler.inverseMatrix(matrix).let {
            newOutput += if (it != null) {
                roundArray(it, 3)
                arrayToString(it)
            } else {
                "error"
            }
        }
        _states.value = states.value.copy(output = newOutput)
    }

    fun getRandomMatrixRank() {
        val matrix = getRandomMatrix()

        var newOutput = states.value.output
        newOutput += "\n\n-------- Matrix rank -------\n"
        newOutput += "Matrix:\n"
        newOutput += arrayToString(matrix)
        newOutput += "\n" + "Rank:" + "\n"

        matrix.getMatrixRank().let {
            newOutput += it
        }
        _states.value = states.value.copy(output = newOutput)
    }

    fun getSolveRandomMatrix() {
        val matrix = getRandomMatrix()
        val constants = arrayOf(
            getRandomMatrixNumber(),
            getRandomMatrixNumber(),
            getRandomMatrixNumber()
        )
        roundArray(constants, 3)

        var newOutput = states.value.output
        newOutput += "\n\n-------- Solve matrix -------\n"
        newOutput += "Matrix:\n"
        newOutput += arrayToString(matrix)
        newOutput += "\n" + "Constants:" + "\n"
        newOutput += arrayToString(constants)
        newOutput += "\n\n" + "Solve:" + "\n"

        val output = MatrixHandler.solveLinearSystem(matrix, constants) ?: return
        roundArray(output, 3)

        newOutput += arrayToString(output)

        _states.value = states.value.copy(output = newOutput)
    }

    private fun getRandomMatrixNumber() =
        Random.nextDouble(states.value.matrixMinValue.toDouble(), states.value.matrixMaxValue.toDouble())

    private fun getRandomMatrix(): Array<Array<Double>> {
        val array = generateTwoDimArray(states.value.matrixRows, states.value.matrixColumns, 0.0)
        fillTwoDimArrayRandomly(
            array,
            states.value.matrixMinValue.toDouble(),
            states.value.matrixMaxValue.toDouble(),
            3
        )
        return array
    }

    fun clearOutput() {
        _states.value = states.value.copy(output = "")
    }
}