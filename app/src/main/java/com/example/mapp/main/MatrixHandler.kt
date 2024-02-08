package com.example.mapp.main

class MatrixHandler {
    companion object {
        // Пошук оберненої матриці для довільної квадратної матриці
        fun inverseMatrix(matrix: Array<Array<Double>>): Array<Array<Double>>? {
            val n = matrix.size

            // Перевірка, чи матриця є квадратною
            if (!isSquareMatrix(matrix)) {
                return null
            }

            // Створення одиничної матриці
            val identityMatrix = Array(n) { i ->
                Array(n) { j ->
                    if (i == j) 1.0 else 0.0
                }
            }

            // Копія вхідної матриці для уникнення змін у вхідних даних
            val augmentedMatrix = matrix.mapIndexed { index, row ->
                row + identityMatrix[index]
            }

            for (i in 0 until n) {
                val pivot = augmentedMatrix[i][i]

                if (pivot == 0.0) {
                    return null // Матриця не має оберненої матриці
                }

                // Кроки Звичайних Жорданових виключень
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

            // Витягнення оберненої матриці з розширеної матриці
            val inverse = augmentedMatrix.map { row ->
                row.slice(n until row.size).toTypedArray()
            }.toTypedArray()

            return inverse
        }

        // Допоміжна функція для перевірки, чи матриця є квадратною
        fun isSquareMatrix(matrix: Array<Array<Double>>): Boolean {
            val numRows = matrix.size
            return numRows > 0 && matrix.all { it.size == numRows }
        }
    }
}