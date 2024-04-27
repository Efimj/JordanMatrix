package com.example.matrix.main.other

import com.example.matrix.main.other.ArrayHelper.Companion.fillTwoDimArrayRandomly
import com.example.matrix.main.other.ArrayHelper.Companion.generateTwoDimArray
import com.example.matrix.main.other.ArrayHelper.Companion.printArray
import com.example.matrix.main.MatrixHandler.Companion.inverseMatrix

fun main(args: Array<String>) {
    println("Hello World!")

    val rows = 3
    val cols = 4
    val array = generateTwoDimArray(rows, cols, 0.0)
    fillTwoDimArrayRandomly(array, -5.0, +5.0)
    printArray(array)
    println()

    inverseMatrix(array).let { if (it != null) printArray(it) }

    printArray(array)
}

