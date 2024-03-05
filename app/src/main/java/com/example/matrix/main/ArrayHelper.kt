import kotlin.math.round
import kotlin.random.Random

class ArrayHelper {
    companion object {
        inline fun <reified T> generateTwoDimArray(rows: Int, cols: Int, initialValue: T): Array<Array<T>> {
            return Array(rows) { Array(cols) { initialValue } }
        }

        fun fillTwoDimArrayRandomly(
            array: Array<Array<Double>>,
            minValue: Double,
            maxValue: Double,
            round: Int? = null
        ) {
            require(minValue < maxValue) { "minValue < maxValue" }

            for (i in array.indices) {
                for (j in array[i].indices) {
                    var randomValue: Double
                    do {
                        randomValue = Random.nextDouble(minValue, maxValue)
                    } while (randomValue == 0.0)

                    array[i][j] = randomValue
                }
            }
            if (round != null)
                roundArray(array, round)
        }

        fun roundArray(array: Array<Array<Double>>, decimalPlaces: Int) {
            val factor = Math.pow(10.0, decimalPlaces.toDouble())

            for (i in array.indices) {
                for (j in array[i].indices) {
                    array[i][j] = Math.round(array[i][j] * factor) / factor
                }
            }
        }

        fun roundArray(array: Array<Double>, decimalPlaces: Int) {
            val factor = Math.pow(10.0, decimalPlaces.toDouble())

            for (i in array.indices) {
                array[i] = Math.round(array[i] * factor) / factor
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

        fun <T> printArray(array: Array<T>, separator: String = "   ") {
            var maxSymbolsElement = 0
            for (element in array) {
                element.toString().let {
                    maxSymbolsElement = maxSymbolsElement.coerceAtLeast(it.length)
                }
            }
            for (element in array) {
                val stringToOutput = element.toString()
                if (stringToOutput.first() != '-') print(" ")
                print(stringToOutput + separator)
                repeat(maxSymbolsElement - stringToOutput.length - if (stringToOutput.first() != '-') 1 else 0) {
                    print(
                        " "
                    )
                }
            }
        }

        fun cloneArray(array: Array<Array<String>>): Array<Array<String>> {
            val newArray = generateTwoDimArray(rows = array.size, cols = array[array.size - 1].size, "")
            for (i in array.indices) {
                for (j in array[i].indices) {
                    newArray[i][j] = array[i][j]
                }
            }
            return newArray
        }

        fun cloneArray(array: Array<Array<Double>>): Array<Array<Double>> {
            val newArray = generateTwoDimArray(rows = array.size, cols = array[array.size - 1].size, 0.0)
            for (i in array.indices) {
                for (j in array[i].indices) {
                    newArray[i][j] = array[i][j]
                }
            }
            return newArray
        }

        fun cloneArray(array: Array<Int>): Array<Int> {
            val newArray = Array(array.size) { 0 }
            for (i in array.indices) {
                newArray[i] = array[i]
            }
            return newArray
        }

        fun cloneArray(array: Array<String>): Array<String> {
            val newArray = Array(array.size) { "" }
            for (i in array.indices) {
                newArray[i] = array[i]
            }
            return newArray
        }

        fun <T> arrayToString(array: Array<Array<T>>, separator: String = "   "): String {
            var maxSymbolsElement = 0
            for (row in array) {
                for (element in row) {
                    element.toString().let {
                        maxSymbolsElement = maxSymbolsElement.coerceAtLeast(it.length)
                    }
                }
            }

            var output = ""
            for (row in array) {
                for (element in row) {
                    val stringToOutput = element.toString()
                    if (stringToOutput.first() != '-') output += " "
                    output += stringToOutput + separator
                    repeat(maxSymbolsElement - stringToOutput.length - if (stringToOutput.first() != '-') 1 else 0) {
                        output += " "
                    }
                }
                output += "\n"
            }
            return output
        }

        fun <T> arrayToString(array: Array<T>, separator: String = "   "): String {
            var maxSymbolsElement = 0
            for (element in array) {
                element.toString().let {
                    maxSymbolsElement = maxSymbolsElement.coerceAtLeast(it.length)
                }
            }

            var output = ""
            for (element in array) {
                val stringToOutput = element.toString()
                if (stringToOutput.first() != '-') output += " "
                output += stringToOutput + separator
                repeat(maxSymbolsElement - stringToOutput.length - if (stringToOutput.first() != '-') 1 else 0) {
                    output += " "
                }
            }
            return output
        }

        fun removeColumn(matrix: Array<Array<Double>>, columnIndex: Int): Array<Array<Double>> {
            return matrix.map { row ->
                row.filterIndexed { index, _ -> index != columnIndex }.toTypedArray()
            }.toTypedArray()
        }
    }
}