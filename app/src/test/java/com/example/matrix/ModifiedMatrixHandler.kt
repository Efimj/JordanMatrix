package com.example.matrix

import ArrayHelper.Companion.printArray
import com.example.matrix.main.ModifiedMatrixHandler
import com.example.matrix.main.ModifiedMatrixHandler.Companion.findXresults
import com.example.matrix.main.ModifiedMatrixHandler.Companion.searchOptimalSolve
import org.junit.Assert
import org.junit.Test

class ModifiedMatrixHandler {
    @Test
    fun findOptimalSolveT1() {
        println("inverseMatrixTest1")

        val inputMatrix = arrayOf(
            arrayOf(1.0, 0.0, -2.0, -1.0, 1.0),
            arrayOf(-1.0, 1.0, 1.0, -1.0, 5.0),
            arrayOf(2.0, -3.0, 1.0, 6.0, 0.0),
            arrayOf(-1.0, -1.0, 2.0, 0.0, 5.0)
        )

        val correct = arrayOf(
            arrayOf(4.0, 2.0, 1.0, 1.0, 22.0),
            arrayOf(1.5, 0.5, 0.5, 1.0, 8.0),
            arrayOf(1.5, 1.5, 0.5, -1.0, 9.0),
            arrayOf(5.5, 3.5, 1.5, 2.0, 36.0),
        )
        val correctX = arrayOf(0.00, 22.00, 0.00, 8.00)

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf("y2", "x2", "x3", "x4"),
            rows = arrayOf("y1", "x1", "y3")
        )

        val output = searchOptimalSolve(matrix = inputMatrix, xy = xy)
        val res = findXresults(output)
        println("Output")
        println(output.result)
        output.matrix.let {
            if (it == null) return
            printArray(it)
        }

        println("Correct")
        printArray(correct)
        println("XPoses")
        printArray(res)
        println()
        println("CorrectX")
        printArray(correctX)

        Assert.assertTrue(correct.contentDeepEquals(output.matrix))
        Assert.assertTrue(correctX.contentDeepEquals(res))
    }
}