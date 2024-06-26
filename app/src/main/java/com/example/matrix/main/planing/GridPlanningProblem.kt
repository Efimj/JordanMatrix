package com.example.matrix.main.planing

import android.util.Range

class GridPlanningProblem {
    companion object {
        fun findOptimalSolution(tasksList: List<Task>): MutableList<Task> {
            val tasks = GridPlanningProblem().makeStartTasksList(tasksList)
            println("Constructed list of starting tasks")

            GridPlanningProblem().calculateEarlyStarts(tasks)
            println("Calculated early starts")

            GridPlanningProblem().calculateLateFinishes(tasks)
            println("Calculated late finishes")

            return tasks
        }
    }

    fun printTaskList(tasksList: List<Task>) {
        tasksList.toMutableList().sortBy { it.taskId }
        println("______ TASKS ______")
        tasksList.forEach { task ->
            println("TASK ${task.taskId}")
            println("Duration     ${task.duration}")
            println("Resources    ${task.resources}")
            println("Early start  ${task.earlyStart}")
            println("Early finish ${task.earlyFinish}")
            println("Late start   ${task.lateStart}")
            println("Late finish  ${task.lateFinish}")
            println("Reserve time ${task.reserveTime}")
            println()
        }

        val criticalTasks = getCriticalTaskChain(tasksList)

        var criticalTasksPath = ""
        criticalTasks.forEach {
            if (it.isVirtual.not()) {
                criticalTasksPath += "${it.taskId} - "
            }
        }
        criticalTasksPath = criticalTasksPath.substring(0, criticalTasksPath.length - 3)
        println("Critical path")
        println(criticalTasksPath)
        println("Project duration")
        println(criticalTasks.maxOfOrNull { it.earlyFinish })
    }

    fun printLoadChart(tasksList: List<Task>) {
        val tasks = tasksList.filter { it.isVirtual.not() }
        val maxWidth = tasks.maxOfOrNull { it.earlyFinish } ?: 0

        val timeToLoad = mutableListOf<Pair<Int, Int>>()

        for (time in 0..maxWidth) {
            val fitTasks = tasks.filter {
                time in it.earlyStart until it.earlyFinish
            }
            val load = fitTasks.sumOf { it.resources }

            println("Time: $time Load: $load Tasks: $fitTasks")
            Thread.sleep(100)
            timeToLoad.add(Pair(time, load))
        }

        val maxHeight = timeToLoad.maxOfOrNull { it.second } ?: 0

        println("_____ Load chart _____")

        val maxLabelLength = timeToLoad.maxOfOrNull { it.first.toString().length } ?: 0
        val labelFormat = "%${maxLabelLength}s "

        // Print chart by rows
        for (height in maxHeight downTo 0) {
            for ((_, dayHeight) in timeToLoad) {
                if (dayHeight > height) {
                    print("*".padEnd(maxLabelLength + 1, ' '))
                } else {
                    print(" ".repeat(maxLabelLength + 1))
                }
            }
            println()
        }

        // Print axis
        for ((day, _) in timeToLoad) {
            print(labelFormat.format(day))
        }
        println()
    }


    fun getCriticalTaskChain(tasks: List<Task>): List<Task> {
        return sortTaskChain(tasks.filter { it.reserveTime == 0 })
    }

    fun sortTaskChain(tasks: List<Task>): List<Task> {
        val visitedTasks = mutableListOf<Task>()
        while (visitedTasks.size != tasks.size) {
            for (i in tasks.indices) {
                val task = tasks[i]
                if (task.taskId in visitedTasks.map { it.taskId }) break

                val nextTasks = tasks.filter { it.previous.contains(task.taskId) }

                // if the next ones have not yet been solved.
                if (visitedTasks.map { it.taskId }.containsAll(nextTasks.map { it.taskId }).not()) continue
                visitedTasks.add(task)
            }
        }

        return visitedTasks.reversed()
    }

    fun findProjectDuration(tasks: List<Task>): Int {
        return tasks.maxOfOrNull { it.earlyFinish } ?: 0
    }

