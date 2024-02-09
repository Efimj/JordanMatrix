package com.example.mapp.main

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
                // Знаходження ненульового елемента в стовпці col
                var nonZeroRow = rank
                while (nonZeroRow < numRows && augmentedMatrix[nonZeroRow][col] == 0.0) {
                    nonZeroRow++
                }

                if (nonZeroRow == numRows) {
                    continue // Всі рядки під колом col мають нульові значення, переходимо до наступного стовпця
                }

                // Обмін рядками, щоб мати ненульовий елемент на позначеній позиції
                augmentedMatrix[rank] to augmentedMatrix[nonZeroRow]
                augmentedMatrix[nonZeroRow] to augmentedMatrix[rank]

                // Зрізання стовпця до ненульового елемента, щоб усі інші рядки мали нульові значення
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

        // Helper function to check if a matrix is square
        fun isSquareMatrix(matrix: Array<Array<Double>>): Boolean {
            val numRows = matrix.size
            return numRows > 0 && matrix.all { it.size == numRows }
        }
    }
}