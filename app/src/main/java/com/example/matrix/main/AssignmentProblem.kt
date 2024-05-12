package com.example.matrix.main

import com.example.matrix.main.other.ArrayHelper.Companion.cloneArray

class AssignmentProblem {
    companion object {
        data class AssignmentProblemSolve(
            val assignments: Array<Array<Int>>,
            val cost: Int
        )

        fun solveAssignmentProblem(inputMatrix: Array<Array<Int>>): AssignmentProblemSolve {
            val matrix = cloneArray(inputMatrix)

            AssignmentProblem().minusMinByRows(matrix)
            AssignmentProblem().minusMinByCols(matrix)
            AssignmentProblem().handleRemoveZeroCells(matrix)
            val assignmentMatrix = AssignmentProblem().findAssignments(matrix)
            val cost = findAssignmentCost(assignmentMatrix = assignmentMatrix, inputMatrix = inputMatrix)

            return AssignmentProblemSolve(
                assignments = assignmentMatrix,
                cost = cost
            )
        }

        private fun findAssignmentCost(
            assignmentMatrix: Array<Array<Int>>,
            inputMatrix: Array<Array<Int>>
        ): Int {
            var cost = 0
            for (r in assignmentMatrix.indices) {
                for (c in assignmentMatrix.indices) {
                    if (assignmentMatrix[r][c] == 1) {
                        cost += inputMatrix[r][c]
                    }
                }
            }

            return cost
        }
    }

    private fun findAssignments(matrix: Array<Array<Int>>): Array<Array<Int>> {
        val assignments = mutableListOf<Pair<Int, Int>>()
        val assignmentMatrix = Array(matrix.size) { Array(matrix.size) { 0 } }

        while (assignments.size < matrix.size) {
            for (r in matrix.indices) {
                for (c in matrix.indices) {
                    if (assignments.any { it.first == r || it.second == c }) continue
                    if (matrix[r][c] == 0) {
                        assignments.add(Pair(r, c))
                    }
                }
            }
        }

        assignments.forEach {
            assignmentMatrix[it.first][it.second] = 1
        }

        return assignmentMatrix
    }

    private fun handleRemoveZeroCells(matrix: Array<Array<Int>>) {
        var removedRowIndices = mutableListOf<Int>()
        var removedColIndices = mutableListOf<Int>()

        while (removedRowIndices.size + removedColIndices.size != matrix.size) {
            removedRowIndices = mutableListOf()
            removedColIndices = mutableListOf()

            AssignmentProblem().removeAllNullLines(
                matrix = matrix,
                removedRowIndices = removedRowIndices,
                removedColIndices = removedColIndices
            )

            var minElement: Int? = null
            for (r in matrix.indices) {
                for (c in matrix[r].indices) {
                    // Skip removed
                    if (r in removedRowIndices || c in removedColIndices) continue

                    if (minElement == null || matrix[r][c] < minElement) {
                        minElement = matrix[r][c]
                    }
                }
            }

            if (minElement == null) {
                println("ERROR: minElement == ZERO")
                return
            }

            // For not removed
            for (r in matrix.indices) {
                for (c in matrix[r].indices) {
                    // Skip removed
                    if (r in removedRowIndices || c in removedColIndices) continue

                    matrix[r][c] -= minElement
                }
            }

            // For cross column and row removed
            for (r in matrix.indices) {
                for (c in matrix[r].indices) {
                    if (r in removedRowIndices && c in removedColIndices) {
                        matrix[r][c] += minElement
                    }
                }
            }
        }
    }

    private fun removeAllNullLines(
        matrix: Array<Array<Int>>,
        removedRowIndices: MutableList<Int>,
        removedColIndices: MutableList<Int>
    ) {
        for (i in 0..matrix.size) {
            var rowIndexWithMaxZeros = -1
            var countRowZeros = 0
            var colIndexWithMaxZeros = -1
            var countColZeros = 0

            for (r in matrix.indices) {
                // if row removed
                if (r in removedRowIndices) continue

                val countZeros = matrix[r].count { it == 0 }
                if (countZeros > countRowZeros) {
                    rowIndexWithMaxZeros = r
                    countRowZeros = countZeros
                }
            }

            for (c in matrix[0].indices) {
                // if column removed
                if (c in removedColIndices) continue

                var countZeros = 0
                for (r in matrix.indices) {
                    if (matrix[r][c] == 0) {
                        countZeros++
                    }
                }

                if (countZeros > countColZeros) {
                    colIndexWithMaxZeros = c
                    countColZeros = countZeros
                }
            }

            if (countRowZeros == 0 && countColZeros == 0 && rowIndexWithMaxZeros == -1 && colIndexWithMaxZeros == -1) {
                break
            }

            if (countRowZeros > countColZeros) {
                removedRowIndices.add(rowIndexWithMaxZeros)
            } else {
                removedColIndices.add(colIndexWithMaxZeros)
            }
        }
    }

    private fun minusMinByCols(matrix: Array<Array<Int>>) {
        for (j in matrix.indices) {
            var min = matrix[0][j]

            for (r in matrix.indices) {
                if (matrix[r][j] < min) {
                    min = matrix[r][j]
                }
            }

            for (r in matrix.indices) {
                matrix[r][j] -= min
            }
        }
    }

    private fun minusMinByRows(matrix: Array<Array<Int>>) {
        for (i in matrix.indices) {
            val min = matrix[i].min()
            for (c in matrix[i].indices) {
                matrix[i][c] -= min
            }
        }
    }
}