    private fun calculateLateFinishes(tasks: MutableList<Task>) {
        val visitedTasksId = mutableListOf<Int>()
        while (visitedTasksId.size != tasks.size) {
            for (i in tasks.indices) {
                val task = tasks[i]
                if (task.taskId in visitedTasksId) continue

                val nextTasks = tasks.filter { it.previous.contains(task.taskId) }

                // if the next ones have not yet been solved.
                if (visitedTasksId.containsAll(nextTasks.map { it.taskId }).not()) continue
                visitedTasksId.add(task.taskId)

                // if no tasks after
                if (nextTasks.isEmpty()) {
                    tasks[i] = task.copy(lateFinish = task.earlyFinish)
                    continue
                }

                // for handle tasks after
                val lateFinish = nextTasks.minOfOrNull { it.lateStart } ?: 0

//                Thread.sleep(500)
//                println("Task ${task.taskId}")
//                println("Previous")
//                println(tasks.filter { t -> task.previous.contains(t.taskId) })
//                println("Early start")
//                println(lateFinish)
//                println()

                tasks[i] = task.copy(lateFinish = lateFinish)
            }
        }
    }

    private fun calculateEarlyStarts(tasks: MutableList<Task>) {
        val visitedTasksId = mutableListOf<Int>()
        while (visitedTasksId.size != tasks.size) {
            for (i in tasks.indices) {
                val task = tasks[i]
                if (task.taskId in visitedTasksId) break

                // if the previous ones have not yet been solved.
                if (visitedTasksId.containsAll(task.previous).not()) continue
                visitedTasksId.add(task.taskId)

                // if no tasks before
                if (task.previous.isEmpty()) {
                    tasks[i] = task.copy(earlyStart = 0)
                    continue
                }

                // for handle tasks before
                val earlyStart =
                    tasks.filter { t -> task.previous.contains(t.taskId) }.maxOfOrNull { it.earlyFinish } ?: 0

//                Thread.sleep(200)
//                println("Task ${task.taskId}")
//                println("Previous")
//                println(tasks.filter { t -> task.previous.contains(t.taskId) })
//                println("Early start")
//                println(earlyStart)
//                println()

                tasks[i] = task.copy(earlyStart = earlyStart)
            }
        }
    }

    private fun makeStartTasksList(tasksList: List<Task>): MutableList<Task> {
        var tasks = deepCopyTasks(tasksList).toMutableList()

        val firstTasks = mutableListOf<Int>()
        tasks.forEach { task ->
            if (task.previous.isEmpty()) {
                firstTasks.add(task.taskId)
            }
        }

        // Add first task is needed
        if (firstTasks.size > 1) {
            val startTaskId = findUniqueInt(tasks)
            val startTask = Task(
                taskId = startTaskId,
                previous = emptyList(),
                duration = 0,
                resources = 0,
                isVirtual = true
            )

            tasks.forEachIndexed { index, task ->
                if (task.previous.isEmpty()) {
                    tasks[index] = task.copy(previous = listOf(startTaskId))
                }
            }

            tasks = (listOf(startTask) + tasks).toMutableList()
        }

        // Adding final task is needed
        val endTasks = mutableListOf<Int>()
        tasks.forEach { task ->
            if (tasks.none { task.taskId in it.previous }) {
                endTasks.add(task.taskId)
            }
        }

        if (endTasks.size > 1) {
            val endTaskId = findUniqueInt(tasks)
            val endTask = Task(
                taskId = endTaskId,
                previous = endTasks,
                duration = 0,
                resources = 0,
                isVirtual = true
            )

            tasks = (tasks + listOf(endTask)).toMutableList()
        }

        return tasks
    }

    private fun findUniqueInt(tasks: List<Task>): Int {
        var id = -1
        while (true) {
            if (tasks.any { it.taskId == id }.not()) {
                return id
            }
            id++
        }
    }

    private fun deepCopyTasks(tasks: List<Task>): List<Task> {
        val copiedTasks = mutableListOf<Task>()

        for (task in tasks) {
            val taskCopy = Task(
                taskId = task.taskId,
                previous = task.previous.toList(),
                duration = task.duration,
                resources = task.resources,
                earlyStart = task.earlyStart,
                lateFinish = task.lateFinish
            )
            copiedTasks.add(taskCopy)
        }

        return copiedTasks
    }
}