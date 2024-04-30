package com.example.matrix.main

import com.example.matrix.main.DualSimplexSolver.Companion.findResultsFor
import com.example.matrix.main.ModifiedMatrixHandler.Companion.Solve
import com.example.matrix.main.ModifiedMatrixHandler.Companion.searchOptimalSolveMaximum
import com.example.matrix.main.ModifiedMatrixHandler.Companion.searchReferenceSolution
import com.example.matrix.main.ModifiedMatrixHandler.Companion.XYPositions
import com.example.matrix.main.ModifiedMatrixHandler.Companion.Result.NoSolve
import com.example.matrix.main.ModifiedMatrixHandler.Companion.findXresults
import com.example.matrix.main.other.ArrayHelper.Companion.printArray
import kotlin.math.abs


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
            println()
            printArray(cMatrix)
            println()

            for (j in coefficients.indices) {
                val cjxj = MulticriteriaOptimization().sumCX(
                    cMatrix = cMatrix,
                    xMatrix = xMatrix,
                    indexX = j,
                    indexC = j
                )
                for (i in coefficients.first().indices) {
                    val ixj = MulticriteriaOptimization().sumCX(
                        cMatrix = cMatrix,
                        xMatrix = xMatrix,
                        indexX = i,
                        indexC = j
                    )

                    coefficients[i][j] = abs(ixj / cjxj - 1.0)
                }
            }
        }
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