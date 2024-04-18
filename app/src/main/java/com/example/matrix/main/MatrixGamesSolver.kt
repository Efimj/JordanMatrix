package com.example.matrix.main

import ArrayHelper.Companion.cloneArray
import ArrayHelper.Companion.printArray
import com.example.matrix.main.ModifiedMatrixHandler.Companion.XYPositions
import kotlin.math.abs

class MatrixGamesSolver {
    companion object {
        data class MatrixGameSolution(
            val firstPlayersSolution: Array<Double>,
            val secondPlayersSolution: Array<Double>,
            val outputMatrix: Array<Array<Double>>,
            val xyPositions: XYPositions
        )

        fun solveMatrixGame(matrix: Array<Array<Double>>, xyPositions: XYPositions): MatrixGameSolution {
            val minGamePrice = MatrixGamesSolver().findMinGamePrice(matrix = matrix)
            val maxGamePrice = MatrixGamesSolver().findMaxGamePrice(matrix = matrix)

            // If saddle point founded.
            if (minGamePrice == maxGamePrice) {
                return MatrixGamesSolver().findSolveInPureStrategies(matrix = matrix, xyPositions = xyPositions)
            }

            return MatrixGamesSolver().findSolveInMixedStrategies(matrix = matrix, xyPositions = xyPositions)
        }
    }

    private fun findSolveInMixedStrategies(
        matrix: Array<Array<Double>>,
        xyPositions: XYPositions
    ): MatrixGameSolution {
        var handledMatrix = cloneArray(matrix)
        val minimumValue = removeNegativeValues(matrix = handledMatrix)
        handledMatrix = MatrixGamesSolver().generateSimplexMatrix(handledMatrix)

        printArray(handledMatrix)

        val referenceSolve = ModifiedMatrixHandler.searchReferenceSolution(matrix = handledMatrix, xy = xyPositions)
        val optimalSolveResult = ModifiedMatrixHandler.searchOptimalSolveMaximum(matrix = referenceSolve.matrix!!, xy = xyPositions)

        val secondPlayersSolution = DualSimplexSolver.findResultsFor(output = optimalSolveResult)
        val firstPlayersSolution = DualSimplexSolver.findDualResultsFor(name = "y", output = optimalSolveResult)

        return MatrixGameSolution(
            firstPlayersSolution = firstPlayersSolution,
            secondPlayersSolution = secondPlayersSolution,
            outputMatrix = optimalSolveResult.matrix!!,
            xyPositions = xyPositions
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
        val minGamePriceIndex = findMinGamePriceIndex(matrix)
        val maxGamePriceIndex = findMaxGamePriceIndex(matrix)

        // If minGamePriceIndex or maxGamePriceIndex not found
        if (minGamePriceIndex < 0 || maxGamePriceIndex < 0) {
            return MatrixGameSolution(
                firstPlayersSolution = emptyArray(),
                secondPlayersSolution = emptyArray(),
                outputMatrix = matrix,
                xyPositions = xyPositions
            )
        }

        val firstPlayersSolution = matrix[minGamePriceIndex]
        val secondPlayersSolution = mutableListOf<Double>()

        for (row in matrix.indices) {
            secondPlayersSolution.add(matrix[row][maxGamePriceIndex])
        }

        return MatrixGameSolution(
            firstPlayersSolution = firstPlayersSolution,
            secondPlayersSolution = secondPlayersSolution.toTypedArray(),
            outputMatrix = matrix,
            xyPositions = xyPositions
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

    private fun addRow(matrix: Array<Array<Double>>, newRowPlaceholder: Double): Array<Array<Double>> {
        val result = Array(matrix.size + 1) { Array(matrix[0].size) { newRowPlaceholder } }
        for (i in matrix.indices) {
            result[i] = matrix[i]
        }
        return result
    }

    private fun addColumn(matrix: Array<Array<Double>>, newColumnPlaceholder: Double): Array<Array<Double>> {
        val result = Array(matrix.size) { Array(matrix[0].size + 1) { newColumnPlaceholder } }
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                result[i][j] = matrix[i][j]
            }
        }
        return result
    }
}