import kotlin.math.round
import kotlin.random.Random

inline fun <reified T> generateTwoDimArray(rows: Int, cols: Int, initialValue: T): Array<Array<T>> {
    return Array(rows) { Array(cols) { initialValue } }
}

fun fillTwoDimArrayRandomly(array: Array<Array<Double>>, minValue: Double, maxValue: Double) {
    require(minValue < maxValue) { "minValue < maxValue" }

    for (i in array.indices) {
        for (j in array[i].indices) {
            var randomValue: Double
            do {
                randomValue = round(Random.nextDouble(minValue, maxValue) * 100) / 100
            } while (randomValue == 0.0)

            array[i][j] = randomValue
        }
    }
}

fun <T> printArray(array: Array<Array<T>>, separator: String = "   ") {
    var maxSymbolsElement = 0
    for (row in array) {
        for (element in row) {
            element.toString().let {
                maxSymbolsElement = maxSymbolsElement.coerceAtLeast(it.length)
            }
        }
    }
    for (row in array) {
        for (element in row) {
            val stringToOutput = element.toString()
            if (stringToOutput.first() != '-') print(" ")
            print(stringToOutput + separator)
            repeat(maxSymbolsElement - stringToOutput.length - if (stringToOutput.first() != '-') 1 else 0) { print(" ") }
        }
        println()
    }
}

fun getRefactoredMatrix(array: Array<Array<Double>>) {
    var currentValue: Double
    var countIterations = 1
    for (rowI in array.indices) {
        currentValue = array[rowI][rowI]
        array[rowI][rowI] = 1.0
        for (j in array[rowI].indices) {
            if (rowI != j) array[rowI][j] = -array[rowI][j]
        }
        for (r1 in array.indices) {
            for (c1 in array[r1].indices) {
                if (r1 != rowI && c1 != rowI)
                    array[r1][c1] = array[r1][c1] * currentValue - array[r1][rowI] * array[rowI][c1]
            }
        }
        for (r1 in array.indices) {
            for (c1 in array[r1].indices) {
                array[r1][c1] = array[r1][c1] / currentValue
            }
        }
        countIterations++

//        val swapE = array[0][rowI]
//        array[0][rowI] = array[rowI][0]
//        array[rowI][0] = swapE
    }
}