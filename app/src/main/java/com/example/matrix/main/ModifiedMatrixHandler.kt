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

        fun findXresults(
            output: OptimalSolveResult,
        ): Array<Double> {
            val res = Array(output.xyPos.cols.size) { 0.0 }
            for (index in output.xyPos.cols.indices) {
                if (output.xyPos.cols[index].startsWith("x")) {
                    val digitAfterX = output.xyPos.cols[index].substringAfter("x")
                    try {
                        val result = digitAfterX.toInt()
                        res[result - 1] = 0.0
                    } catch (e: NumberFormatException) {
                        println("NumberFormatException")
                    }
                }
            }

            for (index in output.xyPos.rows.indices) {
                if (output.xyPos.rows[index].startsWith("x")) {
                    val digitAfterX = output.xyPos.rows[index].substringAfter("x")
                    try {
                        val result = digitAfterX.toInt()
                        res[result - 1] = output.matrix?.get(index)?.last()!!
                    } catch (e: NumberFormatException) {
                        println("NumberFormatException")
                    }
                }
            }
            return res
        }

        enum class SolveResult {
            Solved,
            NoSolve,
            NoRestrictionsAbove,
            ContradictoryRestrictions,
        }

        data class OptimalSolveResult(
            val matrix: Array<Array<Double>>?,
            val result: SolveResult,
            val xyPos: XYPositions,
        )

        data class XYPositions(
            val cols: Array<String>,    // Top
            val rows: Array<String>,    // Left
        )

        /**
         * @param matrix is input matrix.
         * @param xPositions is
         */
        fun searchOptimalSolveMaximum(
            matrix: Array<Array<Double>>,
            xy: XYPositions,
        ): OptimalSolveResult {
            var newMatrix = cloneArray(matrix)
            var xyPos = xy.copy(cols = cloneArray(xy.cols), rows = cloneArray(xy.rows))

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
                        xyPos = xyPos
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
                        xyPos = xyPos
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
                        xyPos = xyPos
                    )


                val temp1 = xyPos.cols[negativeZElementColumn]
                val temp2 = xyPos.rows[MNVindex]

                xyPos.rows[MNVindex] = temp1
                xyPos.cols[negativeZElementColumn] = temp2

                newMatrix = result
            }
        }

        fun searchReferenceSolution(
            matrix: Array<Array<Double>>,
            xy: XYPositions,
        ): OptimalSolveResult {
            var newMatrix = cloneArray(matrix)
            val xyPos = xy.copy(cols = cloneArray(xy.cols), rows = cloneArray(xy.rows))

            while (true) {
                var negativeSingleElementRow = -1
                val singleValues = newMatrix.map { it.last() }.toTypedArray()
                for (i in singleValues.indices) {
                    if (singleValues[i] < 0) {
                        negativeSingleElementRow = i
                        break
                    }
                }

                if (negativeSingleElementRow < 0) {
                    roundArray(newMatrix, 2)
                    return OptimalSolveResult(
                        matrix = newMatrix,
                        result = SolveResult.Solved,
                        xyPos = xyPos
                    )
                }

                var elementColumn: Int? = null
                for (i in newMatrix.first().indices) {
                    if (newMatrix[negativeSingleElementRow][i] < 0.0) {
                        elementColumn = i
                        break
                    }
                }

                if (elementColumn == null) {
                    roundArray(newMatrix, 2)
                    return OptimalSolveResult(
                        matrix = newMatrix,
                        result = SolveResult.ContradictoryRestrictions,
                        xyPos = xyPos
                    )
                }

                fun minimalPositive(matrix: Array<Array<Double>>, colNumber: Int): Double? {
                    if (colNumber < 0 || colNumber >= matrix[0].size) {
                        return null
                    }

                    var minimalPositiveValue: Double? = null

                    for (row in matrix.indices) {
                        val currentValue = matrix[row][colNumber]

                        if (currentValue >= 0 && (minimalPositiveValue == null || currentValue < minimalPositiveValue)) {
                            minimalPositiveValue = currentValue
                        }
                    }

                    return minimalPositiveValue
                }


                val MNVindex = minimalPositive(newMatrix, elementColumn)!!.toInt()

                val result = modifiedJordanEliminationStep(
                    matrix = newMatrix,
                    row = MNVindex,
                    column = elementColumn
                )
                    ?: return OptimalSolveResult(
                        matrix = null,
                        result = SolveResult.NoSolve,
                        xyPos = xyPos
                    )


                val temp1 = xyPos.cols[elementColumn]
                val temp2 = xyPos.rows[MNVindex]

                xyPos.rows[MNVindex] = temp1
                xyPos.cols[elementColumn] = temp2

                newMatrix = result
            }
        }
    }
}