package com.example.matrix.main

import com.example.matrix.main.other.ArrayHelper.Companion.addColumn
import com.example.matrix.main.other.ArrayHelper.Companion.addRow
import com.example.matrix.main.other.ArrayHelper.Companion.cloneArray
import com.example.matrix.main.other.ArrayHelper.Companion.printArray
import kotlin.math.abs

class TransportProblemSolver {
    companion object {
        data class TransportProblem(
            // An array providing different points with quantitative capability. (rows)
            val rowsPossibility: Array<Double>,

            // An array providing different points with quantitative needs. (cols)
            val colsNeed: Array<Double>,

            // Determines the price between points
            val matrix: Array<Array<Double>>
        )

        fun solveTransportProblem(input: TransportProblem) {
            var problem = TransportProblemSolver().cloneInput(input)
            if (TransportProblemSolver().checkIsClosedProblem(input).not()) {
                val quantitativePossibilitySum = input.rowsPossibility.sum()
                val quantitativeNeed = input.colsNeed.sum()
                val difference = quantitativePossibilitySum - quantitativeNeed
                if (difference > 0) {
                    problem = TransportProblemSolver().makeNewNeed(matrix = problem, difference = difference)
                } else {
                    problem = TransportProblemSolver().makeNewPossibility(matrix = problem, difference = difference)
                }
            }

            val referenceSolve = TransportProblemSolver().referenceSolveByNorthwestCorner(problem)
            val check1 = TransportProblemSolver().checkBasisCells(
                rows = problem.rowsPossibility.size,
                cols = problem.colsNeed.size,
                solve = referenceSolve
            )
            if (check1.not()) {
                println("Count base cells error")
                return
            }


            TransportProblemSolver().potentialStep(problem = problem, baseArray = referenceSolve)
        }
    }

    private fun potentialStep(problem: TransportProblem, baseArray: Array<Array<Double>>) {
        while (true) {
            val rowsPotential = Array(problem.rowsPossibility.size) { if (it == 0) 0.0 else Double.NaN }
            val colsPotential = Array(problem.colsNeed.size) { Double.NaN }

            while (rowsPotential.any { it.isNaN() } || colsPotential.any { it.isNaN() }) {
                for (row in baseArray.indices) {
                    for (col in baseArray.first().indices) {
                        if (baseArray[row][col] != 0.0) {
                            if (rowsPotential[row].isNaN().not()) {
                                colsPotential[col] = problem.matrix[row][col] - rowsPotential[row]
                            }
                            if (colsPotential[col].isNaN().not()) {
                                rowsPotential[row] = problem.matrix[row][col] - colsPotential[col]
                            }
                        }
                    }
                }
            }

            println()
            println("Rows potential")
            printArray(rowsPotential)
            println()
            println("Cols potential")
            printArray(colsPotential)
            println()

            val newMatrixPrice = Array(baseArray.size) { Array(baseArray.first().size) { 0.0 } }

            for (row in baseArray.indices) {
                for (col in baseArray.first().indices) {
                    if (baseArray[row][col] == 0.0) {
                        newMatrixPrice[row][col] = rowsPotential[row] + colsPotential[col]
                    }
                }
            }

            println()
            println("New price matrix without base prices")
            printArray(newMatrixPrice)
            println()

            val cells = mutableListOf<Cell>()

            for (row in baseArray.indices) {
                for (col in baseArray.first().indices) {
                    if (baseArray[row][col] == 0.0 && newMatrixPrice[row][col] > problem.matrix[row][col]) {
                        cells.add(
                            Cell(
                                value = newMatrixPrice[row][col],
                                row = row,
                                col = col
                            )
                        )
                    }
                }
            }

            if (cells.isEmpty()) {
                printArray(baseArray)
                println("END")
                return
            }

            val cellToOptimization = cells.maxBy { it.value }

//            if (problem.matrix[cellToOptimization.row][cellToOptimization.col] > cellToOptimization.value) {
//                println("END")
//                println(cellToOptimization.value)
//                return
//            }

            println("Cell to optimization = [${cellToOptimization.row}; ${cellToOptimization.col}]")
            println()

            val path = findConnectedPath(baseArray, cellToOptimization)

            var minimum = baseArray[path[1].first][path[1].second]
            for (i in 1 until path.size step 2) {
                val value = baseArray[path[i].first][path[i].second]
                if (value < minimum) {
                    minimum = value
                }
            }

            for (i in path.indices) {
                if (i % 2 == 0) {
                    baseArray[path[i].first][path[i].second] += minimum
                } else {
                    baseArray[path[i].first][path[i].second] -= minimum
                }
            }

            println("New base array")
            printArray(baseArray)
            println()
        }
    }

