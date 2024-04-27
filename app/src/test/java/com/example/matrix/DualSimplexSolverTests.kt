package com.example.matrix

import com.example.matrix.main.other.ArrayHelper.Companion.printArray
import com.example.matrix.main.DualSimplexSolver.Companion.findDualResultsFor
import com.example.matrix.main.DualSimplexSolver.Companion.findResultsFor
import com.example.matrix.main.ModifiedMatrixHandler
import org.junit.Assert
import org.junit.Test

class DualSimplexSolverTests {
    @Test
    fun dualSimplexSolverTest1() {
        println("Test 1")

        val inputMatrix = arrayOf(
            arrayOf(1.0, 1.0, -1.0, -2.0, 6.0),
            arrayOf(-1.0, -1.0, -1.0, 1.0, -5.0),
            arrayOf(2.0, -1.0, 3.0, 4.0, 10.0),
            arrayOf(-1.0, -2.0, 1.0, 1.0, 0.0)
        )

        val correctReferencesX = arrayOf(5.0, 0.0, 0.0, 0.0)

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf("x1", "x2", "x3", "x4"),
            rows = arrayOf("y1", "y2", "y3")
        )

        val output = ModifiedMatrixHandler.searchReferenceSolution(matrix = inputMatrix, xy = xy)
        val referenceResult = ModifiedMatrixHandler.findXresults(output)
        println("Output")
        println(output.result)
        output.matrix.let {
            if (it == null) return
            printArray(it)
        }

        val resForDual = findDualResultsFor(output = output)

        println("X results")
        printArray(referenceResult)
        println()
        println("CorrectX")
        printArray(correctReferencesX)
        println()
        println("U results")
        printArray(resForDual)
        println()

        println()

        println()
        println("Optimal solution")

        val correctOptimalSolve = arrayOf(
            arrayOf(4.0, 2.0, 1.0, 1.0, 22.0),
            arrayOf(1.5, 0.5, 0.5, 1.0, 8.0),
            arrayOf(1.5, 1.5, 0.5, -1.0, 9.0),
            arrayOf(5.5, 3.5, 1.5, 2.0, 36.0)
        )

        val correctOptimalU = arrayOf(3.5, 0.0, 1.5)

        val optimalSolveResult =
            ModifiedMatrixHandler.searchOptimalSolveMaximum(matrix = output.matrix!!, xy = output.xyPos)
        val optimalSolveResultX = ModifiedMatrixHandler.findXresults(optimalSolveResult)
        val optimalSolveResultU = findDualResultsFor(output = optimalSolveResult)

        println("Output")
        println(optimalSolveResult.result)
        optimalSolveResult.matrix.let {
            if (it == null) return
            printArray(it)
        }

        println("X results")
        printArray(optimalSolveResultX)
        println()
        println("U results")
        printArray(optimalSolveResultU)
        println()
        println("minimum: -${correctOptimalSolve.last().last()}")
        println()

