package com.example.matrix.main.planing

data class Task(
    /**
     * Unique task id.
     */
    val taskId: Int,

    /**
     * Tasks that need to be completed before.
     */
    val previous: List<Int> = emptyList(),

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
    val earlyStart: Int = 0,

    /**
     * It is the latest date that the task can be completed
     * without affecting the entire duration of the project.
     */
    val lateFinish: Int = 0,
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