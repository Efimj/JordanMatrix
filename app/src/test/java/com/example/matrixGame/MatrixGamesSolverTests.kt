package com.example.matrixGame

import ArrayHelper.Companion.printArray
import ArrayHelper.Companion.roundArray
import com.example.matrix.main.MatrixGamesSolver.Companion.solveMatrixGame
import com.example.matrix.main.ModifiedMatrixHandler.Companion.XYPositions
import org.junit.Assert
import org.junit.Test
import kotlin.math.roundToInt

class MatrixGamesSolverTests {
    @Test
    fun matrixGameSolutionTest1() {
        println()
        println("matrixGameSolutionTest1")

        val inputMatrix = arrayOf(
            arrayOf(5.0, 2.0, 3.0, 7.0),
            arrayOf(3.0, 1.0, 4.0, 2.0),
            arrayOf(4.0, 3.0, 5.0, 6.0)
        )

        val inputXYPositions = XYPositions(
            cols = arrayOf("x1", "x2", "x3", "x4"),
            rows = arrayOf("y1", "y2", "y3")
        )

        val correctFirstPlayerSolution = arrayOf(4.0, 3.0, 5.0, 6.0)
        val correctSecondPlayerSolution = arrayOf(2.0, 1.0, 3.0)

        val solution = solveMatrixGame(matrix = inputMatrix, xyPositions = inputXYPositions)

        println()
        println("First player solve")
        printArray(solution.firstPlayersSolution)
        println()
        println("Second player solve")
        printArray(solution.secondPlayersSolution)
        println()

        Assert.assertTrue(correctFirstPlayerSolution.contentDeepEquals(solution.firstPlayersSolution))
        Assert.assertTrue(correctSecondPlayerSolution.contentDeepEquals(solution.secondPlayersSolution))
    }

    @Test
    fun matrixGameSolutionTest2() {
        println()
        println("matrixGameSolutionTest2")

        val inputMatrix = arrayOf(
            arrayOf(2.0, -1.0, 3.0, 3.0),
            arrayOf(-1.0, 2.0, 2.0, 7.0),
            arrayOf(1.0, 1.0, 1.0, 2.0)
        )

        val inputXYPositions = XYPositions(
            cols = arrayOf("x1", "x2", "x3", "x4"),
            rows = arrayOf("y1", "y2", "y3")
        )

        val correctFirstPlayerSolution = arrayOf(0.0, 0.0, 1.0)
        val correctSecondPlayerSolution = arrayOf(0.66, 0.34, 0.0)

        val solution = solveMatrixGame(matrix = inputMatrix, xyPositions = inputXYPositions)

        println()
        println("Output")
        printArray(solution.outputMatrix)
        println()
        println("First player solve")
        printArray(solution.firstPlayersSolution)
        println()
        println("Second player solve")
        printArray(solution.secondPlayersSolution)
        println()
        println("Game price")
        println(solution.gamePrice)
        println()

        Assert.assertTrue(correctFirstPlayerSolution.contentDeepEquals(solution.firstPlayersSolution))
        Assert.assertTrue(correctSecondPlayerSolution.contentDeepEquals(solution.secondPlayersSolution))
    }

    @Test
    fun matrixGameSolutionTest3() {
        println()
        println("matrixGameSolutionTest3")

        val inputMatrix = arrayOf(
            arrayOf(0.0, 1.0, -1.0),
            arrayOf(-1.0, 0.0, 1.0),
            arrayOf(1.0, -1.0, 0.0)
        )

        val inputXYPositions = XYPositions(
            cols = arrayOf("x1", "x2", "x3"),
            rows = arrayOf("y1", "y2", "y3")
        )

        val correctFirstPlayerSolution = arrayOf(0.33, 0.33, 0.33)
        val correctSecondPlayerSolution = arrayOf(0.33, 0.33, 0.33)

        val solution = solveMatrixGame(matrix = inputMatrix, xyPositions = inputXYPositions)

        println()
        println("Output")
        printArray(solution.outputMatrix)
        println()
        println("First player solve")
        printArray(solution.firstPlayersSolution)
        println()
        println("Second player solve")
        printArray(solution.secondPlayersSolution)
        println()
        println("Game price")
        println(solution.gamePrice)
        println()

        Assert.assertTrue(correctFirstPlayerSolution.contentDeepEquals(solution.firstPlayersSolution))
        Assert.assertTrue(correctSecondPlayerSolution.contentDeepEquals(solution.secondPlayersSolution))
    }

