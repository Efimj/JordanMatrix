package com.example.transportProblem

import com.example.matrix.main.TransportProblemSolver.Companion.TransportProblem
import com.example.matrix.main.TransportProblemSolver.Companion.solveTransportProblem
import com.example.matrix.main.other.ArrayHelper.Companion.printArray
import org.junit.Test

class TransportProblemTestSolver {
    @Test
    fun transportProblemTestSolverTest1() {
        val input = TransportProblem(
            rowsPossibility = arrayOf(30.0, 20.0, 50.0),
            colsNeed = arrayOf(10.0, 65.0, 25.0),
            matrix = arrayOf(
                arrayOf(6.0, 3.0, 2.0),
                arrayOf(2.0, 1.0, 5.0),
                arrayOf(3.0, 4.0, 1.0)
            ),
        )

        solveTransportProblem(input)


//        ArrayHelper.printArray(solve)
//        println()
//        ArrayHelper.printArray(problem.rowsPossibility)
//        println()
//        ArrayHelper.printArray(problem.colsNeed)
    }
}