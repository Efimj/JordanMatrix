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

        fun solveMatrixGame(matrix: Array<Array<Double>>, xyPositions: XYPositions): MatrixGameSolution {
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
    }
}