package com.example.matrix.main

import ArrayHelper.Companion.cloneArray
import ArrayHelper.Companion.printArray
import ArrayHelper.Companion.roundArray

class ModifiedMatrixHandler {
    companion object {
        /**
         * One step to get inverse matrix by modified Jordan Elimination.
         */
        fun modifiedJordanEliminationStep(
            matrix: Array<Array<Double>>,
            row: Int,
            column: Int,
        ): Array<Array<Double>>? {
            val newMatrix = cloneArray(matrix)

            // Changing string characters other than element position
            for (r in matrix.indices) {
                newMatrix[r][column] = -newMatrix[r][column]
            }

            newMatrix[row][column] = 1.0

            // Calculation of side elements
            for (r1 in matrix.indices) {
                for (c1 in matrix[r1].indices) {
                    if (r1 != row && c1 != column)
                        newMatrix[r1][c1] =
                            matrix[r1][c1] * matrix[row][column] - matrix[r1][column] * matrix[row][c1]
                }
            }

            // Dividing a matrix by element
            for (r1 in matrix.indices) {
                for (c1 in matrix[r1].indices) {
                    try {
                        newMatrix[r1][c1] = newMatrix[r1][c1] / matrix[row][column]
                    } catch (e: Exception) {
                        return null
                    }
                }
            }

            return newMatrix
        }

        enum class SolveResult {
            Solved,
            NoSolve,
            NoRestrictionsAbove,
        }

        data class OptimalSolveResult(
            val matrix: Array<Array<Double>>?,
            val result: SolveResult,
            val xPos: Array<Int>?,
        )

        /**
         * @param matrix is input matrix.
         * @param xPositions is
         */
        fun searchOptimalSolve(
            matrix: Array<Array<Double>>,
            xPositions: Array<Int> = Array(matrix.first().size) { 1 },
        ): OptimalSolveResult {
            var newMatrix = cloneArray(matrix)
            var xPos = cloneArray(xPositions)

            while (true) {
                var negativeZElementColumn = -1
                val zValues = newMatrix.last().dropLast(1)
                for (i in zValues.indices) {
                    if (zValues[i] < 0) {
                        negativeZElementColumn = i
                        break
                    }
                }

                if (negativeZElementColumn < 0) {
                    roundArray(newMatrix, 2)
                    return OptimalSolveResult(
                        matrix = newMatrix,
                        result = SolveResult.Solved,
                        xPos = xPos
                    )
                }

                var MNVindex: Int? = null
                for (i in newMatrix.indices) {
                    if (newMatrix[i][negativeZElementColumn] <= 0.0) continue
                    if (MNVindex == null || newMatrix[i][negativeZElementColumn] > newMatrix[MNVindex][negativeZElementColumn])
                        MNVindex = i
                }

                if (MNVindex == null) {
                    roundArray(newMatrix, 2)
                    return OptimalSolveResult(
                        matrix = newMatrix,
                        result = SolveResult.NoRestrictionsAbove,
                        xPos = xPos
                    )
                }

                val result = modifiedJordanEliminationStep(
                    matrix = newMatrix,
                    row = MNVindex,
                    column = negativeZElementColumn
                )
                    ?: return OptimalSolveResult(
                        matrix = null,
                        result = SolveResult.NoSolve,
                        xPos = xPos
                    )

                for (index in xPos.indices) {
                    if (xPos[index] == negativeZElementColumn + 1) {
                        xPos[index] = -MNVindex - 1
                        println()
                        println("${xPos[index]}  r ${MNVindex} c ${negativeZElementColumn} >")
                        break
                    }
                    if (xPos[index] == -MNVindex - 1) {
                        xPos[index] = negativeZElementColumn + 1
                        println()
                        println("${xPos[index]}  r ${MNVindex} c ${negativeZElementColumn} <")
                        break
                    }
                }

                // -2  2  3  4
                // -2 -1  3  4
                //  1 -1  3  4
                //  1 -1 -2  4
                //  1 -1  4 -2

                printArray(xPos)
                println()

                newMatrix = result
            }
        }
    }
}