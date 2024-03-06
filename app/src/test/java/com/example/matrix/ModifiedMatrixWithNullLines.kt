package com.example.matrix

import ArrayHelper.Companion.printArray
import com.example.matrix.main.ModifiedMatrixHandler
import com.example.matrix.main.ModifiedMatrixHandler.Companion.removeNullLines
import org.junit.Test

class ModifiedMatrixWithNullLines {
    @Test
    fun T1() {
        println("T1")

        val inputMatrix = arrayOf(
            arrayOf(-2.0, 1.0, 1.0, 3.0, 2.0),
            arrayOf(-3.0, 2.0, -3.0, 0.0, 7.0),
            arrayOf(-3.0, 1.0, 4.0, 1.0, 1.0),
            arrayOf(3.0, -2.0, 2.0, -2.0, -9.0),
            arrayOf(-10.0, 1.0, 42.0, 52.0, 0.0)
        )

//        val correct = arrayOf(
//            arrayOf(1.0, 0.0, -2.0, -1.0, 1.0),
//            arrayOf(-1.0, 1.0, 1.0, -1.0, 5.0),
//            arrayOf(2.0, -3.0, 1.0, 6.0, 0.0),
//            arrayOf(-2.0, 5.0, 2.0, -5.0, 10.0),
//        )

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf("x1", "x2", "x3", "x4"),
            rows = arrayOf("0", "0", "y1", "y2")
        )

        val result = removeNullLines(inputMatrix, xy)
        result.matrix.let {
            if (it == null) return
            printArray(it)
        }
        printArray(result.xyPos.rows)
        println()
        printArray(result.xyPos.cols)
    }
}