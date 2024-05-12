package com.example.AssignmentProblem

import com.example.matrix.main.AssignmentProblem
import com.example.matrix.main.TransportProblemSolver
import com.example.matrix.main.other.ArrayHelper
import org.junit.Assert
import org.junit.Test

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
}