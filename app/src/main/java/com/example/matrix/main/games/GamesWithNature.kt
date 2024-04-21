package com.example.matrix.main.games

import ArrayHelper.Companion.printArray
import kotlin.math.sign

class GamesWithNature {
    companion object {
        fun solveByAbrahamWald(matrix: Array<Array<Double>>): Int {
            val minValuesArray = GamesWithNature().findMinInRows(matrix = matrix)
            val maxValue = minValuesArray.maxOrNull() ?: Double.MIN_VALUE

            return minValuesArray.indexOf(maxValue)
        }

        fun solveByAbrahamWaldMaxMax(matrix: Array<Array<Double>>): Int {
            val maxValuesArray = GamesWithNature().findMaxInRows(matrix = matrix)
            val maxValue = maxValuesArray.maxOrNull() ?: Double.MIN_VALUE

            return maxValuesArray.indexOf(maxValue)
        }

        fun solveByAdolfHurwitz(matrix: Array<Array<Double>>, coefficient: Double = 1.0): Int {
            val maxValuesArray = GamesWithNature().findMaxInRows(matrix = matrix)
            val minValuesArray = GamesWithNature().findMinInRows(matrix = matrix)

            val coefficients = mutableListOf<Double>()

            for (row in maxValuesArray.indices) {
                val current = coefficient * minValuesArray[row] + (1 - coefficient) * maxValuesArray[row]
                coefficients.add(current)
            }

            val maxCoefficient = coefficients.maxOrNull()

            return coefficients.indexOf(maxCoefficient)
        }

        fun solveByLeonardJimmieSavage(matrix: Array<Array<Double>>): Int {
            val newMatrix = Array(matrix.size) { Array(matrix.first().size) { 0.0 } }

            for (col in newMatrix.first().indices) {
                var maxInColumn = Double.MIN_VALUE

                for (row in newMatrix.indices) {
                    maxInColumn = maxInColumn.coerceAtLeast(matrix[row][col])
                }

                for (row in newMatrix.indices) {
                    newMatrix[row][col] = maxInColumn - matrix[row][col]
                }
            }

            val maxValuesArray = GamesWithNature().findMaxInRows(matrix = newMatrix)

            val minValue = maxValuesArray.minOrNull()

            return maxValuesArray.indexOf(minValue)
        }

        fun solveByThomasBayes(matrix: Array<Array<Double>>, probabilities: Array<Double>): Int {
            val coefficientArray = mutableListOf<Double>()

            for (row in matrix.indices) {
                var value = 0.0
                for (col in matrix.first().indices) {
                    value += matrix[row][col] * probabilities[col]
                }
                coefficientArray.add(value)
            }

            val maxValue = coefficientArray.maxOrNull() ?: Double.MIN_VALUE

            return coefficientArray.indexOf(maxValue)
        }

        fun solvePierreSimondeLaplace  (matrix: Array<Array<Double>>): Int {
            val coefficient = 1.0 / matrix.first().size

            val coefficientArray = mutableListOf<Double>()

            for (row in matrix.indices) {
                var value = 0.0
                for (col in matrix.first().indices) {
                    value += coefficient * matrix[row][col]
                }
                coefficientArray.add(value)
            }

            val maxValue = coefficientArray.maxOrNull() ?: Double.MIN_VALUE

            return coefficientArray.indexOf(maxValue)
        }
    }

    private fun findMinInRows(matrix: Array<Array<Double>>): MutableList<Double> {
        val minValuesArray = mutableListOf<Double>()
        matrix.forEach {
            minValuesArray.add(it.minOrNull() ?: Double.MAX_VALUE)
        }
        return minValuesArray
    }

    private fun findMaxInRows(matrix: Array<Array<Double>>): MutableList<Double> {
        val minValuesArray = mutableListOf<Double>()
        matrix.forEach {
            minValuesArray.add(it.maxOrNull() ?: Double.MAX_VALUE)
        }
        return minValuesArray
    }
}