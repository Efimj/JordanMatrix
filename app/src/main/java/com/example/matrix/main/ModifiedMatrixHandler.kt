package com.example.matrix.main

import com.example.matrix.main.other.ArrayHelper.Companion.cloneArray
import com.example.matrix.main.other.ArrayHelper.Companion.printArray
import com.example.matrix.main.other.ArrayHelper.Companion.removeColumn
import com.example.matrix.main.other.ArrayHelper.Companion.removeRow
import com.example.matrix.main.other.ArrayHelper.Companion.roundArray

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
            output: Solve,
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

        enum class Result {
            Solved,
            NoSolve,
            NoRestrictionsAbove,
            NoRestrictionsBelow,
            ContradictoryRestrictions,
        }

        data class Solve(
            val matrix: Array<Array<Double>>?,
            val result: Result,
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
        ): Solve {
            var newMatrix = cloneArray(matrix)
            val xyPos = xy.copy(cols = cloneArray(xy.cols), rows = cloneArray(xy.rows))

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
                    return Solve(
                        matrix = newMatrix,
                        result = Result.Solved,
                        xyPos = xyPos
                    )
                }

                val minimalPositiveRow = minimalPositiveRow(newMatrix, negativeZElementColumn)

                if (minimalPositiveRow == null) {
                    roundArray(newMatrix, 2)
                    return Solve(
                        matrix = newMatrix,
                        result = Result.NoRestrictionsAbove,
                        xyPos = xyPos
                    )
                }

                val result = modifiedJordanEliminationStep(
                    matrix = newMatrix,
                    row = minimalPositiveRow,
                    column = negativeZElementColumn
                )
                    ?: return Solve(
                        matrix = null,
                        result = Result.NoSolve,
                        xyPos = xyPos
                    )

                switchXAfterwordElimination(xyPos, minimalPositiveRow, negativeZElementColumn)

                newMatrix = result
            }
        }

        fun minimalPositiveRow(matrix: Array<Array<Double>>, currentColumn: Int): Int? {
            if (currentColumn < 0 || currentColumn >= matrix[0].size) {
                return null
            }

            var minimalPositiveIndex: Int? = null
            var minimalPositiveValue = Double.MAX_VALUE

            for (row in 0..matrix.size - 2) {
                if (matrix[row][currentColumn] == 0.0) continue
                if (matrix[row][matrix[row].size - 1] == 0.0 && matrix[row][currentColumn] < 0) continue

                val currentValue = matrix[row][matrix[row].size - 1] / matrix[row][currentColumn]
                if (currentValue < 0) continue
                if (currentValue < minimalPositiveValue) {
                    minimalPositiveIndex = row
                    minimalPositiveValue = currentValue
                }
            }

            return minimalPositiveIndex
        }

        fun searchReferenceSolution(
            matrix: Array<Array<Double>>,
            xy: XYPositions,
        ): Solve {
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
                    return Solve(
                        matrix = newMatrix,
                        result = Result.Solved,
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
                    return Solve(
                        matrix = newMatrix,
                        result = Result.ContradictoryRestrictions,
                        xyPos = xyPos
                    )
                }

                val minimalPositiveRow = minimalPositiveRow(newMatrix, elementColumn)!!

                val result = modifiedJordanEliminationStep(
                    matrix = newMatrix,
                    row = minimalPositiveRow,
                    column = elementColumn
                )
                    ?: return Solve(
                        matrix = null,
                        result = Result.NoSolve,
                        xyPos = xyPos
                    )


                switchXAfterwordElimination(xyPos, minimalPositiveRow, elementColumn)

                newMatrix = result
            }
        }

        private fun switchXAfterwordElimination(
            xyPos: XYPositions,
            row: Int,
            column: Int,
        ) {
            val temp1 = xyPos.cols[column]
            val temp2 = xyPos.rows[row]

            xyPos.rows[row] = temp1
            xyPos.cols[column] = temp2
        }

        fun removeNullLines(
            matrix: Array<Array<Double>>,
            xy: XYPositions,
        ): Solve {
            var newMatrix = cloneArray(matrix)
            var xyPos = xy.copy(cols = cloneArray(xy.cols), rows = cloneArray(xy.rows))

            while (true) {

                val rowIndex = findRowWithZero(xyPos.rows)
                    ?: return Solve(
                        matrix = newMatrix,
                        result = Result.Solved,
                        xyPos = xyPos
                    )

                var currentColumn: Int? = null
                for (column in newMatrix[rowIndex].indices) {
                    if (newMatrix[rowIndex][column] > 0) {
                        currentColumn = column
                        break
                    }
                }

                if (currentColumn == null) return Solve(
                    matrix = newMatrix,
                    result = Result.ContradictoryRestrictions,
                    xyPos = xyPos
                )

                val minimalPositiveRow = minimalPositiveRow(newMatrix, currentColumn)!!

                val result = modifiedJordanEliminationStep(
                    matrix = newMatrix,
                    row = minimalPositiveRow,
                    column = currentColumn
                )
                    ?: return Solve(
                        matrix = null,
                        result = Result.NoSolve,
                        xyPos = xyPos
                    )

                newMatrix = result

                switchXAfterwordElimination(xyPos, minimalPositiveRow, currentColumn)


                if (xyPos.cols[currentColumn] == "0") {
                    newMatrix = removeColumn(newMatrix, currentColumn)
                    xyPos =
                        xyPos.copy(cols = xyPos.cols.filterIndexed { index, _ -> index != currentColumn }
                            .toTypedArray())
                }

            }
        }

        fun findRowWithZero(matrix: Array<String>): Int? {
            for ((index, row) in matrix.withIndex()) {
                if (row == "0") {
                    return index
                }
            }
            return null
        }

        data class SolveWithIndependentVariables(
            val solve: Solve,
            val arrayToHandleX: Array<String>
        )

        fun removeIndependentVariables(
            matrix: Array<Array<Double>>,
            xy: XYPositions,
            independentVariables: Array<String>
        ): SolveWithIndependentVariables {
            var newMatrix = cloneArray(matrix)
            var xyPos = xy.copy(cols = cloneArray(xy.cols), rows = cloneArray(xy.rows))
            var independents = cloneArray(independentVariables)

            var arrayToHandleX = emptyList<String>()

            while (independents.isNotEmpty()) {

                // Find independent variable
                val currentIndependentIndex = xyPos.cols.indexOf(independents.first())
                printArray(independents)
                println()

                if (currentIndependentIndex == -1) {
                    println("wss")
                    return SolveWithIndependentVariables(
                        solve = Solve(
                            matrix = newMatrix,
                            result = Result.Solved,
                            xyPos = xyPos
                        ),
                        arrayToHandleX = arrayToHandleX.toTypedArray()
                    )
                }

                val row = 0
                val result = modifiedJordanEliminationStep(
                    matrix = newMatrix,
                    row = row,
                    column = currentIndependentIndex
                )
                    ?: return SolveWithIndependentVariables(
                        solve = Solve(
                            matrix = null,
                            result = Result.NoSolve,
                            xyPos = xyPos
                        ),
                        arrayToHandleX = arrayToHandleX.toTypedArray()
                    )

                newMatrix = result

                // Switch x to y
                switchXAfterwordElimination(xyPos, row, currentIndependentIndex)

                // Save handle expression to find x
                var resultString = ""

                xyPos.cols.forEachIndexed { index, value ->
                    val string = -(Math.round(newMatrix[row][index] * 100.0) / 100.0)
                    resultString += if (string < 0) {
                        string.toString()
                    } else {
                        "+${string}"

                    }
                    resultString += value
                }

                val singleValue = (newMatrix[row][newMatrix[row].size-1])
                resultString += if(singleValue>0) "+${singleValue}" else singleValue.toString()

                arrayToHandleX = arrayToHandleX.plus(resultString)

                // Removing the current row
                newMatrix = removeRow(newMatrix, row)

                // Remove independent variable
                independents =
                    independents.filterIndexed { index, value -> value != xyPos.rows[row] }
                        .toTypedArray()

                // Remove xy position
                xyPos = xyPos.copy(rows = xyPos.rows.filterIndexed { index, value -> index != row }.toTypedArray())

            }
            return SolveWithIndependentVariables(
                solve = Solve(
                    matrix = newMatrix,
                    result = Result.Solved,
                    xyPos = xyPos
                ),
                arrayToHandleX = arrayToHandleX.toTypedArray()
            )
        }
    }
}