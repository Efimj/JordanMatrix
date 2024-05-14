package com.example.matrix.main.games

import com.example.matrix.main.other.ArrayHelper.Companion.cloneArray
import com.example.matrix.main.DualSimplexSolver
import com.example.matrix.main.ModifiedMatrixHandler
import com.example.matrix.main.ModifiedMatrixHandler.Companion.XYPositions
import com.example.matrix.main.other.ArrayHelper.Companion.addColumn
import com.example.matrix.main.other.ArrayHelper.Companion.addRow
import kotlin.math.abs

class MatrixGamesSolver {
    companion object {
        enum class StrategiesType {
            PureStrategies,
            MixedStrategies,
            Undefined
        }

        data class MatrixGameSolution(
            val firstPlayersSolution: Array<Double>,
            val secondPlayersSolution: Array<Double>,
            val outputMatrix: Array<Array<Double>>,
            val xyPositions: XYPositions,
            val gamePrice: Double,
            val strategyType: StrategiesType
        )

        fun solveMatrixGame(matrix: Array<Array<Double>>, xyPositions: XYPositions): MatrixGameSolution {
            val handledMatrix = cloneArray(matrix)
            val minimumValue = MatrixGamesSolver().removeNegativeValues(matrix = handledMatrix)

            val minGamePrice = MatrixGamesSolver().findMinGamePrice(matrix = handledMatrix)
            val maxGamePrice = MatrixGamesSolver().findMaxGamePrice(matrix = handledMatrix)

            // If saddle point founded.
            if (minGamePrice == maxGamePrice) {
                return MatrixGamesSolver().findSolveInPureStrategies(matrix = handledMatrix, xyPositions = xyPositions)
            }

            return MatrixGamesSolver().findSolveInMixedStrategies(
                matrix = handledMatrix,
                minimumValue = minimumValue,
                xyPositions = xyPositions
            )
        }
    }

    private fun findSolveInMixedStrategies(
        matrix: Array<Array<Double>>,
        minimumValue: Double,
        xyPositions: XYPositions
    ): MatrixGameSolution {
        var handledMatrix = cloneArray(matrix)
        handledMatrix = MatrixGamesSolver().generateSimplexMatrix(handledMatrix)

        val referenceSolve = ModifiedMatrixHandler.searchReferenceSolution(matrix = handledMatrix, xy = xyPositions)
        val optimalSolveResult =
            ModifiedMatrixHandler.searchOptimalSolveMaximum(matrix = referenceSolve.matrix!!, xy = xyPositions)

        val secondPlayersSolution = DualSimplexSolver.findResultsFor(output = optimalSolveResult)
        val firstPlayersSolution = DualSimplexSolver.findDualResultsFor(name = "y", output = optimalSolveResult)

        val lastCellValue = optimalSolveResult.matrix!!.last().last()
        val gamePrice = 1.0.div(lastCellValue) - abs(minimumValue)

        secondPlayersSolution.forEachIndexed { index, value ->
            secondPlayersSolution[index] = value * 1.0.div(lastCellValue)
        }

        firstPlayersSolution.forEachIndexed { index, value ->
            firstPlayersSolution[index] = value * 1.0.div(lastCellValue)
        }

        return MatrixGameSolution(
            firstPlayersSolution = firstPlayersSolution,
            secondPlayersSolution = secondPlayersSolution,
            outputMatrix = optimalSolveResult.matrix,
            xyPositions = xyPositions,
            strategyType = StrategiesType.MixedStrategies,
            gamePrice = gamePrice
        )
    }

    private fun removeNegativeValues(matrix: Array<Array<Double>>): Double {
        var minimumValue = 0.0
        matrix.forEach {
            it.forEach { value ->
                minimumValue = minimumValue.coerceAtMost(value)
            }
        }

        if (minimumValue < 0.0) {
            // Increase all values for remove negative values
            for (i in matrix.indices) {
                for (j in matrix[i].indices) {
                    matrix[i][j] += abs(minimumValue)
                }
            }
        }

        return minimumValue
    }

