package com.example.matrix.main

import com.example.matrix.main.ModifiedMatrixHandler.Companion.XYPositions

class MatrixGamesSolver {
    companion object {
        data class MatrixGameSolution(
            val firstPlayersSolution: Array<Double>,
            val secondPlayersSolution: Array<Double>,
            val outputMatrix: Array<Array<Double>>,
            val xyPositions: XYPositions
        )

        fun solveMatrixGame(matrix: Array<Array<Double>>, xyPositions: XYPositions):MatrixGameSolution {
            val minGamePrice = findMinGamePrice(matrix)
            val maxGamePrice = findMaxGamePrice(matrix)

            // If saddle point founded.
            if (minGamePrice == maxGamePrice) {
                return findSolveInPureStrategies(matrix, xyPositions)
            }

            return MatrixGameSolution(
                firstPlayersSolution = emptyArray(),
                secondPlayersSolution = emptyArray(),
                outputMatrix = matrix,
                xyPositions = xyPositions
            )
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
            val secondPlayersSolution = emptyList<Double>()

            for (row in matrix.indices) {
                secondPlayersSolution.plus(matrix[row][maxGamePriceIndex])
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
        fun findMinGamePrice(matrix: Array<Array<Double>>): Double {
            val minValuesArray = emptyList<Double>()
            matrix.forEach {
                minValuesArray.plus(it.minOrNull() ?: Double.MAX_VALUE)
            }
            return minValuesArray.maxOrNull() ?: Double.MIN_VALUE
        }

        fun findMinGamePriceIndex(matrix: Array<Array<Double>>): Int {
            val minValuesArray = emptyList<Double>()
            matrix.forEach {
                minValuesArray.plus(it.minOrNull() ?: Double.MAX_VALUE)
            }
            val minValue = minValuesArray.maxOrNull() ?: Double.MIN_VALUE

            return minValuesArray.indexOf(minValue)
        }

        /**
         * Find the maximum payoff for the first player.
         */
        fun findMaxGamePrice(matrix: Array<Array<Double>>): Double {
            val maxValuesArray = emptyList<Double>()
            for (row in matrix.indices) {
                val maxValue = Double.MIN_VALUE
                for (column in matrix[row].indices) {
                    maxValuesArray.plus(maxValue.coerceAtLeast(matrix[row][column]))
                }
            }
            return maxValuesArray.minOrNull() ?: Double.MAX_VALUE
        }

        fun findMaxGamePriceIndex(matrix: Array<Array<Double>>): Int {
            val maxValuesArray = emptyList<Double>()
            for (row in matrix.indices) {
                val maxValue = Double.MIN_VALUE
                for (column in matrix[row].indices) {
                    maxValuesArray.plus(maxValue.coerceAtLeast(matrix[row][column]))
                }
            }
            val maxValue = maxValuesArray.minOrNull() ?: Double.MAX_VALUE

            return maxValuesArray.indexOf(maxValue)
        }
    }
}