    @Test
    fun matrixGameSolutionTest4() {
        println()
        println("matrixGameSolutionTest4")

        val inputMatrix = arrayOf(
            arrayOf(5.0, 2.0, 7.0),
            arrayOf(1.0, 4.0, 3.0),
            arrayOf(6.0, 1.0, 5.0)
        )

        val inputXYPositions = XYPositions(
            cols = arrayOf("x1", "x2", "x3"),
            rows = arrayOf("y1", "y2", "y3")
        )

        val correctFirstPlayerSolution = arrayOf(0.52, 0.52, 0.0)
        val correctSecondPlayerSolution = arrayOf(0.33, 0.67, 0.0)

        val solution = solveMatrixGame(matrix = inputMatrix, xyPositions = inputXYPositions)

        roundArray(solution.firstPlayersSolution, 2)
        roundArray(solution.secondPlayersSolution, 2)

        println()
        println("Output")
        printArray(solution.outputMatrix)
        println()
        println("First player solve")
        printArray(solution.firstPlayersSolution)
        println()
        println("Second player solve")
        printArray(solution.secondPlayersSolution)
        println()
        println("Game price")
        println(solution.gamePrice.roundToInt())
        println()

        Assert.assertTrue(correctFirstPlayerSolution.contentDeepEquals(solution.firstPlayersSolution))
        Assert.assertTrue(correctSecondPlayerSolution.contentDeepEquals(solution.secondPlayersSolution))
    }

    @Test
    fun matrixGameSolutionTest5() {
        println()
        println("matrixGameSolutionTest5")

        val inputMatrix = arrayOf(
            arrayOf(2.0, -1.0, 3.0, 3.0),
            arrayOf(-1.0, 2.0, 2.0, 7.0),
            arrayOf(1.0, 1.0, 1.0, 2.0)
        )

        val inputXYPositions = XYPositions(
            cols = arrayOf("x1", "x2", "x3", "x4"),
            rows = arrayOf("y1", "y2", "y3")
        )

        val correctFirstPlayerSolution = arrayOf(0.0, 0.0, 1.0)
        val correctSecondPlayerSolution = arrayOf(0.66, 0.34, 0.0)

        val solution = solveMatrixGame(matrix = inputMatrix, xyPositions = inputXYPositions)

        roundArray(solution.firstPlayersSolution, 2)
        roundArray(solution.secondPlayersSolution, 2)

        println()
        println("Output")
        printArray(solution.outputMatrix)
        println()
        println("First player solve")
        printArray(solution.firstPlayersSolution)
        println()
        println("Second player solve")
        printArray(solution.secondPlayersSolution)
        println()
        println("Game price")
        println(solution.gamePrice.roundToInt())
        println()

        Assert.assertTrue(correctFirstPlayerSolution.contentDeepEquals(solution.firstPlayersSolution))
        Assert.assertTrue(correctSecondPlayerSolution.contentDeepEquals(solution.secondPlayersSolution))
    }

    @Test
    fun matrixGameSolutionTest6() {
        println()
        println("matrixGameSolutionTest6 variant 1")

        val inputMatrix = arrayOf(
            arrayOf(3.0, 1.0, 1.0),
            arrayOf(2.0, -2.0, 1.0),
            arrayOf(-1.0, -3.0, -2.0)
        )

        val inputXYPositions = XYPositions(
            cols = arrayOf("x1", "x2", "x3"),
            rows = arrayOf("y1", "y2", "y3")
        )

        val correctFirstPlayerSolution = arrayOf(3.0, 1.0, 1.0)
        val correctSecondPlayerSolution = arrayOf(1.0, -2.0, -3.0)

        val solution = solveMatrixGame(matrix = inputMatrix, xyPositions = inputXYPositions)

        roundArray(solution.firstPlayersSolution, 2)
        roundArray(solution.secondPlayersSolution, 2)

        println()
        println("Output")
        printArray(solution.outputMatrix)
        println()
        println("First player solve")
        printArray(solution.firstPlayersSolution)
        println()
        println("Second player solve")
        printArray(solution.secondPlayersSolution)
        println()
        println("Game price")
        println(solution.gamePrice.roundToInt())
        println()

        Assert.assertTrue(correctFirstPlayerSolution.contentDeepEquals(solution.firstPlayersSolution))
        Assert.assertTrue(correctSecondPlayerSolution.contentDeepEquals(solution.secondPlayersSolution))
    }
}