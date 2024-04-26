package com.example.matrixGame

import ArrayHelper.Companion.printArray
import com.example.matrix.main.games.GamesWithNature.Companion.solveByAbrahamWald
import com.example.matrix.main.games.GamesWithNature.Companion.solveByAbrahamWaldMaxMax
import com.example.matrix.main.games.GamesWithNature.Companion.solveByAdolfHurwitz
import com.example.matrix.main.games.GamesWithNature.Companion.solveByLeonardJimmieSavage
import com.example.matrix.main.games.GamesWithNature.Companion.solveByThomasBayes
import com.example.matrix.main.games.GamesWithNature.Companion.solvePierreSimondeLaplace
import org.junit.Assert
import org.junit.Test

class GamesWithNatureTests {
    @Test
    fun solveByAbrahamWaldTest1() {
        println()
        println("solveByAbrahamWaldTest1")

        val inputMatrix = arrayOf(
            arrayOf(-1.0, 1.0, 1.0, 4.0),
            arrayOf(-1.0, -2.0, 2.0, 3.0),
            arrayOf(3.0, -1.0, 3.0, 2.0)
        )

        val result = solveByAbrahamWald(matrix = inputMatrix)

        println("Input")
        printArray(inputMatrix)
        println()
        println("Strategy for solve")
        println(result + 1)

        val expected = 0

        Assert.assertTrue(result == expected)
    }

    @Test
    fun solveByAbrahamWaldTest2() {
        println()
        println("solveByAbrahamWaldTest2 - Variant")

        val inputMatrix = arrayOf(
            arrayOf(1.0, 1.0, 2.0, 6.0),
            arrayOf(1.0, -2.0, 3.0, 5.0),
            arrayOf(-3.0, 1.0, 2.0, 2.0)
        )

        val result = solveByAbrahamWald(matrix = inputMatrix)

        println("Input")
        printArray(inputMatrix)
        println()
        println("Strategy for solve")
        println(result + 1)

        val expected = 0

        Assert.assertTrue(result == expected)
    }

    @Test
    fun solveByAbrahamWaldMaxMaxTest1() {
        println()
        println("solveByAbrahamWaldMaxMaxTest1")

        val inputMatrix = arrayOf(
            arrayOf(-1.0, 1.0, 1.0, 4.0),
            arrayOf(-1.0, -2.0, 2.0, 3.0),
            arrayOf(3.0, -1.0, 3.0, 2.0)
        )

        val result = solveByAbrahamWaldMaxMax(matrix = inputMatrix)

        println("Input")
        printArray(inputMatrix)
        println()
        println("Strategy for solve")
        println(result + 1)

        val expected = 0

        Assert.assertTrue(result == expected)
    }

    @Test
    fun solveByAbrahamWaldMaxMaxTest2() {
        println()
        println("solveByAbrahamWaldMaxMaxTest2 - Variant")

        val inputMatrix = arrayOf(
            arrayOf(1.0, 1.0, 2.0, 6.0),
            arrayOf(1.0, -2.0, 3.0, 5.0),
            arrayOf(-3.0, 1.0, 2.0, 2.0)
        )

        val result = solveByAbrahamWaldMaxMax(matrix = inputMatrix)

        println("Input")
        printArray(inputMatrix)
        println()
        println("Strategy for solve")
        println(result + 1)

        val expected = 0

        Assert.assertTrue(result == expected)
    }

    @Test
    fun solveByAdolfHurwitzTest1() {
        println()
        println("solveByAdolfHurwitzTest1")

        val inputMatrix = arrayOf(
            arrayOf(-1.0, 1.0, 1.0, 4.0),
            arrayOf(-1.0, -2.0, 2.0, 3.0),
            arrayOf(3.0, -1.0, 3.0, 2.0)
        )

        val result = solveByAdolfHurwitz(matrix = inputMatrix, coefficient = 0.3)

        println("Input")
        printArray(inputMatrix)
        println()
        println("Strategy for solve")
        println(result + 1)

        val expected = 0

        Assert.assertTrue(result == expected)
    }

