package com.example.matrix.main

import ArrayHelper.Companion.addRow
import ArrayHelper.Companion.addValueAtPosition
import ArrayHelper.Companion.cloneArray
import ArrayHelper.Companion.printArray
import com.example.matrix.main.ModifiedMatrixHandler.Companion.Solve
import com.example.matrix.main.ModifiedMatrixHandler.Companion.Result
import com.example.matrix.main.ModifiedMatrixHandler.Companion.findXresults

class IntegerSolveHandler {
    companion object {
        fun findIntegerSolve(
            matrix: Array<Array<Double>>,
            xy: ModifiedMatrixHandler.Companion.XYPositions,
        ): Solve {
            var newMatrix = cloneArray(matrix)
            var xyPos = xy.copy(cols = cloneArray(xy.cols), rows = cloneArray(xy.rows))

            var finishd = false
            var counter = 0

            do {
                counter++

                val resultAfterReference = ModifiedMatrixHandler.searchReferenceSolution(
                    matrix = newMatrix,
                    xy = xyPos
                )
                if (resultAfterReference.matrix == null) {
                    return Solve(
                        matrix = null,
                        result = Result.NoSolve,
                        xyPos = xyPos
                    )
                }
                val resultAfterOptimal = ModifiedMatrixHandler.searchOptimalSolveMaximum(
                    matrix = resultAfterReference.matrix,
                    xy = resultAfterReference.xyPos
                )

                val currentX = findXresults(resultAfterOptimal)
                val isAllIntegers = currentX.all { it % 1.0 == 0.0 }

                if (isAllIntegers || resultAfterOptimal.matrix == null) {
                    return resultAfterOptimal
                }

                val indexNumberWithMaxFractionalPart = currentX.indexOf(currentX.maxBy { it % 1.0 })

                val newRestriction = Array(resultAfterOptimal.matrix[indexNumberWithMaxFractionalPart].size) { 0.0 }
                resultAfterOptimal.matrix[indexNumberWithMaxFractionalPart].forEachIndexed { index, value ->
                    if (index == resultAfterOptimal.matrix[indexNumberWithMaxFractionalPart].size - 1) {
                        newRestriction[index] = -(value % 1.0)
                    } else {
                        newRestriction[index] = -if (value > 0.0) {
                            value % 1.0
                        } else {
                            1 - (value % 1.0)
                        }
                    }
                }

                val position = resultAfterOptimal.matrix.size - 2
                newMatrix =
                    addRow(matrix = newMatrix, rowIndex = position, newRow = newRestriction)
                val newRowsX = addValueAtPosition(array = xyPos.rows, position = position, "s${counter}")
                xyPos = xyPos.copy(rows = newRowsX)

            } while (finishd.not())
            return Solve(
                matrix = null,
                result = Result.NoSolve,
                xyPos = xyPos
            )
        }
    }
}