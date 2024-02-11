package com.example.matrix

import ArrayHelper.Companion.printArray
import ArrayHelper.Companion.roundArray
import com.example.matrix.main.MatrixHandler.Companion.getMatrixRank
import com.example.matrix.main.MatrixHandler.Companion.inverseMatrix
import com.example.matrix.main.MatrixHandler.Companion.solveLinearSystem
import org.junit.Assert.assertEquals
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

        var protocol = ""

        val output = inverseMatrix(input) { protocol = it } ?: return
        roundArray(output, 3)

        makeReportToInverse(input, output, correct, protocol)

        assertTrue(correct.contentDeepEquals(output))
    }

    private fun makeReportToInverse(
        input: Array<Array<Double>>,
        output: Array<Array<Double>>,
        correct: Array<Array<Double>>,
        protocol: String
    ) {
        println("Input")
        printArray(input)
        println()
        println("Output")
        printArray(output)
        println()
        println("Correct")
        printArray(correct)
        println()
        println("Protocol")
        println(protocol)
        println()
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

        var protocol = ""

        val output = inverseMatrix(input) { protocol = it } ?: return
        roundArray(output, 3)

        makeReportToInverse(input, output, correct, protocol)

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

        var protocol = ""

        val output = inverseMatrix(input) { protocol = it } ?: return
        roundArray(output, 3)

        makeReportToInverse(input, output, correct, protocol)

        assertTrue(correct.contentDeepEquals(output))
    }

    @Test
    fun getMatrixRankTest1() {
        println("getMatrixRank1")
        val input = arrayOf(
            arrayOf(1.0, 2.0, 3.0, 4.0),
            arrayOf(2.0, 4.0, 6.0, 8.0),
        )

        val correct = 1

        val output = input.getMatrixRank()

        makeReportToRank(input, output, correct)

        assertEquals(correct, output)
    }

    private fun makeReportToRank(input: Array<Array<Double>>, output: Int, correct: Int) {
        println("Input")
        printArray(input)
        println()
        println("Output")
        println(output)
        println()
        println("Correct")
        println(correct)
        println()
    }

    @Test
    fun getMatrixRankTest2() {
        println("getMatrixRank2")
        val input = arrayOf(
            arrayOf(2.0, 5.0, 4.0),
            arrayOf(-3.0, 1.0, -2.0),
            arrayOf(-1.0, 6.0, 2.0),
        )

        val correct = 2

        val output = input.getMatrixRank()

        makeReportToRank(input, output, correct)

        assertEquals(correct, output)
    }

    @Test
    fun getMatrixRankTest3() {
        println("getMatrixRank3")
        val input = arrayOf(
            arrayOf(1.0, 2.0),
            arrayOf(3.0, 6.0),
            arrayOf(5.0, 10.0),
            arrayOf(4.0, 8.0),
        )

        val correct = 1

        val output = input.getMatrixRank()

        makeReportToRank(input, output, correct)

        assertEquals(correct, output)
    }

    @Test
    fun getMatrixRankTest4() {
        println("getMatrixRank4")
        val input = arrayOf(
            arrayOf(6.0, 2.0, 5.0),
            arrayOf(-3.0, 4.0, -1.0),
            arrayOf(1.0, 4.0, 3.0),
        )

        val correct = 3

        val output = input.getMatrixRank()

        makeReportToRank(input, output, correct)

        assertEquals(correct, output)
    }

    @Test
    fun getMatrixRankTest5() {
        println("getMatrixRank5")
        val input = arrayOf(
            arrayOf(-1.0, 5.0, 4.0),
            arrayOf(-2.0, 7.0, 5.0),
            arrayOf(3.0, 4.0, 1.0),
        )

        val correct = 3

        val output = input.getMatrixRank()

        makeReportToRank(input, output, correct)

        assertEquals(correct, output)
    }

    @Test
    fun getMatrixRankTest6() {
        println("getMatrixRank6")
        val input = arrayOf(
            arrayOf(1.0, 2.0, 3.0, 4.0),
            arrayOf(-2.0, 5.0, -1.0, 3.0),
            arrayOf(2.0, 4.0, 6.0, 8.0),
            arrayOf(-1.0, 7.0, 2.0, 7.0),
        )

        val correct = 2

        val output = input.getMatrixRank()

        makeReportToRank(input, output, correct)

        assertEquals(correct, output)
    }

    @Test
    fun solveLinearSystemTest1() {
        println("solveLinearSystemTest1")
        val input = arrayOf(
            arrayOf(5.0, -3.0, 7.0),
            arrayOf(-1.0, 4.0, 3.0),
            arrayOf(6.0, -2.0, 5.0)
        )

        val constants = arrayOf(13.0, 13.0, 12.0)
        val correct = arrayOf(1.0, 2.0, 2.0)

        var protocol = ""

        val output = solveLinearSystem(input, constants) { protocol = it } ?: return
        roundArray(output, 3)

        makeReportToSolve(input, output, correct, protocol)

        assertTrue(correct.contentDeepEquals(output))
    }

    private fun makeReportToSolve(
        input: Array<Array<Double>>,
        output: Array<Double>,
        correct: Array<Double>,
        protocol: String
    ) {
        println("Input")
        printArray(input)
        println()
        println("Output")
        printArray(output)
        println()
        println("Correct")
        printArray(correct)
        println()
        println("Protocol")
        println(protocol)
        println()
    }

    @Test
    fun solveLinearSystemTest2() {
        println("solveLinearSystemTest2")
        val input = arrayOf(
            arrayOf(6.0, 2.0, 5.0),
            arrayOf(-3.0, 4.0, -1.0),
            arrayOf(1.0, 4.0, 3.0)
        )

        val constants = arrayOf(1.0, 6.0, 6.0)
        val correct = arrayOf(-1.0, 1.0, 1.0)

        var protocol = ""

        val output = solveLinearSystem(input, constants) { protocol = it } ?: return
        roundArray(output, 3)

        makeReportToSolve(input, output, correct, protocol)

        assertTrue(correct.contentDeepEquals(output))
    }

    @Test
    fun variantTest() {
        println("variantTest")
        val input = arrayOf(
            arrayOf(2.0, -1.0, 3.0),
            arrayOf(1.0, 3.0, -2.0),
            arrayOf(3.0, 1.0, -1.0)
        )

        val constants = arrayOf(4.0, 2.0, 3.0)
        val correct = arrayOf(1.0, 1.0, 1.0)

        var protocol = ""

        val output = solveLinearSystem(input, constants) { protocol = it } ?: return
        roundArray(output, 3)

        makeReportToSolve(input, output, correct, protocol)

        assertTrue(correct.contentDeepEquals(output))
    }
}
