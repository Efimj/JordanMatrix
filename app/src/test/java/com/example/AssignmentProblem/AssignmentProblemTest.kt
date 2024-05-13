package com.example.AssignmentProblem

import com.example.matrix.main.AssignmentProblem
import com.example.matrix.main.ModifiedMatrixHandler
import com.example.matrix.main.TransportProblemSolver
import com.example.matrix.main.other.ArrayHelper
import org.junit.Assert
import org.junit.Test
import kotlin.math.abs

class AssignmentProblemTest {
    @Test
    fun assignmentProblemTest1() {
        println()
        println("________ Test 1 _________")

        val input = arrayOf(
            arrayOf(2, 4, 1, 3, 3),
            arrayOf(1, 5, 4, 1, 2),
            arrayOf(3, 5, 2, 2, 4),
            arrayOf(1, 4, 3, 1, 4),
            arrayOf(3, 2, 5, 3, 5)
        )

        val result = AssignmentProblem.solveAssignmentProblem(input)

        println("Assignments matrix")
        ArrayHelper.printArray(result.assignments)
        println()
        println("Cost by matrix")
        println(result.cost)

        val expectedCost = 8

        Assert.assertTrue(expectedCost == result.cost)
    }

    @Test
    fun assignmentProblemTest2() {
        println()
        println("________ Test 2 _________")

        val input = arrayOf(
            arrayOf(2, 10, 9, 7),
            arrayOf(15, 4, 14, 8),
            arrayOf(13, 14, 16, 11),
            arrayOf(4, 15, 13, 19),
        )

        val result = AssignmentProblem.solveAssignmentProblem(input)

        println("Assignments matrix")
        ArrayHelper.printArray(result.assignments)
        println()
        println("Cost by matrix")
        println(result.cost)

        val expectedCost = 28

        Assert.assertTrue(expectedCost == result.cost)
    }

    @Test
    fun assignmentProblemTest3() {
        println()
        println("________ Test Variant _________")

        val input = arrayOf(
            arrayOf(7, 10, 5, 7),
            arrayOf(8, 11, 16, 14),
            arrayOf(13, 9, 10, 5),
            arrayOf(21, 19, 15, 18),
        )

        val result = AssignmentProblem.solveAssignmentProblem(input)

        println("Assignments matrix")
        ArrayHelper.printArray(result.assignments)
        println()
        println("Cost by matrix")
        println(result.cost)

        val expectedCost = 37

        Assert.assertTrue(expectedCost == result.cost)
    }

    @Test
    fun assignmentProblemTest4() {
        println()
        println("________ Test 1 with simplex method _________")

        val input = arrayOf(
            arrayOf(1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0),
            arrayOf(0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0),
            arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0),
            arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0),
            arrayOf(-1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0),
            arrayOf(0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, -1.0),
            arrayOf(0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, -1.0),
            arrayOf(0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, -1.0),
            arrayOf(2.0, 10.0, 9.0, 7.0, 15.0, 4.0, 14.0, 8.0, 13.0, 14.0, 16.0, 11.0, 4.0, 15.0, 13.0, 19.0, 0.0)
        )

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf(
                "x1",
                "x2",
                "x3",
                "x4",
                "x5",
                "x6",
                "x7",
                "x8",
                "x9",
                "x10",
                "x11",
                "x12",
                "x13",
                "x14",
                "x15",
                "x16",
            ),
            rows = arrayOf("y1", "y2", "y3", "y4", "y5", "y6", "y7", "y8")
        )

        val resultAfterReference = ModifiedMatrixHandler.searchReferenceSolution(input, xy)

        println("___ reference ___")
        ArrayHelper.printArray(resultAfterReference.matrix!!)
        println()

        val resultAfterOptimal =
            ModifiedMatrixHandler.searchOptimalSolveMaximum(resultAfterReference.matrix!!, resultAfterReference.xyPos)

        println("___ optimal ___")
        ArrayHelper.printArray(resultAfterOptimal.matrix!!)
        println()
        println()
        println("Expected 28")
        println()

        val expectedCost = 28.0
        Assert.assertTrue(expectedCost == abs(resultAfterOptimal.matrix!!.last().last()))
    }

    @Test
    fun assignmentProblemTest5() {
        println()
        println("________ Test Variant with simplex method _________")

        val input = arrayOf(
            arrayOf(1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0),
            arrayOf(0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0),
            arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0),
            arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0),
            arrayOf(-1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0),
            arrayOf(0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, -1.0),
            arrayOf(0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, -1.0),
            arrayOf(0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, -1.0),
            arrayOf(7.0, 10.0, 5.0, 7.0, 8.0, 11.0, 16.0, 14.0, 13.0, 9.0, 10.0, 5.0, 21.0, 19.0, 15.0, 18.0, 0.0)
        )

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf(
                "x1",
                "x2",
                "x3",
                "x4",
                "x5",
                "x6",
                "x7",
                "x8",
                "x9",
                "x10",
                "x11",
                "x12",
                "x13",
                "x14",
                "x15",
                "x16",
            ),
            rows = arrayOf("y1", "y2", "y3", "y4", "y5", "y6", "y7", "y8")
        )

        val resultAfterReference = ModifiedMatrixHandler.searchReferenceSolution(input, xy)

        val resultAfterOptimal =
            ModifiedMatrixHandler.searchOptimalSolveMaximum(resultAfterReference.matrix!!, resultAfterReference.xyPos)

        println("___ optimal ___")
        ArrayHelper.printArray(resultAfterOptimal.matrix!!)
        println()
        println()
        println("Expected 37")
        println()

        val expectedCost = 37.0
        Assert.assertTrue(expectedCost == abs(resultAfterOptimal.matrix!!.last().last()))
    }

}