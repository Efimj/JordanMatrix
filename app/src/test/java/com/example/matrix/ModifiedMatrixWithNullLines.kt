package com.example.matrix

import ArrayHelper.Companion.printArray
import com.example.matrix.main.ModifiedMatrixHandler
import com.example.matrix.main.ModifiedMatrixHandler.Companion.removeIndependentVariables
import com.example.matrix.main.ModifiedMatrixHandler.Companion.removeNullLines
import com.example.matrix.main.ModifiedMatrixHandler.Companion.searchOptimalSolveMaximum
import com.example.matrix.main.ModifiedMatrixHandler.Companion.searchReferenceSolution
import org.junit.Assert
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

        val correctAfterRemovedNullLines = arrayOf(
            arrayOf(-5.0, -6.0, 3.0),
            arrayOf(-2.0, -8.0, 2.0),
            arrayOf(-9.0, -9.0, 8.0),
            arrayOf(-1.0, -2.0, -2.0),
            arrayOf(1.0, 1.0, 22.0)
        )

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf("x1", "x2", "x3", "x4"),
            rows = arrayOf("0", "0", "y1", "y2")
        )

        val resultAfterRemovedNullLines = removeNullLines(inputMatrix, xy)

        println()
        println("Remove null Lines")
        println("Output")
        resultAfterRemovedNullLines.matrix.let {
            if (it == null) return
            printArray(it)
        }
        println()
        println("Correct")
        printArray(correctAfterRemovedNullLines)
        println()

        Assert.assertTrue(correctAfterRemovedNullLines.contentDeepEquals(resultAfterRemovedNullLines.matrix))

        val correctAfterReference = arrayOf(
            arrayOf(-5.0, 4.0, 13.0),
            arrayOf(-2.0, -4.0, 6.0),
            arrayOf(-9.0, 9.0, 26.0),
            arrayOf(-1.0, 2.0, 2.0),
            arrayOf(1.0, -1.0, 20.0)
        )

        val resultAfterReference =
            searchReferenceSolution(resultAfterRemovedNullLines.matrix!!, resultAfterRemovedNullLines.xyPos)

        println()
        println("Reference solve")
        println("Output")
        resultAfterReference.matrix.let {
            if (it == null) return
            printArray(it)
        }
        println()
        println("Correct")
        printArray(correctAfterReference)
        println()

        Assert.assertTrue(correctAfterReference.contentDeepEquals(resultAfterReference.matrix))

        val correctOptimal = arrayOf(
            arrayOf(-3.0, -2.0, 9.0),
            arrayOf(-4.0, 2.0, 10.0),
            arrayOf(-4.5, -4.5, 17.0),
            arrayOf(-0.5, 0.5, 1.0),
            arrayOf(0.5, 0.5, 21.0)
        )

        val resultAfterOptimal =
            searchOptimalSolveMaximum(resultAfterReference.matrix!!, resultAfterReference.xyPos)

        println()
        println("Optimal solve")
        println("Output")
        resultAfterOptimal.matrix.let {
            if (it == null) return
            printArray(it)
        }
        println()
        println("Correct")
        printArray(correctOptimal)
        println()

        Assert.assertTrue(correctOptimal.contentDeepEquals(resultAfterOptimal.matrix))
    }

    @Test
    fun T2() {
        println("T2")

        val inputMatrix = arrayOf(
            arrayOf(-1.0, -2.0, 1.0),
            arrayOf(-2.0, -1.0, -4.0),
            arrayOf(-1.0, 1.0, 1.0),
            arrayOf(-1.0, 4.0, 13.0),
            arrayOf(4.0, -1.0, 23.0),
            arrayOf(3.0, -6.0, 0.0)
        )

        val correctAfterRemovedIndependentRows = arrayOf(
            arrayOf(1.0, -1.0, 6.0),
            arrayOf(3.0, -2.0, 24.0),
            arrayOf(-2.0, 3.0, 9.0),
            arrayOf(-5.0, 4.0, -21.0),
        )

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf("x1", "x2"),
            rows = arrayOf("y1", "y2", "y3", "y4", "y5")
        )

        val independentVars = arrayOf("x1", "x2")

        val resultAfterRemovedNullLines = removeIndependentVariables(inputMatrix, xy, independentVars)

        println()
        println("Remove independent")
        println("Output")
        resultAfterRemovedNullLines.solve.matrix.let {
            if (it == null) return
            printArray(it)
        }
        println()
        println("Correct")
        printArray(correctAfterRemovedIndependentRows)
        println()

        println()
        println("Expressions to find x:")
        resultAfterRemovedNullLines.arrayToHandleX.forEach {
            println(it)
        }
        println()

        Assert.assertTrue(correctAfterRemovedIndependentRows.contentDeepEquals(resultAfterRemovedNullLines.solve.matrix))

        val resultAfterReference =
            searchReferenceSolution(resultAfterRemovedNullLines.solve.matrix!!, resultAfterRemovedNullLines.solve.xyPos)

        val resultAfterOptimal =
            searchReferenceSolution(resultAfterReference.matrix!!, resultAfterReference.xyPos)

        println()
        println("Optimal solve")
        println("Output")
        resultAfterOptimal.matrix.let {
            if (it == null) return
            printArray(it)
        }

        resultAfterRemovedNullLines.arrayToHandleX.reversedArray().forEach { expression ->
            val variables =
                getExpressions(equation = expression, matrix = resultAfterOptimal.matrix!!, resultAfterOptimal.xyPos)

            println(variables)
        }

        println("Rows")
        printArray(resultAfterOptimal.xyPos.rows)
        println()
        println("Cols")
        printArray(resultAfterOptimal.xyPos.cols)
        println()


