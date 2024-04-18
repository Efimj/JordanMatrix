package com.example.matrixGame

import ArrayHelper.Companion.printArray
import com.example.matrix.main.MatrixGamesSolver.Companion.solveMatrixGame
import com.example.matrix.main.ModifiedMatrixHandler.Companion.XYPositions
import org.junit.Assert
import org.junit.Test

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

        Assert.assertTrue(correctFirstPlayerSolution.contentDeepEquals(solution.firstPlayersSolution))
        Assert.assertTrue(correctSecondPlayerSolution.contentDeepEquals(solution.secondPlayersSolution))
    }
}