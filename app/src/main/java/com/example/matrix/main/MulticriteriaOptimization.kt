package com.example.matrix.main

import com.example.matrix.main.DualSimplexSolver.Companion.findResultsFor
import com.example.matrix.main.ModifiedMatrixHandler.Companion.Solve
import com.example.matrix.main.ModifiedMatrixHandler.Companion.searchOptimalSolveMaximum
import com.example.matrix.main.ModifiedMatrixHandler.Companion.searchReferenceSolution
import com.example.matrix.main.ModifiedMatrixHandler.Companion.XYPositions
import com.example.matrix.main.ModifiedMatrixHandler.Companion.Result.NoSolve
import com.example.matrix.main.ModifiedMatrixHandler.Companion.findXresults
import com.example.matrix.main.other.ArrayHelper.Companion.printArray


class MulticriteriaOptimization() {
    companion object {
        fun findCompromiseSolution(matrices: Array<Array<Array<Double>>>, zRows: Array<Array<Double>>) {
            val optimalSolves = Array(matrices.size) {
                Solve(
                    matrix = emptyArray(),
                    result = NoSolve,
                    xyPos = XYPositions(cols = emptyArray(), rows = emptyArray())
                )
            }
            MulticriteriaOptimization().findAllOptimalSolves(matrices = matrices, optimalSolves = optimalSolves)
            val Xmatrix = Array(optimalSolves.size) { findXresults(optimalSolves[it]) }
            val coefficients = Array(matrices.size) { Array(matrices.size) { 0.0 } }

            findNonOptimalityCoefficients(coefficients = coefficients, cMatrix = zRows, xMatrix = Xmatrix)

            printArray(coefficients)
        }

        private fun findNonOptimalityCoefficients(
            coefficients: Array<Array<Double>>,
            cMatrix: Array<Array<Double>>,
            xMatrix: Array<Array<Double>>
        ) {
            for (row in coefficients.indices) {
                for (col in coefficients.first().indices) {
                    var cx1 = 0.0
                    for (index in cMatrix.indices) {
                        cx1 += cMatrix[col][index] * xMatrix[row][index]
                    }

                    var cx2 = 0.0
                    for (index in cMatrix.indices) {
                        cx2 += cMatrix[col][index] * xMatrix[col][index]
                    }

                    val coefficient = (cx1 - cx2) / cx2

                    coefficients[row][col] = coefficient
                }
            }
        }
    }

    private fun findAllOptimalSolves(
        matrices: Array<Array<Array<Double>>>,
        optimalSolves: Array<Solve>
    ) {
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
    }
}