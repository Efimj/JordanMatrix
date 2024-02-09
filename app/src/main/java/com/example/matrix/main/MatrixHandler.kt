package com.example.matrix.main

class MatrixHandler {
    companion object {
        // Finding the inverse of an arbitrary square matrix
        fun inverseMatrix(matrix: Array<Array<Double>>): Array<Array<Double>>? {
            val n = matrix.size

            // Checking if a matrix is square
            if (!isSquareMatrix(matrix)) {
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

            for (i in 0 until n) {
                val pivot = augmentedMatrix[i][i]

                if (pivot == 0.0) {
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
            }

            // Extracting the inverse matrix from the expanded matrix
            val inverse = augmentedMatrix.map { row ->
                row.slice(n until row.size).toTypedArray()
            }.toTypedArray()

            return inverse
        }

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

        fun solveLinearSystem(matrix: Array<Array<Double>>, constants: Array<Double>): Array<Double>? {
            val n = matrix.size

            // Calculation of the inverse matrix
            val inverse = inverseMatrix(matrix) ?: return null

            // Calculation of the solution vector
            val solution = DoubleArray(n)
            for (i in 0 until n) {
                var sum = 0.0
                for (j in 0 until n) {
                    sum += inverse[i][j] * constants[j]
                }
                solution[i] = sum
            }

            return solution.toTypedArray()
        }

        // Helper function to check if a matrix is square
        fun isSquareMatrix(matrix: Array<Array<Double>>): Boolean {
            val numRows = matrix.size
            return numRows > 0 && matrix.all { it.size == numRows }
        }
    }
}