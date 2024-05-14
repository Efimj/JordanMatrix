package com.example.Planing

import com.example.matrix.main.AssignmentProblem
import com.example.matrix.main.other.ArrayHelper
import com.example.matrix.main.planing.GridPlanningProblem
import com.example.matrix.main.planing.GridPlanningProblem.Companion.findOptimalSolution
import com.example.matrix.main.planing.Task
import org.junit.Assert
import org.junit.Test

class GridPlanningProblemTests {
    @Test
    fun gridPlanningProblemTest1() {
        println()
        println("________ Test 1 _________")

        val input = listOf(
            Task(taskId = 1, previous = listOf(), duration = 5, resources = 2),
            Task(taskId = 2, previous = listOf(1), duration = 8, resources = 3),
            Task(taskId = 3, previous = listOf(1), duration = 3, resources = 2),
            Task(taskId = 4, previous = listOf(1), duration = 6, resources = 2),
            Task(taskId = 5, previous = listOf(2), duration = 7, resources = 3),
            Task(taskId = 6, previous = listOf(2, 3), duration = 6, resources = 2),
            Task(taskId = 7, previous = listOf(4, 5, 6), duration = 4, resources = 2),
        )

        findOptimalSolution(input)
    }

    @Test
    fun gridPlanningProblemTest2() {
        println()
        println("________ Test 2 _________")

        val input = listOf(
            Task(taskId = 1, previous = listOf(), duration = 3, resources = 2),
            Task(taskId = 2, previous = listOf(1), duration = 4, resources = 3),
            Task(taskId = 3, previous = listOf(1), duration = 2, resources = 4),
            Task(taskId = 4, previous = listOf(2), duration = 5, resources = 3),
            Task(taskId = 5, previous = listOf(3), duration = 1, resources = 2),
            Task(taskId = 6, previous = listOf(3), duration = 2, resources = 3),
            Task(taskId = 7, previous = listOf(4, 5), duration = 4, resources = 2),
            Task(taskId = 8, previous = listOf(6, 7), duration = 3, resources = 2),
            )

        val resultTasks = findOptimalSolution(input)

        GridPlanningProblem().printTaskList(resultTasks)
    }
}