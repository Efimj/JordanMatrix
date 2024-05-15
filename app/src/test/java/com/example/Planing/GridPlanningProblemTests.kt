package com.example.Planing

import com.example.matrix.main.AssignmentProblem
import com.example.matrix.main.other.ArrayHelper
import com.example.matrix.main.planing.GridPlanningProblem
import com.example.matrix.main.planing.GridPlanningProblem.Companion.findOptimalSolution
import com.example.matrix.main.planing.Task
import org.junit.Assert
import org.junit.Test
import kotlin.math.abs

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

        val resultTasks = findOptimalSolution(input)

        GridPlanningProblem().printTaskList(resultTasks)

        val expectedChain = listOf(1, 2, 5, 7)
        val expectedProjectDuration = 24

        val outputCriticalChain = GridPlanningProblem().getCriticalTaskChain(resultTasks).map { it.taskId }
        val projectDuration = GridPlanningProblem().findProjectDuration(resultTasks)

        Assert.assertTrue(expectedChain == outputCriticalChain)
        Assert.assertTrue(expectedProjectDuration == projectDuration)
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

        val expectedChain = listOf(1, 2, 4, 7, 8)
        val expectedProjectDuration = 19

        val outputCriticalChain = GridPlanningProblem().getCriticalTaskChain(resultTasks).map { it.taskId }
        val projectDuration = GridPlanningProblem().findProjectDuration(resultTasks)

        Assert.assertTrue(expectedChain == outputCriticalChain)
        Assert.assertTrue(expectedProjectDuration == projectDuration)
    }

    @Test
    fun gridPlanningProblemTest3() {
        println()
        println("________ Test Variant _________")

        val input = listOf(
            Task(taskId = 1, previous = listOf(), duration = 8, resources = 3),
            Task(taskId = 2, previous = listOf(), duration = 6, resources = 2),
            Task(taskId = 3, previous = listOf(1), duration = 13, resources = 5),
            Task(taskId = 4, previous = listOf(1), duration = 4, resources = 1),
            Task(taskId = 5, previous = listOf(4), duration = 5, resources = 2),
            Task(taskId = 6, previous = listOf(2), duration = 10, resources = 4),
            Task(taskId = 7, previous = listOf(2), duration = 6, resources = 1),
            Task(taskId = 8, previous = listOf(7), duration = 9, resources = 2),
            Task(taskId = 9, previous = listOf(5, 6, 8), duration = 10, resources = 3),
            Task(taskId = 10, previous = listOf(3, 4, 9), duration = 7, resources = 3),
            Task(taskId = 11, previous = listOf(5, 6, 8), duration = 11, resources = 5),
        )

        val resultTasks = findOptimalSolution(input)

        GridPlanningProblem().printTaskList(resultTasks)

        val expectedChain = listOf(1, 2, 4, 7, 8)
        val expectedProjectDuration = 19

        val outputCriticalChain = GridPlanningProblem().getCriticalTaskChain(resultTasks).map { it.taskId }
        val projectDuration = GridPlanningProblem().findProjectDuration(resultTasks)

        Assert.assertTrue(expectedChain == outputCriticalChain)
        Assert.assertTrue(expectedProjectDuration == projectDuration)
    }
}