package com.example.matrix.main.planing

class GridPlanningProblem {
    companion object {
        fun findOptimalSolution(tasksList: List<Task>) {
            val tasks = GridPlanningProblem().makeStartTasksList(tasksList)

            GridPlanningProblem().calculateEarlyAndLateStarts(tasks)

            tasks.sortBy { it.taskId }

            tasks.forEach { task ->
                println("TASK ${task.taskId}")
                println("Duration ${task.duration}")
                println("Early start ${task.earlyStart}")
                println("Late start ${task.lateFinish}")
                println()
            }
        }
    }

    private fun calculateEarlyAndLateStarts(tasks: MutableList<Task>) {
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
            val earlyStart = tasks.filter { t -> task.previous.contains(t.taskId) }.map { it.lateStart }.max()
            tasks[taskIndex] = task.copy(earlyStart = earlyStart, )
        }
    }

    private fun makeStartTasksList(tasksList: List<Task>): MutableList<Task> {
        val startTask = Task(
            taskId = findUniqueInt(tasksList),
            previous = emptyList(),
            duration = 0,
            resources = 0,
        )

        val tasks = deepCopyTasks(tasksList).map { task ->
            if (task.previous.isEmpty()) {
                task.copy(previous = listOf(startTask.taskId))
            } else {
                task
            }
        }

        return (listOf(startTask) + tasks).toMutableList()
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