        Assert.assertTrue(correctOptimalSolve.contentDeepEquals(optimalSolveResult.matrix))
        Assert.assertTrue(correctOptimalU.contentDeepEquals(optimalSolveResultU))
    }

    @Test
    fun dualSimplexSolverTest2() {
        println("Test 2")
        val inputMatrix = arrayOf(
            arrayOf(-3.0, 1.0, 4.0, 1.0, 1.0),
            arrayOf(3.0, -2.0, 2.0, -2.0, -9.0),
            arrayOf(-2.0, 1.0, 1.0, 3.0, 2.0),
            arrayOf(-3.0, 2.0, -3.0, 0.0, 7.0),
            arrayOf(-10.0, 1.0, 42.0, 52.0, 0.0)
        )

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf("x1", "x2", "x3", "x4"),
            rows = arrayOf("y1", "y2", "0", "0")
        )

        val resultAfterRemovedNullLines = ModifiedMatrixHandler.removeNullLines(inputMatrix, xy)

        val correctReferenceSolve = arrayOf(
            arrayOf(-9.0, 9.0, 26.0),
            arrayOf(-1.0, 2.0, 2.0),
            arrayOf(-5.0, 4.0, 13.0),
            arrayOf(-2.0, -4.0, 6.0),
            arrayOf(1.0, -1.0, 20.0)
        )

        val referenceSolve = ModifiedMatrixHandler.searchReferenceSolution(
            matrix = resultAfterRemovedNullLines.matrix!!,
            xy = resultAfterRemovedNullLines.xyPos
        )
        println("Output")
        println(referenceSolve.result)
        referenceSolve.matrix.let {
            if (it == null) return
            printArray(it)
        }

        Assert.assertTrue(correctReferenceSolve.contentDeepEquals(referenceSolve.matrix))

        println()
        println("Optimal solution")

        val correctOptimalSolve = arrayOf(
            arrayOf(-4.5, -4.5, 17.0),
            arrayOf(-0.5, 0.5, 1.0),
            arrayOf(-3.0, -2.0, 9.0),
            arrayOf(-4.0, 2.0, 10.0),
            arrayOf(0.5, 0.5, 21.0)
        )

        val correctOptimalU = arrayOf(0.0, 0.0, 0.5, 0.0)

        val optimalSolveResult =
            ModifiedMatrixHandler.searchOptimalSolveMaximum(matrix = referenceSolve.matrix!!, xy = referenceSolve.xyPos)
        val optimalSolveResultU = findDualResultsFor(name = "x", output = optimalSolveResult)

        println("Output")
        println(optimalSolveResult.result)
        optimalSolveResult.matrix.let {
            if (it == null) return
            printArray(it)
        }

        println("Correct")
        printArray(correctOptimalSolve)
        println()
        println("V results")
        printArray(optimalSolveResultU)
        println()
        println("minimum: -${correctOptimalSolve.last().last()}")
        println()

        printArray(optimalSolveResult.xyPos.rows)
        println()
        printArray(optimalSolveResult.xyPos.cols)

        Assert.assertTrue(correctOptimalSolve.contentDeepEquals(optimalSolveResult.matrix))
        Assert.assertTrue(correctOptimalU.contentDeepEquals(optimalSolveResultU))
    }

    @Test
    fun dualSimplexSolverTestVariant1() {
        println("Test 3")

        val inputMatrix = arrayOf(
            arrayOf(2.0, 1.0, 3.0),
            arrayOf(1.0, 2.0, 5.0),
            arrayOf(-1.0, -1.0, 0.0),
        )

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf("x1", "x2"),
            rows = arrayOf("0", "y1")
        )

        val resultAfterRemovedNullLines = ModifiedMatrixHandler.removeNullLines(inputMatrix, xy)

        val referenceSolve = ModifiedMatrixHandler.searchReferenceSolution(
            matrix = resultAfterRemovedNullLines.matrix!!,
            xy = resultAfterRemovedNullLines.xyPos
        )

        val optimalSolveResult =
            ModifiedMatrixHandler.searchOptimalSolveMaximum(matrix = referenceSolve.matrix!!, xy = referenceSolve.xyPos)
        println("Output")
        println(optimalSolveResult.result)
        optimalSolveResult.matrix.let {
            if (it == null) return
            printArray(it)
        }
        val optimalSolveResultX = findResultsFor(optimalSolveResult)
        val optimalSolveResultU = findDualResultsFor(name = "y", output = optimalSolveResult)

        println()
        println("X results")
        printArray(optimalSolveResultX)
        println()
        println("U results")
        printArray(optimalSolveResultU)
        println()

        println()
        println("maximum: ${optimalSolveResult.matrix!!.last().last()}")
        println()
        println("minimum: -${optimalSolveResult.matrix!!.last().last()}")
        println()
    }

    @Test
    fun dualSimplexSolverTestVariant11() {
        println("dualSimplexSolverTestVariant11")

        val inputMatrix = arrayOf(
            arrayOf(1.0, -1.0, 1.0, 1.0, 2.0),
            arrayOf(1.0, -1.0, 1.0, -1.0, 2.0),
            arrayOf(-1.0, -1.0, 1.0, 1.0, -2.0),
            arrayOf(1.0, -1.0, -1.0, 1.0, 2.0),
            arrayOf(-1.0, 8.0, -1.0, -4.0, 0.0)
        )

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf("x1", "x2", "x3", "x4", "x5"),
            rows = arrayOf("y1", "y2", "y3", "y4", "y5")
        )

        val referenceSolve = ModifiedMatrixHandler.searchReferenceSolution(
            matrix = inputMatrix,
            xy = xy
        )

        val optimalSolveResult =
            ModifiedMatrixHandler.searchOptimalSolveMaximum(matrix = referenceSolve.matrix!!, xy = referenceSolve.xyPos)
        println("Output")
        println(optimalSolveResult.result)
        optimalSolveResult.matrix.let {
            if (it == null) return
            printArray(it)
        }
        val optimalSolveResultX = findResultsFor(optimalSolveResult)
        val optimalSolveResultU = findDualResultsFor(name = "y", output = optimalSolveResult)

        println()
        println("X results")
        printArray(optimalSolveResultX)
        println()
        println("U results")
        printArray(optimalSolveResultU)
        println()

        println()
        println("maximum: ${optimalSolveResult.matrix!!.last().last()}")
        println()
        println("minimum: -${optimalSolveResult.matrix!!.last().last()}")
        println()
    }
}