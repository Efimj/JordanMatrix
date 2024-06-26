package com.example.matrix.main

import com.example.matrix.main.other.ArrayHelper.Companion.addRow
import com.example.matrix.main.other.ArrayHelper.Companion.addValueAtPosition
import com.example.matrix.main.other.ArrayHelper.Companion.cloneArray
import com.example.matrix.main.other.ArrayHelper.Companion.printArray
import com.example.matrix.main.other.ArrayHelper.Companion.roundArray
import com.example.matrix.main.ModifiedMatrixHandler.Companion.Solve
import com.example.matrix.main.ModifiedMatrixHandler.Companion.Result
import com.example.matrix.main.ModifiedMatrixHandler.Companion.findXresults
import kotlin.math.absoluteValue

class IntegerSolveHandler {
    companion object {
        fun findIntegerSolve(
            matrix: Array<Array<Double>>,
            xy: ModifiedMatrixHandler.Companion.XYPositions,
        ): Solve {
            var newMatrix = cloneArray(matrix)
            var xyPos = xy.copy(cols = cloneArray(xy.cols), rows = cloneArray(xy.rows))

            var counter = 0

            do {
                counter++

                val resultAfterReference = ModifiedMatrixHandler.searchReferenceSolution(
                    matrix = newMatrix,
                    xy = xyPos
                )
                if (resultAfterReference.matrix == null) {
                    println("resultAfterReference.matrix == null")
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


                println("Reference")
                printArray(findXresults(resultAfterReference))
                println()
                println("Optimal")
                printArray(currentX)
                println()
                println("OLD")
                printArray(newMatrix)
                println()
                println("NEW")
                printArray(resultAfterOptimal.matrix!!)
                println("POS")
                println("rows")
                printArray(resultAfterOptimal.xyPos.rows)
                println()
                println("cols")
                printArray(resultAfterOptimal.xyPos.cols)
                println()

                if (isAllIntegers || resultAfterOptimal.matrix == null) {
                    println("isAllIntegers || resultAfterOptimal.matrix == null")
                    return resultAfterOptimal
                }

                val indexNumberWithMaxFractionalPart = currentX.indexOf(currentX.maxBy { it % 1.0 })

                val newRestriction = Array(resultAfterOptimal.matrix[indexNumberWithMaxFractionalPart].size) { 0.0 }
                resultAfterOptimal.matrix[indexNumberWithMaxFractionalPart].forEachIndexed { index, value ->
                    if (index == resultAfterOptimal.matrix[indexNumberWithMaxFractionalPart].size - 1) {
                        newRestriction[index] = -(value % 1.0)
                    } else {
                        newRestriction[index] = if (value == 0.0) {
                            0.0
                        } else -if (value > 0.0) {
                            value % 1.0
                        } else {
                            1 - (value % 1.0).absoluteValue
                        }
                    }
                }

                // Round
                roundArray(newRestriction, 3)

                val position = resultAfterOptimal.matrix.size - 1
                newMatrix =
                    addRow(matrix = resultAfterOptimal.matrix, rowIndex = position, newRow = newRestriction)
                val newRowsX = addValueAtPosition(array = resultAfterOptimal.xyPos.rows, position = position, "s${counter}")
                xyPos = resultAfterOptimal.xyPos.copy(rows = newRowsX)

                println("NEW pos ")
                println("rows")
                printArray(resultAfterOptimal.xyPos.rows)
                println()
                println("cols")
                printArray(resultAfterOptimal.xyPos.cols)
                println()

            } while (counter < 15)
            return Solve(
                matrix = null,
                result = Result.NoSolve,
                xyPos = xyPos
            )
        }
    }
}