package com.example.matrix.main

import ArrayHelper.Companion.arrayToString
import ArrayHelper.Companion.cloneArray
import ArrayHelper.Companion.roundArray

class MatrixHandler {
    companion object {

        /**
         * One step to get inverse matrix by simple Jordan Elimination.
         */
        private fun simpleJordanEliminationStep(
            matrix: Array<Array<Double>>,
            row: Int,
            column: Int = row
        ): Array<Array<Double>> {
            val newMatrix = cloneArray(matrix)
            newMatrix[row][column] = 1.0

            // Changing string characters other than element position
            for (c in matrix[row].indices) {
                if (row != c)
                    newMatrix[row][c] = -newMatrix[row][c]
            }

            // Calculation of side elements
            for (r1 in matrix.indices) {
                for (c1 in matrix[r1].indices) {
                    if (r1 != row && c1 != row)
                        newMatrix[r1][c1] =
                            matrix[r1][c1] * matrix[row][column] - matrix[r1][column] * matrix[row][c1]
                }
            }

            // Dividing a matrix by element
            for (r1 in matrix.indices) {
                for (c1 in matrix[r1].indices) {
                    runCatching {
                        newMatrix[r1][c1] = newMatrix[r1][c1] / matrix[row][column]
                    }
                }
            }
            return newMatrix
        }

        // Finding the inverse of an arbitrary square matrix
        fun inverseMatrix(
            matrix: Array<Array<Double>>,
            getProtocol: (protocol: String) -> Unit = {}
        ): Array<Array<Double>>? {
            if (!isSquareMatrix(matrix)) {
                getProtocol("matrix isn't square")
                return null
            }

            var newMatrix = cloneArray(matrix)

            var protocol = ""

            // Matrix row walk
            for (r in newMatrix.indices) {
                newMatrix = simpleJordanEliminationStep(newMatrix, r)
                protocol += "step: \n$r\n"
                protocol += "matrix:\n" + arrayToString(newMatrix)
                protocol += "\n"
            }

            getProtocol(protocol)

            return newMatrix
        }

        private fun getInverse(
            augmentedMatrix: List<Array<Double>>,
            n: Int
        ) = augmentedMatrix.map { row ->
            row.slice(n until row.size).toTypedArray()
        }.toTypedArray()

        fun Array<Array<Double>>.getMatrixRank(): Int {
            val augmentedMatrix = this.map { it.clone() }.toTypedArray()

            val numRows = augmentedMatrix.size
            val numCols = augmentedMatrix[0].size

            var rank = 0

            for (col in 0 until numCols) {
                // Finding a non-zero element in col
                var nonZeroRow = rank
                while (nonZeroRow < numRows && augmentedMatrix[nonZeroRow][col] == 0.0) {
                    nonZeroRow++
                }

                if (nonZeroRow == numRows) {
                    continue // All lines under the col circle have zero values, we move to the next column
                }

                // Swap strings to have a non-null element at the indicated position
                augmentedMatrix[rank] to augmentedMatrix[nonZeroRow]
                augmentedMatrix[nonZeroRow] to augmentedMatrix[rank]

                // Truncate the column to a non-null element so that all other rows have null values
                for (i in rank + 1 until numRows) {
                    val factor = augmentedMatrix[i][col] / augmentedMatrix[rank][col]
                    for (j in col until numCols) {
                        augmentedMatrix[i][j] -= factor * augmentedMatrix[rank][j]
                    }
                }

                rank++
            }

            return rank
        }

        fun solveLinearSystem(
            matrix: Array<Array<Double>>,
            constants: Array<Double>,
            getProtocol: (protocol: String) -> Unit = {}
        ): Array<Double>? {
            var protocol = ""
            val n = matrix.size

            // Calculation of the inverse matrix
            val inverse = inverseMatrix(matrix) { protocol = it } ?: return null

            protocol += "\nsolution vector:\n"

            // Calculation of the solution vector
            val solution = DoubleArray(n)
            for (i in 0 until n) {
                protocol += "X[$i] = "
                var sum = 0.0
                for (j in 0 until n) {
                    sum += inverse[i][j] * constants[j]
                    protocol += "${inverse[i][j]} * ${constants[j]}"
                    if (j < n - 1) protocol += " + "
                }
                solution[i] = sum
                protocol += " = $sum\n"
            }

            getProtocol(protocol)
            return solution.toTypedArray()
        }

        // Helper function to check if a matrix is square
        fun isSquareMatrix(matrix: Array<Array<Double>>): Boolean {
            val numRows = matrix.size
            return numRows > 0 && matrix.all { it.size == numRows }
        }
    }
}