package com.example.matrix

import ArrayHelper.Companion.printArray
import com.example.matrix.main.MatrixHandler
import com.example.matrix.main.ModifiedMatrixHandler.Companion.modifiedJordanEliminationStep
import com.example.matrix.main.ModifiedMatrixHandler.Companion.searchOptimalSolve
import org.junit.Assert
import org.junit.Test

class ModifiedMatrixHandler {
    @Test
    fun inverseMatrixTest1() {
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

        val output = searchOptimalSolve(matrix = inputMatrix)

        println("Output")
        println(output.result)
        output.matrix.let {
            if (it == null) return
            printArray(it)

        }

        println("Correct")
        printArray(correct)

        Assert.assertTrue(correct.contentDeepEquals(output.matrix))
    }
}