    private fun findConnectedPath(matrix: Array<Array<Double>>, start: Cell): List<Pair<Int, Int>> {
        fun dfs(
            matrix: Array<Array<Double>>,
            visited: Array<BooleanArray>,
            row: Int,
            col: Int,
            path: MutableList<Pair<Int, Int>>
        ) {
            val numRows = matrix.size
            val numCols = matrix[0].size

            visited[row][col] = true
            path.add(row to col)

            val directions = arrayOf(
                intArrayOf(-1, 0), // up
                intArrayOf(1, 0),  // down
                intArrayOf(0, -1), // left
                intArrayOf(0, 1)   // right
            )


            for (r in 0..numRows) {
                for (c in 0..numCols) {
                    for (dir in directions) {
                        if (r == c) {
                            continue
                        }

                        val newRow = row + dir[0] + r
                        val newCol = col + dir[1] + c

                        // eliminate diagonal transitions
                        if (newRow != row && newCol != col) {
                            continue
                        }

                        // so as not to go back along the way
                        if (path.size > 1 && path[path.size - 2].first == newRow && path[path.size - 2].second == newCol) {
                            continue
                        }

                        if (newRow < 0 || newRow >= numRows || newCol < 0 || newCol >= numCols || visited[newRow][newCol]) {
                            continue
                        }

                        if (matrix[newRow][newCol] > 0) {
                            dfs(matrix, visited, newRow, newCol, path)
                            return
                        }
                    }
                }
            }
        }

        fun findClosedPath(matrix: Array<Array<Double>>, startRow: Int, startCol: Int): List<Pair<Int, Int>> {
            val visited = Array(matrix.size) { BooleanArray(matrix.first().size) }
            val path = mutableListOf<Pair<Int, Int>>()

            dfs(matrix, visited, startRow, startCol, path)

            return path
        }

        //        fun dfs(
//            matrix: Array<Array<Double>>,
//            visited: Array<Array<MutableList<Pair<Int, Int>>?>>,
//            row: Int,
//            col: Int,
//            path: MutableList<Pair<Int, Int>>
//        ) {
//            val numRows = matrix.size
//            val numCols = matrix[0].size
//
//            val directions = arrayOf(
//                intArrayOf(-1, 0), // up
//                intArrayOf(1, 0),  // down
//                intArrayOf(0, -1), // left
//                intArrayOf(0, 1)   // right
//            )
//
//            for (r in 0..numRows) {
//                for (c in 0..numCols) {
//                    for (dir in directions) {
//                        if (r == c) {
//                            continue
//                        }
//
//                        val newRow = row + dir[0] + r
//                        val newCol = col + dir[1] + c
//
//                        // eliminate diagonal transitions
//                        if (newRow != row && newCol != col) {
//                            continue
//                        }
//
//                        // so as not to go back along the way
//                        if (path.size > 1 && path[path.size - 2].first == newRow && path[path.size - 2].second == newCol) {
//                            continue
//                        }
//
//                        if (newRow < 0 || newRow >= numRows || newCol < 0 || newCol >= numCols) {
//                            continue
//                        }
//
//                        val vs = visited[newRow][newCol]?.size
//                        if (vs != null && vs > 3) {
//                            return
//                        }
//
//                        val isVisited = visited[newRow][newCol]?.any { it.first == dir[0] && it.second == dir[1] }
//                        if (isVisited != null && isVisited) {
//                            continue
//                        }
//
//                        if (matrix[newRow][newCol] > 0) {
//                            if (visited[row][col] == null) {
//                                visited[row][col] = mutableListOf(Pair(dir[0], dir[1]))
//                            } else {
//                                visited[row][col]?.add(Pair(dir[0], dir[1]))
//                            }
//                            path.add(row to col)
//
//                            dfs(matrix, visited, newRow, newCol, path)
//                            return
//                        }
//                    }
//                }
//            }
//        }
//
//        fun findClosedPath(matrix: Array<Array<Double>>, startRow: Int, startCol: Int): List<Pair<Int, Int>> {
//            val visited = Array(matrix.size) { Array<MutableList<Pair<Int, Int>>?>(matrix[0].size) { null } }
//            val path = mutableListOf<Pair<Int, Int>>()
//
//            dfs(matrix, visited, startRow, startCol, path)
//
//            return path
//        }

        val res = findClosedPath(matrix, start.row, start.col)
        val resCut = removeIntermediatePathValues(res)

        println("Vertices")
        println(res)
        println()

        return res
    }

