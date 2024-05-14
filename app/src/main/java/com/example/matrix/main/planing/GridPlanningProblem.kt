package com.example.matrix.main.planing

class GridPlanningProblem {
    companion object {
        fun findOptimalSolution(tasksList: List<Task>) {
            val tasks = GridPlanningProblem().makeStartTasksList(tasksList)

            GridPlanningProblem().calculateEarlyStarts(tasks)
            GridPlanningProblem().calculateLateFinishes(tasks)


            tasks.sortBy { it.taskId }
            tasks.forEach { task ->
                println("TASK ${task.taskId}")
                println("Duration     ${task.duration}")
                println("Early start  ${task.earlyStart}")
                println("Early finish ${task.earlyFinish}")
                println()
                println("Late start   ${task.lateStart}")
                println("Late finish  ${task.lateFinish}")
                println()
            }
        }
    }

    private fun calculateLateFinishes(tasks: MutableList<Task>) {
        val visitedTasksId = mutableListOf<Int>()
        while (visitedTasksId.size != tasks.size) {
            val task = tasks.find { task -> task.taskId !in visitedTasksId } ?: break
            val nextTasks = tasks.filter { task.taskId in it.previous }

            // if the next ones have not yet been solved.
            if (visitedTasksId.containsAll(nextTasks.map { it.taskId }).not()) continue
            val taskIndex = tasks.indexOfFirst { it.taskId == task.taskId }
            visitedTasksId.add(task.taskId)

            // if no tasks after
            if (nextTasks.isEmpty()) {
                tasks[taskIndex] = task.copy(lateFinish = task.earlyFinish)
                continue
            }

            // for handle tasks after
            val lateFinish = nextTasks.minOfOrNull { it.earlyFinish } ?: 0

            println("Task ${task.taskId}")
            println("Next")
            println(nextTasks.filter { t -> task.previous.contains(t.taskId) })
            println("Late finish")
            println(lateFinish)
            println()

            tasks[taskIndex] = task.copy(lateFinish = lateFinish)
        }
    }

    private fun calculateEarlyStarts(tasks: MutableList<Task>) {
        val visitedTasksId = mutableListOf<Int>()
        while (visitedTasksId.size != tasks.size) {
            val task = tasks.find { task -> task.taskId !in visitedTasksId } ?: break

            // if the previous ones have not yet been solved.
            if (visitedTasksId.containsAll(task.previous).not()) continue
            val taskIndex = tasks.indexOfFirst { it.taskId == task.taskId }
            visitedTasksId.add(task.taskId)

            // if no tasks before
            if (task.previous.isEmpty()) {
                tasks[taskIndex] = task.copy(earlyStart = 0)
                continue
            }

            // for handle tasks before
            val earlyStart = tasks.filter { t -> task.previous.contains(t.taskId) }.maxOfOrNull { it.earlyFinish } ?: 0

//            println("Task ${task.taskId}")
//            println("Previous")
//            println(tasks.filter { t -> task.previous.contains(t.taskId) })
//            println("Early start")
//            println(earlyStart)
//            println()

            tasks[taskIndex] = task.copy(earlyStart = earlyStart)
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
            val startTaskId = findUniqueInt(tasksList)
            val startTask = Task(
                taskId = startTaskId,
                previous = emptyList(),
                duration = 0,
                resources = 0,
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
            val endTaskId = findUniqueInt(tasksList)
            val endTask = Task(
                taskId = endTaskId,
                previous = endTasks,
                duration = 0,
                resources = 0,
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