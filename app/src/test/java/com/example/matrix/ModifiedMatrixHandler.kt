package com.example.matrix

import ArrayHelper.Companion.printArray
import com.example.matrix.main.ModifiedMatrixHandler
import com.example.matrix.main.ModifiedMatrixHandler.Companion.findXresults
import com.example.matrix.main.ModifiedMatrixHandler.Companion.searchOptimalSolveMaximum
import com.example.matrix.main.ModifiedMatrixHandler.Companion.searchReferenceSolution
import org.junit.Assert
import org.junit.Test

class ModifiedMatrixHandler {
    @Test
    fun findReferenceSolutionT1() {
        println("findReferenceSolutionT1")

        val inputMatrix = arrayOf(
            arrayOf(1.0, 1.0, -1.0, -2.0, 6.0),
            arrayOf(-1.0, -1.0, -1.0, 1.0, -5.0),
            arrayOf(2.0, -1.0, 3.0, 4.0, 10.0),
            arrayOf(-1.0, -2.0, 1.0, 1.0, 0.0)
        )

        val correct = arrayOf(
            arrayOf(1.0, 0.0, -2.0, -1.0, 1.0),
            arrayOf(-1.0, 1.0, 1.0, -1.0, 5.0),
            arrayOf(2.0, -3.0, 1.0, 6.0, 0.0),
            arrayOf(-1.0, -1.0, 2.0, 0.0, 5.0),
        )
        val correctX = arrayOf(5.0, 0.0, 0.0, 0.0)

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf("x1", "x2", "x3", "x4"),
            rows = arrayOf("y1", "y2", "y3")
        )

        val output = searchReferenceSolution(matrix = inputMatrix, xy = xy)
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
        println()

        Assert.assertTrue(correct.contentDeepEquals(output.matrix))
        Assert.assertTrue(correctX.contentDeepEquals(res))
    }

    @Test
    fun findReferenceSolutionT2() {
        println("findReferenceSolutionT2")

        val inputMatrix = arrayOf(
            arrayOf(1.0, 1.0, -1.0, -2.0, 6.0),
            arrayOf(-1.0, -1.0, -1.0, 1.0, -5.0),
            arrayOf(2.0, -1.0, 3.0, 4.0, 10.0),
            arrayOf(-2.0, 3.0, 0.0, -3.0, 0.0)
        )

        val correct = arrayOf(
            arrayOf(1.0, 0.0, -2.0, -1.0, 1.0),
            arrayOf(-1.0, 1.0, 1.0, -1.0, 5.0),
            arrayOf(2.0, -3.0, 1.0, 6.0, 0.0),
            arrayOf(-2.0, 5.0, 2.0, -5.0, 10.0),
        )
        val correctX = arrayOf(5.0, 0.0, 0.0, 0.0)

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf("x1", "x2", "x3", "x4"),
            rows = arrayOf("y1", "y2", "y3")
        )

        val output = searchReferenceSolution(matrix = inputMatrix, xy = xy)
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
        println()

        Assert.assertTrue(correct.contentDeepEquals(output.matrix))
        Assert.assertTrue(correctX.contentDeepEquals(res))
    }

    @Test
    fun findOptimalSolveT1() {
        println("findOptimalSolveT1")

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

        val output = searchOptimalSolveMaximum(matrix = inputMatrix, xy = xy)
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
        println()
        println("Maximum: ${correct.last().last()}")
        println()

        Assert.assertTrue(correct.contentDeepEquals(output.matrix))
        Assert.assertTrue(correctX.contentDeepEquals(res))
    }

    @Test
    fun findOptimalSolveT2() {
        println("findOptimalSolveT2")

        val inputMatrix = arrayOf(
            arrayOf(1.0, 0.0, -2.0, -1.0, 1.0),
            arrayOf(-1.0, 1.0, 1.0, -1.0, 5.0),
            arrayOf(2.0, -3.0, 1.0, 6.0, 0.0),
            arrayOf(-2.0, 5.0, 2.0, -5.0, 10.0)
        )

        val correct = arrayOf(
            arrayOf(-0.5, 1.5, -2.5, -4.0, 1.0),
            arrayOf(0.5, -0.5, 1.5, 2.0, 5.0),
            arrayOf(0.5, -1.5, 0.5, 3.0, 0.0),
            arrayOf(1.0, 2.0, 3.0, 1.0, 10.0),
        )
        val correctX = arrayOf(5.0, 0.00, 0.00, 0.00)

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf("y2", "x2", "x3", "x4"),
            rows = arrayOf("y1", "x1", "y3")
        )

        val output = searchOptimalSolveMaximum(matrix = inputMatrix, xy = xy)
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
        println()
        println("minimum: -${correct.last().last()}")
        println()

        Assert.assertTrue(correct.contentDeepEquals(output.matrix))
        Assert.assertTrue(correctX.contentDeepEquals(res))
    }

    @Test
    fun testV1() {
        println("findReferenceSolutionV1")

        val inputMatrix = arrayOf(
            arrayOf(1.0, -1.0, 1.0, 1.0, 2.0),
            arrayOf(1.0, -1.0, 1.0, -1.0, 2.0),
            arrayOf(-1.0, -1.0, 1.0, 1.0, -2.0),
            arrayOf(1.0, -1.0, -1.0, 1.0, 2.0),
            arrayOf(-1.0, 8.0, -1.0, -4.0, 0.0)
        )

        val correct = arrayOf(
            arrayOf(-1.0, 0.0, 0.0, 2.0, 0.0),
            arrayOf(1.0, -1.0, 1.0, -1.0, 2.0),
            arrayOf(1.0, -2.0, 2.0, 0.0, 0.0),
            arrayOf(1.0, 7.0, 0.0, -5.0, 2.0),
        )
        val correctX = arrayOf(2.0, 0.0, 0.0, 0.0)

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf("x1", "x2", "x3", "x4"),
            rows = arrayOf("y1", "y2", "y3")
        )

        val output = searchReferenceSolution(matrix = inputMatrix, xy = xy)
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
        println()

        Assert.assertTrue(correct.contentDeepEquals(output.matrix))
        Assert.assertTrue(correctX.contentDeepEquals(res))

        println()
        println("Optimal solution")

        val correct2 = arrayOf(
            arrayOf(0.5, -1.0, 1.0, 0.5, 0.0),
            arrayOf(-0.5, 0.0, 0.0, 0.5, 2.0),
            arrayOf(1.0, -2.0, 2.0, 0.0, 0.0),
            arrayOf(1.5, 4.0, 3.0, 2.5, 2.0),
        )
        val correctX2 = arrayOf(2.0, 0.0, 0.0, 0.0)

        val output2 = searchOptimalSolveMaximum(matrix = output.matrix!!, xy = output.xyPos)
        val res2 = findXresults(output2)
        println("Output")
        println(output2.result)
        output2.matrix.let {
            if (it == null) return
            printArray(it)
        }

        println("Correct")
        printArray(correct2)
        println("XPoses")
        printArray(res2)
        println()
        println("CorrectX")
        printArray(correctX2)
        println()
        println("minimum: -${correct2.last().last()}")
        println()

        Assert.assertTrue(correct2.contentDeepEquals(output2.matrix))
        Assert.assertTrue(correctX2.contentDeepEquals(res2))

    }
}