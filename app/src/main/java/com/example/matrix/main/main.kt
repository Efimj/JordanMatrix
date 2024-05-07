package com.example.matrix.main

fun main() {
//    val m = arrayOf(
//        arrayOf(0.0, 1.0, 1.0),
//        arrayOf(1.0, 0.0, 1.0),
//        arrayOf(1.0, 0.0, 0.0)
//    )
//
//    // [(2, 1), (0, 1), (0, 2), (1, 2), (1, 0), (2, 0)]
//
//    val startRow = 2
//    val startCol = 1

//    val m = arrayOf(
//        arrayOf(1.0, 1.0, 0.0),
//        arrayOf(0.0, 1.0, 0.0),
//        arrayOf(0.0, 1.0, 1.0)
//    )
//
//    // [(2, 0), (0, 0), (0, 1), (2, 1)]
//
//    val startRow = 2
//    val startCol = 0

    val m = arrayOf(
        arrayOf(1.0, 1.0, 1.0),
        arrayOf(0.0, 0.0, 1.0),
        arrayOf(1.0, 0.0, 0.0)
    )

    // [(1, 0), (0, 0), (0, 2), (1, 2)]

    val startRow = 1
    val startCol = 0

    val res = findClosedPath(m, startRow, startCol)

    println("Vertices")
    println(res)
    println()
}

fun dfs(
    matrix: Array<Array<Double>>,
    visited: Array<BooleanArray>,
    row: Int,
    col: Int,
    path: MutableList<Pair<Int, Int>>
) {
    val numRows = matrix.size
    val numCols = matrix[0].size

    var needExit = false
    var isColumn = false

    path.add(row to col)

    while (needExit.not()) {
        needExit = true

        val currentRow = path.last().first
        val currentCol = path.last().second

        if (isColumn.not()) {
            if (visited[row][currentCol].not() && matrix[row][currentCol] > 0.0) {
                visited[row][currentCol] = true
                path.add(row to currentCol)
                break
            } else {
                for (r in 0 until numRows) {
                    if (visited[r][currentCol]) continue
                    if (matrix[r][currentCol] > 0.0) {
                        var exec = false
                        for (c in 0 until numCols) {
                            if (r == currentRow) continue
                            if (matrix[r][c] > 0.0) {
                                exec = true
                            }
                        }
                        if (exec) {
                            visited[r][currentCol] = true
                            path.add(r to currentCol)
                            isColumn = true
                            needExit = false
                            break
                        }
                    }
                }
            }
        } else {
            if (visited[currentRow][col].not() && matrix[currentRow][col] > 0.0) {
                visited[currentRow][col] = true
                path.add(currentRow to col)
                break
            } else {
                for (c in 0 until numCols) {
                    if (visited[currentRow][c]) continue
                    if (matrix[currentRow][c] > 0.0) {
                        var exec = false
                        for (r in 0 until numRows) {
                            if (r == currentRow) continue
                            if (matrix[r][c] > 0.0) {
                                exec = true
                            }
                        }
                        if (exec) {
                            visited[currentRow][c] = true
                            path.add(currentRow to c)
                            isColumn = false
                            needExit = false
                            break
                        }
                    }
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
