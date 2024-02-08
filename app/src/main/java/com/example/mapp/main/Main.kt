import ArrayHelper.Companion.fillTwoDimArrayRandomly
import ArrayHelper.Companion.generateTwoDimArray
import ArrayHelper.Companion.printArray
import com.example.mapp.main.MatrixHandler.Companion.inverseMatrix

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

