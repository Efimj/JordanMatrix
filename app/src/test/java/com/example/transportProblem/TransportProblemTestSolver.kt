package com.example.transportProblem

import com.example.matrix.main.ModifiedMatrixHandler
import com.example.matrix.main.TransportProblemSolver.Companion.TransportProblem
import com.example.matrix.main.TransportProblemSolver.Companion.solveTransportProblem
import com.example.matrix.main.other.ArrayHelper.Companion.printArray
import org.junit.Assert
import org.junit.Test
import kotlin.math.abs

class TransportProblemTestSolver {
    @Test
    fun transportProblemTestSolverTest1() {
        println()
        println("________ Test 1 _________")

        val input = TransportProblem(
            rowsPossibility = arrayOf(30.0, 20.0, 50.0),
            colsNeed = arrayOf(10.0, 65.0, 25.0),
            matrix = arrayOf(
                arrayOf(6.0, 3.0, 2.0),
                arrayOf(2.0, 1.0, 5.0),
                arrayOf(3.0, 4.0, 1.0)
            ),
        )

        val result = solveTransportProblem(input)

        println("Result plan")
        printArray(result.solve)
        println()
        println("Cost by plan")
        println(result.price)

        val expectedResult = arrayOf(
            arrayOf(0.0, 30.0, 0.0),
            arrayOf(0.0, 20.0, 0.0),
            arrayOf(10.0, 15.0, 25.0)
        )

        val expectedCost = 225.0

        Assert.assertTrue(expectedResult.contentDeepEquals(result.solve))
        Assert.assertTrue(expectedCost == result.price)
    }

    @Test
    fun transportProblemTestSolverTest2() {
        println()
        println("________ Test 2 _________")

        val input = TransportProblem(
            rowsPossibility = arrayOf(120.0, 100.0, 80.0),
            colsNeed = arrayOf(90.0, 90.0, 120.0),
            matrix = arrayOf(
                arrayOf(7.0, 6.0, 4.0),
                arrayOf(3.0, 8.0, 5.0),
                arrayOf(2.0, 3.0, 7.0)
            ),
        )

        val result = solveTransportProblem(input)

        println("Result plan")
        printArray(result.solve)
        println()
        println("Cost by plan")
        println(result.price)

        val expectedResult = arrayOf(
            arrayOf(0.0, 10.0, 110.0),
            arrayOf(90.0, 0.0, 10.0),
            arrayOf(0.0, 80.0, 0.0)
        )

        val expectedCost = 1060.0

        Assert.assertTrue(expectedResult.contentDeepEquals(result.solve))
        Assert.assertTrue(expectedCost == result.price)
    }

    @Test
    fun transportProblemTestSolverTestVariant() {
        println()
        println("________ Test Variant _________")

        val input = TransportProblem(
            rowsPossibility = arrayOf(105.0, 135.0, 125.0),
            colsNeed = arrayOf(85.0, 125.0, 105.0, 50.0),
            matrix = arrayOf(
                arrayOf(9.0, 4.0, 6.0, 9.0),
                arrayOf(7.0, 5.0, 7.0, 9.0),
                arrayOf(5.0, 6.0, 4.0, 8.0)
            ),
        )

        solveTransportProblem(input)
    }

