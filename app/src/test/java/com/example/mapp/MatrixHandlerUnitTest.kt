package com.example.mapp

import com.example.mapp.main.MatrixHandler.Companion.inverseMatrix
import org.junit.Assert.assertTrue
import org.junit.Test
import printArray


class MatrixHandlerUnitTest {
    @Test
    fun inverseMatrixTest1() {
        val input = arrayOf(
            arrayOf(5.0, -3.0, 7.0),
            arrayOf(-1.0, 4.0, 3.0),
            arrayOf(6.0, -2.0, 5.0)
        )

        val correct = arrayOf(
            arrayOf(-0.28, -0.011, 0.398),
            arrayOf(-0.247, 0.183, 0.237),
            arrayOf(0.237, 0.086, -0.183)
        )

        val output = inverseMatrix(input) ?: return

        println("Correct")
        printArray(correct)
        println()
        println("Output")
        printArray(output)

        assertTrue(correct.contentDeepEquals(output))
    }
}
