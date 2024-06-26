package com.example.matrix.main.other

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

        fun cloneArray(array: Array<Array<Int>>): Array<Array<Int>> {
            val newArray = generateTwoDimArray(rows = array.size, cols = array[array.size - 1].size, 0)
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

        fun cloneArray(array: Array<Double>): Array<Double> {
            val newArray = Array(array.size) { 0.0 }
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

        fun removeRow(matrix: Array<Array<Double>>, rowIndex: Int): Array<Array<Double>> {
            return matrix.filterIndexed { index, _ -> index != rowIndex }.toTypedArray()
        }

        fun removeColumn(matrix: Array<Array<Double>>, columnIndex: Int): Array<Array<Double>> {
            return matrix.map { row ->
                row.filterIndexed { index, _ -> index != columnIndex }.toTypedArray()
            }.toTypedArray()
        }

        fun addRow(matrix: Array<Array<Double>>, rowIndex: Int, newRow: Array<Double>): Array<Array<Double>> {
            require(rowIndex >= 0 && rowIndex <= matrix.size) { "Invalid rowIndex" }
            require(newRow.size == matrix[0].size) { "New row size must match the matrix column count" }

            val result = mutableListOf<Array<Double>>()

            for (i in 0 until rowIndex) {
                result.add(matrix[i])
            }

            result.add(newRow)

            for (i in rowIndex until matrix.size) {
                result.add(matrix[i])
            }

            return result.toTypedArray()
        }

        fun addValueAtPosition(array: Array<String>, position: Int, value: String): Array<String> {
            if (position < 0 || position > array.size) {
                throw IndexOutOfBoundsException("Invalid position")
            }

            val resultArray = Array(array.size + 1) { "" }

            for (i in 0 until position) {
                resultArray[i] = array[i]
            }

            resultArray[position] = value

            for (i in position until array.size) {
                resultArray[i + 1] = array[i]
            }

            return resultArray
        }

        fun addRow(matrix: Array<Array<Double>>, newRowPlaceholder: Double): Array<Array<Double>> {
            val result = Array(matrix.size + 1) { Array(matrix[0].size) { newRowPlaceholder } }
            for (i in matrix.indices) {
                result[i] = matrix[i]
            }
            return result
        }

        fun addColumn(matrix: Array<Array<Double>>, newColumnPlaceholder: Double): Array<Array<Double>> {
            val result = Array(matrix.size) { Array(matrix[0].size + 1) { newColumnPlaceholder } }
            for (i in matrix.indices) {
                for (j in matrix[i].indices) {
                    result[i][j] = matrix[i][j]
                }
            }
            return result
        }
    }
}