    private fun removeIntermediatePathValues(res: List<Pair<Int, Int>>): MutableList<Pair<Int, Int>> {
        val resCut = mutableListOf<Pair<Int, Int>>()

        for (element in res) {
            if (resCut.size >= 2) {
                val (lastF, lastS) = resCut.last()
                val (beforeLastF, beforeLastS) = resCut[resCut.size - 2]

                if (lastF == element.first && beforeLastF == element.first) {
                    resCut[resCut.size - 1] = element
                    continue
                }

                if (lastS == element.second && beforeLastS == element.second) {
                    resCut[resCut.size - 1] = element
                    continue
                }
            }
            resCut.add(element)
        }
        return resCut
    }

    data class Cell(
        val value: Double,
        val row: Int,
        val col: Int
    )

    private fun checkBasisCells(rows: Int, cols: Int, solve: Array<Array<Double>>): Boolean {
        val count = rows + cols - 1
        var founded = 0

        for (row in solve.indices) {
            for (col in solve.first().indices) {
                if (solve[row][col] > 0.0) founded++
            }
        }

        return founded == count
    }

    fun findCost(problem: TransportProblem, solve: Array<Array<Double>>): Double {
        var cost = 0.0
        for (row in problem.matrix.indices) {
            for (col in problem.matrix.first().indices) {
                runCatching {
                    cost += solve[row][col] * problem.matrix[row][col]
                }
            }
        }

        return cost
    }

    private fun referenceSolveByNorthwestCorner(input: TransportProblem): Array<Array<Double>> {
        val problem = TransportProblemSolver().cloneInput(input)
        val solve = Array(problem.matrix.size) { Array(problem.matrix.first().size) { 0.0 } }

        var row = 0
        var col = 0

        while (row < problem.matrix.size && col < problem.matrix.first().size) {
            if (problem.rowsPossibility[row] > 0) {
                val difference = problem.colsNeed[col].coerceAtMost(problem.rowsPossibility[row])
                solve[row][col] += difference

                problem.colsNeed[col] = problem.colsNeed[col] - difference
                problem.rowsPossibility[row] = problem.rowsPossibility[row] - difference

                if (problem.colsNeed[col] == 0.0 && problem.rowsPossibility[row] == 0.0) {
                    row++
                    col++
                } else if (problem.colsNeed[col] == 0.0) {
                    col++
                } else {
                    row++
                }
            }
        }

        println()
        println("Reference solve by Northwest Corner")
        printArray(solve)
        println()
        println("Cost")
        println(findCost(problem, solve))
        println()

        return solve
    }

    private fun makeNewNeed(
        matrix: TransportProblem,
        difference: Double
    ): TransportProblem {
        addColumn(matrix = matrix.matrix, newColumnPlaceholder = 0.0)
        val newQuantitativeNeed = Array(matrix.colsNeed.size + 1) { 0.0 }
        for (i in matrix.colsNeed.indices) {
            newQuantitativeNeed[i] = matrix.colsNeed[i]
        }
        newQuantitativeNeed[newQuantitativeNeed.lastIndex] = difference
        return matrix.copy(colsNeed = newQuantitativeNeed)
    }

    private fun makeNewPossibility(
        matrix: TransportProblem,
        difference: Double
    ): TransportProblem {
        addRow(matrix = matrix.matrix, newRowPlaceholder = 0.0)
        val newQuantitativePossibility = Array(matrix.rowsPossibility.size + 1) { 0.0 }
        for (i in matrix.rowsPossibility.indices) {
            newQuantitativePossibility[i] = matrix.rowsPossibility[i]
        }
        newQuantitativePossibility[newQuantitativePossibility.lastIndex] = difference
        return matrix.copy(rowsPossibility = newQuantitativePossibility)
    }

    fun cloneInput(input: TransportProblem): TransportProblem {
        return TransportProblem(
            rowsPossibility = cloneArray(input.rowsPossibility),
            colsNeed = cloneArray(input.colsNeed),
            matrix = cloneArray(input.matrix),
        )
    }

    fun checkIsClosedProblem(input: TransportProblem): Boolean {
        val quantitativePossibilitySum = input.rowsPossibility.sum()
        val quantitativeNeed = input.colsNeed.sum()

        return quantitativePossibilitySum == quantitativeNeed
    }
}