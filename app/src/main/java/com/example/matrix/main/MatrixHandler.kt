package com.example.matrix.main

import ArrayHelper.Companion.arrayToString
import ArrayHelper.Companion.roundArray

class MatrixHandler {
    companion object {
        // Finding the inverse of an arbitrary square matrix
        fun inverseMatrix(
            matrix: Array<Array<Double>>,
            getProtocol: (protocol: String) -> Unit = {}
        ): Array<Array<Double>>? {
            val n = matrix.size

            // Checking if a matrix is square
            if (!isSquareMatrix(matrix)) {
                getProtocol("matrix isn't square")
                return null
            }

            // Creating a unit matrix
            val identityMatrix = Array(n) { i ->
                Array(n) { j ->
                    if (i == j) 1.0 else 0.0
                }
            }

            // A copy of the input matrix to avoid changes in the input data
            val augmentedMatrix = matrix.mapIndexed { index, row ->
                row + identityMatrix[index]
            }

            var protocol = ""

            for (i in 0 until n) {
                val pivot = augmentedMatrix[i][i]
                protocol += "[$i, $i] = $pivot\n"

                if (pivot == 0.0) {
                    getProtocol("matrix doesnt have an inverse matrix")
                    return null // The matrix does not have an inverse matrix
                }

                // Steps of Normal Jordan Exceptions
                for (j in 0 until 2 * n) {
                    augmentedMatrix[i][j] /= pivot
                }

                for (k in 0 until n) {
                    if (k != i) {
                        val factor = augmentedMatrix[k][i]
                        for (j in 0 until 2 * n) {
                            augmentedMatrix[k][j] -= factor * augmentedMatrix[i][j]
                        }
                    }
                }

                val currentMatrix = getInverse(augmentedMatrix, n)
                roundArray(currentMatrix, 3)

                protocol += "step: \n$i\n"
                protocol += "matrix:\n" + arrayToString(currentMatrix)
                protocol += "\n"

            }

            // Extracting the inverse matrix from the expanded matrix
            val inverse = getInverse(augmentedMatrix, n)
            getProtocol(protocol)

            return inverse
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