package com.example.matrix.main

import com.example.matrix.main.other.ArrayHelper.Companion.addColumn
import com.example.matrix.main.other.ArrayHelper.Companion.addRow
import com.example.matrix.main.other.ArrayHelper.Companion.cloneArray

class TransportProblemSolver {
    companion object {
        data class TransportProblem(
            // An array providing different points with quantitative capability. (rows)
            val quantitativePossibility: Array<Double>,

            // An array providing different points with quantitative needs. (cols)
            val quantitativeNeed: Array<Double>,

            // Determines the price between points
            val matrix: Array<Array<Double>>
        )

        fun solveTransportProblem(input: TransportProblem) {
            var problem = TransportProblemSolver().cloneInput(input)
            if (TransportProblemSolver().checkIsClosedProblem(input).not()) {
                val quantitativePossibilitySum = input.quantitativePossibility.sum()
                val quantitativeNeed = input.quantitativeNeed.sum()
                val difference = quantitativePossibilitySum - quantitativeNeed
                if (difference > 0) {
                    problem = makeNewNeed(matrix = problem, difference = difference)
                } else {
                    problem = makeNewPossibility(matrix = problem, difference = difference)
                }
            }

        }

        private fun makeNewNeed(
            matrix: TransportProblem,
            difference: Double
        ): TransportProblem {
            addColumn(matrix = matrix.matrix, newColumnPlaceholder = 0.0)
            val newQuantitativeNeed = Array(matrix.quantitativeNeed.size + 1) { 0.0 }
            for (i in matrix.quantitativeNeed.indices) {
                newQuantitativeNeed[i] = matrix.quantitativeNeed[i]
            }
            newQuantitativeNeed[newQuantitativeNeed.lastIndex] = difference
            return matrix.copy(quantitativeNeed = newQuantitativeNeed)
        }

        private fun makeNewPossibility(
            matrix: TransportProblem,
            difference: Double
        ): TransportProblem {
            addRow(matrix = matrix.matrix, newRowPlaceholder = 0.0)
            val newQuantitativePossibility = Array(matrix.quantitativePossibility.size + 1) { 0.0 }
            for (i in matrix.quantitativePossibility.indices) {
                newQuantitativePossibility[i] = matrix.quantitativePossibility[i]
            }
            newQuantitativePossibility[newQuantitativePossibility.lastIndex] = difference
            return matrix.copy(quantitativePossibility = newQuantitativePossibility)
        }
    }

    fun cloneInput(input: TransportProblem): TransportProblem {
        return TransportProblem(
            quantitativePossibility = cloneArray(input.quantitativePossibility),
            quantitativeNeed = cloneArray(input.quantitativeNeed),
            matrix = cloneArray(input.matrix),
        )
    }

    fun checkIsClosedProblem(input: TransportProblem): Boolean {
        val quantitativePossibilitySum = input.quantitativePossibility.sum()
        val quantitativeNeed = input.quantitativeNeed.sum()

        return quantitativePossibilitySum == quantitativeNeed
    }
}