    private fun findSolveInPureStrategies(
        matrix: Array<Array<Double>>,
        xyPositions: XYPositions
    ): MatrixGameSolution {
        val gamePrice = findMinGamePrice(matrix)
        val minGamePriceIndex = findMinGamePriceIndex(matrix)
        val maxGamePriceIndex = findMaxGamePriceIndex(matrix)

        // If minGamePriceIndex or maxGamePriceIndex not found
        if (minGamePriceIndex < 0 || maxGamePriceIndex < 0) {
            return MatrixGameSolution(
                firstPlayersSolution = emptyArray(),
                secondPlayersSolution = emptyArray(),
                outputMatrix = matrix,
                xyPositions = xyPositions,
                strategyType = StrategiesType.Undefined,
                gamePrice = 0.0
            )
        }

        val firstPlayersSolution = Array(matrix.size) { 0.0 }
        firstPlayersSolution[minGamePriceIndex] = 1.0

        val secondPlayersSolution = Array(matrix.first().size) { 0.0 }
        secondPlayersSolution[maxGamePriceIndex] = 1.0


        return MatrixGameSolution(
            firstPlayersSolution = firstPlayersSolution,
            secondPlayersSolution = secondPlayersSolution,
            outputMatrix = matrix,
            xyPositions = xyPositions,
            strategyType = StrategiesType.PureStrategies,
            gamePrice = gamePrice
        )
    }

    /**
     * Find the first player's guaranteed payoff.
     */
    private fun findMinGamePrice(matrix: Array<Array<Double>>): Double {
        val minValuesArray = mutableListOf<Double>()
        matrix.forEach {
            minValuesArray.add(it.minOrNull() ?: Double.MAX_VALUE)
        }
        return minValuesArray.maxOrNull() ?: Double.MIN_VALUE
    }

    private fun findMinGamePriceIndex(matrix: Array<Array<Double>>): Int {
        val minValuesArray = mutableListOf<Double>()
        matrix.forEach {
            minValuesArray.add(it.minOrNull() ?: Double.MAX_VALUE)
        }
        val minValue = minValuesArray.maxOrNull() ?: Double.MIN_VALUE

        return minValuesArray.indexOf(minValue)
    }

    /**
     * Find the maximum payoff for the first player.
     */
    private fun findMaxGamePrice(matrix: Array<Array<Double>>): Double {
        val maxValuesArray = findColumnsMaxValues(matrix)
        return maxValuesArray.minOrNull() ?: Double.MAX_VALUE
    }

    private fun findMaxGamePriceIndex(matrix: Array<Array<Double>>): Int {
        val maxValuesArray = findColumnsMaxValues(matrix)
        val maxValue = maxValuesArray.minOrNull() ?: Double.MAX_VALUE
        return maxValuesArray.indexOf(maxValue)
    }

    private fun findColumnsMaxValues(matrix: Array<Array<Double>>): MutableList<Double> {
        val maxValuesArray = mutableListOf<Double>()
        val columns = matrix[0].size

        for (column in 0 until columns) {
            var maxValue = Double.MIN_VALUE
            for (row in matrix.indices) {
                maxValue = maxValue.coerceAtLeast(
                    matrix[row][column]
                )
            }
            maxValuesArray.add(maxValue)
        }
        return maxValuesArray
    }

    private fun generateSimplexMatrix(matrix: Array<Array<Double>>): Array<Array<Double>> {
        var newMatrix = cloneArray(matrix)

        newMatrix = addColumn(matrix = newMatrix, newColumnPlaceholder = 1.0)
        newMatrix = addRow(matrix = newMatrix, newRowPlaceholder = -1.0)
        newMatrix[newMatrix.size - 1][newMatrix[newMatrix.size - 1].size - 1] = 0.0

        return newMatrix
    }
}