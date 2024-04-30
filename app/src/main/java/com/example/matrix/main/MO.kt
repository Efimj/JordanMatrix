package com.example.matrix.main

import com.example.matrix.main.DualSimplexSolver.Companion.findResultsFor
import com.example.matrix.main.ModifiedMatrixHandler.Companion.Solve
import com.example.matrix.main.ModifiedMatrixHandler.Companion.searchOptimalSolveMaximum
import com.example.matrix.main.ModifiedMatrixHandler.Companion.searchReferenceSolution
import com.example.matrix.main.ModifiedMatrixHandler.Companion.XYPositions
import com.example.matrix.main.ModifiedMatrixHandler.Companion.Result.NoSolve
import com.example.matrix.main.ModifiedMatrixHandler.Companion.findXresults
import com.example.matrix.main.other.ArrayHelper.Companion.printArray
import com.example.matrix.main.games.MatrixGamesSolver.Companion.solveMatrixGame
import com.example.matrix.main.other.ArrayHelper.Companion.roundArray
import kotlin.math.abs

// Multicultural optimization
class MO() {
    companion object {
        fun findCompromiseSolution(matrices: Array<Array<Array<Double>>>, zRows: Array<Array<Double>>): Array<Double> {
            val optimalSolves = Array(matrices.size) {
                Solve(
                    matrix = emptyArray(),
                    result = NoSolve,
                    xyPos = XYPositions(cols = emptyArray(), rows = emptyArray())
                )
            }
            MO().findAllOptimalSolves(matrices = matrices, optimalSolves = optimalSolves)

            val matrixX = Array(optimalSolves.size) { findXresults(optimalSolves[it]) }
            val coefficients = Array(matrices.size) { Array(matrices.size) { 0.0 } }

            MO().findNonOptimalityCoefficients(coefficients = coefficients, cMatrix = zRows, xMatrix = matrixX)
            MO().changeSigns(matrix = coefficients)

            val weightingCoefficients = MO().findWeightingCoefficients(coefficients)

            val result = MO().findCompromiseVector(matrixX, weightingCoefficients)

            println()
            return result
        }
    }

    private fun findWeightingCoefficients(coefficients: Array<Array<Double>>): Array<Double> {
        val input = XYPositions(
            cols = Array(coefficients.first().size - 1) { "x${it + 1}" },
            rows = Array(coefficients.size - 1) { "y${it + 1}" }
        )
        val solution = solveMatrixGame(matrix = coefficients, xyPositions = input)

        val weightingCoefficients = Array(coefficients.size) {
            if (solution.firstPlayersSolution.size - 1 < it) 0.0
            else solution.firstPlayersSolution[it]
        }

        roundArray(array = weightingCoefficients, decimalPlaces = 3)

        println("Weighting coefficients")
        printArray(weightingCoefficients)
        println()

        return weightingCoefficients
    }

    private fun findCompromiseVector(
        matrixX: Array<Array<Double>>,
        weightingCoefficients: Array<Double>,
    ): Array<Double> {
        val result = Array(matrixX.first().size) { 0.0 }

        for (col in matrixX.first().indices) {
            var sum = 0.0
            for (row in matrixX.indices) {
                sum += matrixX[row][col] * weightingCoefficients[row]
            }
            result[col] = sum
        }

        return result
    }

    fun changeSigns(matrix: Array<Array<Double>>) {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                matrix[i][j] = -matrix[i][j]
            }
        }
    }

    private fun findNonOptimalityCoefficients(
        coefficients: Array<Array<Double>>,
        cMatrix: Array<Array<Double>>,
        xMatrix: Array<Array<Double>>
    ) {
        for (j in coefficients.indices) {
            val cjxj = MO().sumCX(
                cMatrix = cMatrix,
                xMatrix = xMatrix,
                indexX = j,
                indexC = j
            )
            for (i in coefficients.first().indices) {
                val ixj = MO().sumCX(
                    cMatrix = cMatrix,
                    xMatrix = xMatrix,
                    indexX = i,
                    indexC = j
                )

                coefficients[i][j] = abs(ixj / cjxj - 1.0)
            }
        }

        println("Matrix of non-optimal solutions:")
        printArray(coefficients)
        println()
    }

    private fun sumCX(
        cMatrix: Array<Array<Double>>,
        xMatrix: Array<Array<Double>>,
        indexX: Int,
        indexC: Int
    ): Double {
        var coefficient = 0.0

        for (index in cMatrix.first().indices) {
            coefficient += cMatrix[indexC][index] * xMatrix[indexX][index]
        }

        return coefficient
    }

    private fun findAllOptimalSolves(
        matrices: Array<Array<Array<Double>>>,
        optimalSolves: Array<Solve>
    ) {
        println()
        println("x results")

        for ((index, matrix) in matrices.withIndex()) {
            val input = XYPositions(
                cols = Array(matrix.first().size - 1) { "x${it + 1}" },
                rows = Array(matrix.size - 1) { "y${it + 1}" }
            )

            val referenceSolve = searchReferenceSolution(
                matrix = matrix,
                xy = input
            )

            val optimalSolveResult = searchOptimalSolveMaximum(
                matrix = referenceSolve.matrix!!,
                xy = referenceSolve.xyPos
            )

            printArray(findResultsFor(optimalSolveResult))
            println()

            optimalSolves[index] = optimalSolveResult
        }
        println()
    }
}