    @Test
    fun solveByAdolfHurwitzTest2() {
        println()
        println("solveByAdolfHurwitzTest2 - Variant")

        val inputMatrix = arrayOf(
            arrayOf(1.0, 1.0, 2.0, 6.0),
            arrayOf(1.0, -2.0, 3.0, 5.0),
            arrayOf(-3.0, 1.0, 2.0, 2.0)
        )

        val result = solveByAdolfHurwitz(matrix = inputMatrix, coefficient = 0.3)

        println("Input")
        printArray(inputMatrix)
        println()
        println("Strategy for solve")
        println(result + 1)

        val expected = 0

        Assert.assertTrue(result == expected)
    }

    @Test
    fun solveByLeonardJimmieSavageTest1() {
        println()
        println("solveByLeonardJimmieSavageTest1")

        val inputMatrix = arrayOf(
            arrayOf(-1.0, 1.0, 1.0, 4.0),
            arrayOf(-1.0, -2.0, 2.0, 3.0),
            arrayOf(3.0, -1.0, 3.0, 2.0)
        )

        val result = solveByLeonardJimmieSavage(matrix = inputMatrix)

        println("Input")
        printArray(inputMatrix)
        println()
        println("Strategy for solve")
        println(result + 1)

        val expected = 2

        Assert.assertTrue(result == expected)
    }

    @Test
    fun solveByLeonardJimmieSavageTest2() {
        println()
        println("solveByLeonardJimmieSavageTest2 - Variant")

        val inputMatrix = arrayOf(
            arrayOf(1.0, 1.0, 2.0, 6.0),
            arrayOf(1.0, -2.0, 3.0, 5.0),
            arrayOf(-3.0, 1.0, 2.0, 2.0)
        )

        val result = solveByLeonardJimmieSavage(matrix = inputMatrix)

        println("Input")
        printArray(inputMatrix)
        println()
        println("Strategy for solve")
        println(result + 1)

        val expected = 0

        Assert.assertTrue(result == expected)
    }

    @Test
    fun solveByThomasBayesTest1() {
        println()
        println("solveByThomasBayesTest1")

        val inputMatrix = arrayOf(
            arrayOf(-1.0, 1.0, 1.0, 4.0),
            arrayOf(-1.0, -2.0, 2.0, 3.0),
            arrayOf(3.0, -1.0, 3.0, 2.0)
        )

        val result = solveByThomasBayes(matrix = inputMatrix, probabilities = arrayOf(0.2, 0.4, 0.1, 0.3))

        println("Input")
        printArray(inputMatrix)
        println()
        println("Strategy for solve")
        println(result + 1)

        val expected = 0

        Assert.assertTrue(result == expected)
    }

    @Test
    fun solveByThomasBayesTest2() {
        println()
        println("solveByThomasBayesTest2 - Variant")

        val inputMatrix = arrayOf(
            arrayOf(1.0, 1.0, 2.0, 6.0),
            arrayOf(1.0, -2.0, 3.0, 5.0),
            arrayOf(-3.0, 1.0, 2.0, 2.0)
        )

        val result = solveByThomasBayes(matrix = inputMatrix, probabilities = arrayOf(0.2, 0.4, 0.1, 0.3))

        println("Input")
        printArray(inputMatrix)
        println()
        println("Strategy for solve")
        println(result + 1)

        val expected = 0

        Assert.assertTrue(result == expected)
    }

    @Test
    fun solvePierreSimondeLaplaceTest1() {
        println()
        println("solvePierreSimondeLaplaceTest1")

        val inputMatrix = arrayOf(
            arrayOf(-1.0, 1.0, 1.0, 4.0),
            arrayOf(-1.0, -2.0, 2.0, 3.0),
            arrayOf(3.0, -1.0, 3.0, 2.0)
        )

        val result = solvePierreSimondeLaplace(matrix = inputMatrix)

        println("Input")
        printArray(inputMatrix)
        println()
        println("Strategy for solve")
        println(result + 1)

        val expected = 2

        Assert.assertTrue(result == expected)
    }

    @Test
    fun solvePierreSimondeLaplaceTest2() {
        println()
        println("solvePierreSimondeLaplaceTest2 - Variant")

        val inputMatrix = arrayOf(
            arrayOf(1.0, 1.0, 2.0, 6.0),
            arrayOf(1.0, -2.0, 3.0, 5.0),
            arrayOf(-3.0, 1.0, 2.0, 2.0)
        )

        val result = solvePierreSimondeLaplace(matrix = inputMatrix)

        println("Input")
        printArray(inputMatrix)
        println()
        println("Strategy for solve")
        println(result + 1)

        val expected = 0

        Assert.assertTrue(result == expected)
    }
}