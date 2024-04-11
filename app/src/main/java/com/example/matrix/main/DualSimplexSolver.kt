package com.example.matrix.main


class DualSimplexSolver {
    companion object {
        fun findDualResultsFor(
            name: String = "y",
            output: ModifiedMatrixHandler.Companion.Solve,
        ): Array<Double> {
            if (output.matrix == null) return emptyArray()

            val res = Array(output.xyPos.rows.size) { 0.0 }
            for (index in output.xyPos.cols.indices) {
                if (output.xyPos.cols[index].startsWith(name)) {
                    val digitAfterX = output.xyPos.cols[index].substringAfter(name)
                    try {
                        val result = digitAfterX.toInt()
                        res[result - 1] = output.matrix.last()[index]
                    } catch (e: NumberFormatException) {
                        println("NumberFormatException")
                    }
                }
            }
            return res
        }

        fun findResultsFor(
            output: ModifiedMatrixHandler.Companion.Solve,
        ): Array<Double> {
            val res = Array(output.xyPos.rows.size) { 0.0 }
            for (index in output.xyPos.rows.indices) {
                if (output.xyPos.rows[index].startsWith("x")) {
                    val digitAfterX = output.xyPos.rows[index].substringAfter("x")
                    try {
                        val result = digitAfterX.toInt()
                        res[result - 1] = output.matrix?.get(index)?.last()!!
                    } catch (e: NumberFormatException) {
                        println("NumberFormatException")
                    }
                }
            }
            return res
        }
    }
}