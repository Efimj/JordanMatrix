package com.example.matrix.main

import com.example.matrix.main.other.ArrayHelper.Companion.addColumn
import com.example.matrix.main.other.ArrayHelper.Companion.addRow
import com.example.matrix.main.other.ArrayHelper.Companion.cloneArray
import com.example.matrix.main.other.ArrayHelper.Companion.printArray
import kotlin.math.abs

class TransportProblemSolver {
    companion object {
        data class TransportProblem(
            // An array providing different points with quantitative capability. (rows)
            val rowsPossibility: Array<Double>,

            // An array providing different points with quantitative needs. (cols)
            val colsNeed: Array<Double>,

            // Determines the price between points
            val matrix: Array<Array<Double>>
        )

        fun solveTransportProblem(input: TransportProblem) {
            var problem = TransportProblemSolver().cloneInput(input)
            if (TransportProblemSolver().checkIsClosedProblem(input).not()) {
                val quantitativePossibilitySum = input.rowsPossibility.sum()
                val quantitativeNeed = input.colsNeed.sum()
                val difference = quantitativePossibilitySum - quantitativeNeed
                if (difference > 0) {
                    problem = makeNewNeed(matrix = problem, difference = difference)
                } else {
                    problem = makeNewPossibility(matrix = problem, difference = difference)
                }
            }

            referenceSolveByNorthwestCorner(problem)
        }

        private fun referenceSolveByNorthwestCorner(input: TransportProblem) {
            val problem = TransportProblemSolver().cloneInput(input)
            val solve = Array(problem.matrix.size) { Array(problem.matrix.first().size) { 0.0 } }

            var row = 0
            var col = 0

            while (row < problem.matrix.size && col < problem.matrix.first().size) {
                if (problem.rowsPossibility[row] > 0) {
                    val difference = problem.colsNeed[col].coerceAtMost(problem.rowsPossibility[row])
                    solve[row][col] += difference

                    problem.colsNeed[col] = problem.colsNeed[col] - difference
                    problem.rowsPossibility[row] = problem.rowsPossibility[row] - difference

                    if (problem.colsNeed[col] == 0.0 && problem.rowsPossibility[row] == 0.0) {
                        row++
                        col++
                    } else if (problem.colsNeed[col] == 0.0) {
                        col++
                    } else {
                        row++
                    }
                }
            }
        }

        private fun makeNewNeed(
            matrix: TransportProblem,
            difference: Double
        ): TransportProblem {
            addColumn(matrix = matrix.matrix, newColumnPlaceholder = 0.0)
            val newQuantitativeNeed = Array(matrix.colsNeed.size + 1) { 0.0 }
            for (i in matrix.colsNeed.indices) {
                newQuantitativeNeed[i] = matrix.colsNeed[i]
            }
            newQuantitativeNeed[newQuantitativeNeed.lastIndex] = difference
            return matrix.copy(colsNeed = newQuantitativeNeed)
        }

        private fun makeNewPossibility(
            matrix: TransportProblem,
            difference: Double
        ): TransportProblem {
            addRow(matrix = matrix.matrix, newRowPlaceholder = 0.0)
            val newQuantitativePossibility = Array(matrix.rowsPossibility.size + 1) { 0.0 }
            for (i in matrix.rowsPossibility.indices) {
                newQuantitativePossibility[i] = matrix.rowsPossibility[i]
            }
            newQuantitativePossibility[newQuantitativePossibility.lastIndex] = difference
            return matrix.copy(rowsPossibility = newQuantitativePossibility)
        }
    }

    fun cloneInput(input: TransportProblem): TransportProblem {
        return TransportProblem(
            rowsPossibility = cloneArray(input.rowsPossibility),
            colsNeed = cloneArray(input.colsNeed),
            matrix = cloneArray(input.matrix),
        )
    }

    fun checkIsClosedProblem(input: TransportProblem): Boolean {
        val quantitativePossibilitySum = input.rowsPossibility.sum()
        val quantitativeNeed = input.colsNeed.sum()

        return quantitativePossibilitySum == quantitativeNeed
    }
}