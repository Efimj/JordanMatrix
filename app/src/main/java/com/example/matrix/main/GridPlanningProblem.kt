package com.example.matrix.main

class GridPlanningProblem {
    companion object {
        data class Task(
            /**
             * Unique task id.
             */
            val taskId: Int,

            /**
             * Tasks that need to be completed before.
             */
            val previousTasks: List<Int>,

            /**
             * Duration of task.
             */
            val duration: Int,

            /**
             * Resources to perform, such as people.
             */
            val resources: Int,

            /**
             * This is the date from which a given task can begin as soon as possible,
             * taking into account all connections with previous tasks and their duration.
             */
            val earlyStart: Int,

            /**
             * It is the latest date that the task can be completed
             * without affecting the entire duration of the project.
             */
            val lateFinish: Int,
        ) {
            /**
             * Early finish = Early start + duration.
             */
            val earlyFinish: Int
                get() = earlyStart + duration

            /**
             * Late start = Late finish - duration.
             */
            val lateStart: Int
                get() = lateFinish - duration

            /**
             * Reserve time = Late finish - early finish.
             */
            val reserveTime: Int
                get() = lateFinish - earlyFinish
        }

        fun findOptimalSolution(tasksList: List<Task>) {
            val tasks = GridPlanningProblem().deepCopyTasks(tasksList)


        }
    }

    private fun deepCopyTasks(tasksList: List<Task>): List<Task> {
        val copiedTasks = mutableListOf<Task>()

        for (task in tasksList) {
            val taskCopy = Task(
                taskId = task.taskId,
                previousTasks = task.previousTasks.toList(),
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