package com.example.matrix

import ArrayHelper.Companion.printArray
import com.example.matrix.main.IntegerSolveHandler.Companion.findIntegerSolve
import com.example.matrix.main.ModifiedMatrixHandler
import org.junit.Assert
import org.junit.Test

class ModifiedMatrixIntegerSolution {
    @Test
    fun T1() {
        println("T1")

        val inputMatrix = arrayOf(
            arrayOf(3.0, 2.0, 0.0, 10.0),
            arrayOf(1.0, 4.0, 0.0, 11.0),
            arrayOf(3.0, 3.0, 1.0, 13.0),
            arrayOf(-4.0, -5.0, -1.0, 0.0)
        )

        val correct = arrayOf(
            arrayOf(-0.18, 0.45, 0.00, 2.00),
            arrayOf(0.27, -0.18, 0.00, 2.00),
            arrayOf(-0.27, -0.82, 1.00, 1.00),
            arrayOf(-0.91, 0.27, 0.00, 1.00),
            arrayOf(-0.73, -0.18, 0.00, 0.00),
            arrayOf(0.36, 0.09, 1.00, 19.00)
        )

        val correctX = arrayOf(2.0, 2.0, 1.0)

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf("x1", "x2", "x3"),
            rows = arrayOf("y1", "y2", "y3")
        )

        val result = findIntegerSolve(matrix = inputMatrix, xy = xy)

        val resX = ModifiedMatrixHandler.findXresults(result)
        println("Output")
        println(result.result)
        result.matrix.let {
            if (it == null) return
            printArray(it)
        }

        println("Correct")
        printArray(correct)
        println("Output X")
        printArray(resX)
        println()
        println("Correct X")
        printArray(correctX)
        println()

        Assert.assertTrue(correct.contentDeepEquals(result.matrix))
        Assert.assertTrue(correctX.contentDeepEquals(resX))

    }
}