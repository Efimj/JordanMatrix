import kotlin.math.round
import kotlin.random.Random

class ArrayHelper {
    companion object {
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

        fun roundArray(array: Array<Array<Double>>, decimalPlaces: Int) {
            val factor = Math.pow(10.0, decimalPlaces.toDouble())

            for (i in array.indices) {
                for (j in array[i].indices) {
                    array[i][j] = Math.round(array[i][j] * factor) / factor
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
                    repeat(maxSymbolsElement - stringToOutput.length - if (stringToOutput.first() != '-') 1 else 0) {
                        print(
                            " "
                        )
                    }
                }
                println()
            }
        }
    }
}