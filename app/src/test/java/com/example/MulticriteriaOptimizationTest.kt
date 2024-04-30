package com.example

import com.example.matrix.main.MO.Companion.findCompromiseSolution
import com.example.matrix.main.other.ArrayHelper.Companion.printArray
import org.junit.Assert
import org.junit.Test

class MulticriteriaOptimizationTest() {
//    @Test
//    fun test1() {
//        val t1 = arrayOf(
//            arrayOf(-1.0, 2.0, -1.0, 2.0, 1.0, 6.0),
//            arrayOf(1.0, 4.0, 3.0, 2.0, 1.0, 9.0),
//            arrayOf(1.0, 2.0, 0.0, 2.0, -1.0, 2.0),
//            arrayOf(-2.0, -2.0, -1.0, -1.0, -1.0, 0.0)
//        )
//        val t1z = arrayOf(2.0, 2.0, 1.0, 1.0, 1.0)
//
//        val t2 = arrayOf(
//            arrayOf(-1.0, 2.0, -1.0, 2.0, 1.0, 6.0),
//            arrayOf(1.0, 4.0, 3.0, 2.0, 1.0, 9.0),
//            arrayOf(1.0, 2.0, 0.0, 2.0, -1.0, 2.0),
//            arrayOf(1.0, -3.0, 5.0, -1.0, -2.0, 0.0)
//        )
//        val t2z = arrayOf(1.0, -3.0, 5.0, -1.0, -2.0)
//
//
//        val t3 = arrayOf(
//            arrayOf(-1.0, 2.0, -1.0, 2.0, 1.0, 6.0),
//            arrayOf(1.0, 4.0, 3.0, 2.0, 1.0, 9.0),
//            arrayOf(1.0, 2.0, 0.0, 2.0, -1.0, 2.0),
//            arrayOf(-1.0, 4.0, -5.0, -9.0, 2.0, 0.0)
//        )
//        val t3z = arrayOf(1.0, -4.0, 5.0, 9.0, -2.0)
//
//        findCompromiseSolution(
//            matrices = arrayOf(t1, t2, t3),
//            zRows = arrayOf(t1z, t2z, t3z)
//        )
//    }

    @Test
    fun test2() {
        val t1 = arrayOf(
            arrayOf(1.0, -1.0, 1.0, 1.0, 2.0),
            arrayOf(1.0, 1.0, 1.0, -1.0, 2.0),
            arrayOf(-1.0, 1.0, 1.0, 1.0, 2.0),
            arrayOf(1.0, 1.0, -1.0, 1.0, 2.0),
            arrayOf(-1.0, 8.0, -1.0, -4.0, 0.0),
        )
        val t1z = arrayOf(1.0, -8.0, 1.0, 4.0)

        val t2 = arrayOf(
            arrayOf(1.0, -1.0, 1.0, 1.0, 2.0),
            arrayOf(1.0, 1.0, 1.0, -1.0, 2.0),
            arrayOf(-1.0, 1.0, 1.0, 1.0, 2.0),
            arrayOf(1.0, 1.0, -1.0, 1.0, 2.0),
            arrayOf(-1.0, 3.0, 5.0, 1.0, 0.0)
        )
        val t2z = arrayOf(-1.0, 3.0, 5.0, 1.0)

        val t3 = arrayOf(
            arrayOf(1.0, -1.0, 1.0, 1.0, 2.0),
            arrayOf(1.0, 1.0, 1.0, -1.0, 2.0),
            arrayOf(-1.0, 1.0, 1.0, 1.0, 2.0),
            arrayOf(1.0, 1.0, -1.0, 1.0, 2.0),
            arrayOf(-3.0, -1.0, -1.0, 1.0, 0.0)
        )
        val t3z = arrayOf(3.0, 1.0, 1.0, -1.0)

        val compromiseVector = findCompromiseSolution(
            matrices = arrayOf(t1, t2, t3),
            zRows = arrayOf(t1z, t2z, t3z)
        )

        println("COMPROMISE VECTOR")
        printArray(compromiseVector)
        println()

        val expected = arrayOf(1.45, 0.0, 0.0, 0.55)

        Assert.assertTrue(expected.contentDeepEquals(compromiseVector))
    }

    @Test
    fun test3() {
        val t1 = arrayOf(
            arrayOf(2.0, -1.0, 3.0, 4.0, 10.0),
            arrayOf(1.0, 1.0, 1.0, -1.0, 5.0),
            arrayOf(1.0, 2.0, -2.0, 4.0, 12.0),
            arrayOf(-1.0, -2.0, -1.0, 0.0, 0.0),
        )
        val t1z = arrayOf(1.0, 2.0, 1.0, 0.0)

        val t2 = arrayOf(
            arrayOf(2.0, -1.0, 3.0, 4.0, 10.0),
            arrayOf(1.0, 1.0, 1.0, -1.0, 5.0),
            arrayOf(1.0, 2.0, -2.0, 4.0, 12.0),
            arrayOf(-1.0, -2.0, 1.0, 1.0, 0.0),
        )
        val t2z = arrayOf(-1.0, -2.0, 1.0, 1.0)

        val t3 = arrayOf(
            arrayOf(2.0, -1.0, 3.0, 4.0, 10.0),
            arrayOf(1.0, 1.0, 1.0, -1.0, 5.0),
            arrayOf(1.0, 2.0, -2.0, 4.0, 12.0),
            arrayOf(2.0, 1.0, -1.0, -1.0, 0.0),
        )
        val t3z = arrayOf(-2.0, -1.0, 1.0, 1.0)

        val compromiseVector = findCompromiseSolution(
            matrices = arrayOf(t1, t2, t3),
            zRows = arrayOf(t1z, t2z, t3z)
        )

        println("COMPROMISE VECTOR")
        printArray(compromiseVector)
        println()

        val expected = arrayOf(1.45, 0.0, 0.0, 0.55)

        Assert.assertTrue(expected.contentDeepEquals(compromiseVector))
    }
}