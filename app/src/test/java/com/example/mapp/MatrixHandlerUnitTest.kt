package com.example.mapp

import ArrayHelper.Companion.printArray
import ArrayHelper.Companion.roundArray
import com.example.mapp.main.MatrixHandler.Companion.inverseMatrix
import org.junit.Assert.assertTrue
import org.junit.Test


class MatrixHandlerUnitTest {
    @Test
    fun inverseMatrixTest1() {
        println("inverseMatrixTest1")
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

        val output = inverseMatrix(input)?:return
        roundArray(output,3)

        println("Correct")
        printArray(correct)
        println()
        println("Output")
        printArray(output)
        println()

        assertTrue(correct.contentDeepEquals(output))
    }

    @Test
    fun inverseMatrixTest2() {
        println("inverseMatrixTest2")
        val input = arrayOf(
            arrayOf(6.0, 2.0, 5.0),
            arrayOf(-3.0, 4.0, -1.0),
            arrayOf(1.0, 4.0, 3.0)
        )

        val correct = arrayOf(
            arrayOf(0.5, 0.438, -0.687),
            arrayOf(0.25, 0.406, -0.281),
            arrayOf(-0.5, -0.688, 0.938)
        )

//        Correct by book
//        val correct = arrayOf(
//            arrayOf(0.5, 0.437, -0.687),
//            arrayOf(0.25, 0.406, -0.281),
//            arrayOf(-0.5, -0.687, 0.937)
//        )

        val output = inverseMatrix(input)?:return
        roundArray(output,3)

        println("Correct")
        printArray(correct)
        println()
        println("Output")
        printArray(output)
        println()

        assertTrue(correct.contentDeepEquals(output))
    }

    @Test
    fun inverseMatrixTest3() {
        println("inverseMatrixTest3")
        val input = arrayOf(
            arrayOf(2.0, -1.0, 3.0),
            arrayOf(-1.0, 2.0, 2.0),
            arrayOf(1.0, 1.0, 1.0)
        )

        val correct = arrayOf(
            arrayOf(0.0, -0.333, 0.667),
            arrayOf(-0.25, 0.083, 0.583),
            arrayOf(0.25, 0.25, -0.25)
        )

        val output = inverseMatrix(input)?:return
        roundArray(output,3)

        println("Correct")
        printArray(correct)
        println()
        println("Output")
        printArray(output)
        println()

        assertTrue(correct.contentDeepEquals(output))
    }
}