//        println()
//        println("Correct")
//        printArray(correctOptimal)
//        println()
//
//        Assert.assertTrue(correctOptimal.contentDeepEquals(resultAfterOptimal.matrix))
    }

    private fun getExpressions(
        equation: String,
        matrix: Array<Array<Double>>,
        xyPositions: ModifiedMatrixHandler.Companion.XYPositions
    ): String {
        val variablePattern = Regex("[a-zA-Z]+\\d+")
        return variablePattern.replace(equation) { matchResult ->
            val indexInColumns = xyPositions.cols.indexOf(matchResult.value)
            if (indexInColumns != -1)
                return@replace "*0"
            val indexInRows = xyPositions.rows.indexOf(matchResult.value)
            if (indexInRows != -1)
                return@replace "*0"
            return@replace "*${matrix[indexInRows][matrix[indexInRows].size - 1]}"
        }
    }

    @Test
    fun T3() {
        println("T2")

        val inputMatrix = arrayOf(
            arrayOf(-1.0, -2.0, 1.0),
            arrayOf(-2.0, -1.0, -4.0),
            arrayOf(-1.0, 1.0, 1.0),
            arrayOf(-1.0, 4.0, 13.0),
            arrayOf(4.0, -1.0, 23.0),
            arrayOf(3.0, -6.0, 0.0)
        )

//        val correct = arrayOf(
//            arrayOf(1.0, 0.0, -2.0, -1.0, 1.0),
//            arrayOf(-1.0, 1.0, 1.0, -1.0, 5.0),
//            arrayOf(2.0, -3.0, 1.0, 6.0, 0.0),
//            arrayOf(-2.0, 5.0, 2.0, -5.0, 10.0),
//        )

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf("x1", "x2"),
            rows = arrayOf("y1", "y2", "y3", "y4", "y5")
        )

        val output1 = ModifiedMatrixHandler.searchReferenceSolution(matrix = inputMatrix, xy = xy)
        val output2 = ModifiedMatrixHandler.searchOptimalSolveMaximum(matrix = output1.matrix!!, xy = output1.xyPos)

        output2.matrix.let {
            if (it == null) return
            printArray(it)
        }

        println()
        println("RowsPoses")
        printArray(output2.xyPos.rows)
        println()
        println("ColsPoses")
        printArray(output2.xyPos.cols)

//        val result = removeNullLines(inputMatrix, xy)
//        result.matrix.let {
//            if (it == null) return
//            printArray(it)
//        }
//        printArray(result.xyPos.rows)
//        println()
//        printArray(result.xyPos.cols)
    }
}