    @Test
    fun transportProblemTestSolverTest4() {
        println()
        println("________ Test 1 simplex method _________")

        val input = arrayOf(
            arrayOf(1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 30.0),
            arrayOf(0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 20.0),
            arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 50.0),
            arrayOf(-1.0, 0.0, 0.0, -1.0, 0.0, 0.0, -1.0, 0.0, 0.0, -10.0),
            arrayOf(0.0, -1.0, 0.0, 0.0, -1.0, 0.0, 0.0, -1.0, 0.0, -65.0),
            arrayOf(0.0, 0.0, -1.0, 0.0, 0.0, -1.0, 0.0, 0.0, -1.0, -25.0),
            arrayOf(6.0, 3.0, 2.0, 2.0, 1.0, 5.0, 3.0, 4.0, 1.0, 0.0)
        )

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf("x1", "x2", "x3", "x4", "x5", "x6", "x7", "x8", "x9"),
            rows = arrayOf("y1", "y2", "y3", "y4", "y5", "y6")
        )

        val resultAfterRemovedNullLines =
            ModifiedMatrixHandler.removeNullLines(input, xy)

        println("___ after removing null lines ___")
        printArray(resultAfterRemovedNullLines.matrix!!)
        println()

        val resultAfterReference =
            ModifiedMatrixHandler.searchReferenceSolution(
                resultAfterRemovedNullLines.matrix!!,
                resultAfterRemovedNullLines.xyPos
            )

        println("___ reference ___")
        printArray(resultAfterReference.matrix!!)
        println()

        val resultAfterOptimal =
            ModifiedMatrixHandler.searchOptimalSolveMaximum(resultAfterReference.matrix!!, resultAfterReference.xyPos)

        println("___ optimal ___")
        printArray(resultAfterOptimal.matrix!!)
        println()

        val expectedCost = 225.0

        Assert.assertTrue(expectedCost == abs(resultAfterOptimal.matrix!!.last().last()))
    }

    @Test
    fun transportProblemTestSolverTest5() {
        println()
        println("________ Test 2 simplex method _________")

        val input = arrayOf(
            arrayOf(1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 120.0),
            arrayOf(0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 100.0),
            arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 80.0),
            arrayOf(-1.0, 0.0, 0.0, -1.0, 0.0, 0.0, -1.0, 0.0, 0.0, -90.0),
            arrayOf(0.0, -1.0, 0.0, 0.0, -1.0, 0.0, 0.0, -1.0, 0.0, -90.0),
            arrayOf(0.0, 0.0, -1.0, 0.0, 0.0, -1.0, 0.0, 0.0, -1.0, -120.0),
            arrayOf(7.0, 6.0, 4.0, 3.0, 8.0, 5.0, 2.0, 3.0, 7.0, 0.0)
        )

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf("x1", "x2", "x3", "x4", "x5", "x6", "x7", "x8", "x9"),
            rows = arrayOf("y1", "y2", "y3", "y4", "y5", "y6")
        )

        val resultAfterRemovedNullLines =
            ModifiedMatrixHandler.removeNullLines(input, xy)

        println("___ after removing null lines ___")
        printArray(resultAfterRemovedNullLines.matrix!!)
        println()

        val resultAfterReference =
            ModifiedMatrixHandler.searchReferenceSolution(
                resultAfterRemovedNullLines.matrix!!,
                resultAfterRemovedNullLines.xyPos
            )

        println("___ reference ___")
        printArray(resultAfterReference.matrix!!)
        println()

        val resultAfterOptimal =
            ModifiedMatrixHandler.searchOptimalSolveMaximum(resultAfterReference.matrix!!, resultAfterReference.xyPos)

        println("___ optimal ___")
        printArray(resultAfterOptimal.matrix!!)
        println()

        val expectedCost = 1060.0

        Assert.assertTrue(expectedCost == abs(resultAfterOptimal.matrix!!.last().last()))
    }

    @Test
    fun transportProblemTestSolverTest6() {
        println()
        println("________ Test variant simplex method _________")

        val input = arrayOf(
            arrayOf(1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 105.0),
            arrayOf(0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 135.0),
            arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 125.0),
            arrayOf(-1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -85.0),
            arrayOf(0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, -120.0),
            arrayOf(0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, -105.0),
            arrayOf(0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, -1.0, -50.0),
            arrayOf(9.0, 4.0, 6.0, 9.0, 7.0, 5.0, 7.0, 9.0, 5.0, 6.0, 4.0, 8.0, 0.0)
        )

        val xy = ModifiedMatrixHandler.Companion.XYPositions(
            cols = arrayOf("x1", "x2", "x3", "x4", "x5", "x6", "x7", "x8", "x9", "x10", "x11", "x12"),
            rows = arrayOf("y1", "y2", "y3", "y4", "y5", "y6", "y7")
        )

        val resultAfterRemovedNullLines =
            ModifiedMatrixHandler.removeNullLines(input, xy)

        println("___ after removing null lines ___")
        printArray(resultAfterRemovedNullLines.matrix!!)
        println()

        val resultAfterReference =
            ModifiedMatrixHandler.searchReferenceSolution(
                resultAfterRemovedNullLines.matrix!!,
                resultAfterRemovedNullLines.xyPos
            )

        println("___ reference ___")
        printArray(resultAfterReference.matrix!!)
        println()

        val resultAfterOptimal =
            ModifiedMatrixHandler.searchOptimalSolveMaximum(resultAfterReference.matrix!!, resultAfterReference.xyPos)

        println("___ optimal ___")
        printArray(resultAfterOptimal.matrix!!)
        println()

        val expectedCost = 1920.0

        Assert.assertTrue(expectedCost == abs(resultAfterOptimal.matrix!!.last().last()))
    }
}