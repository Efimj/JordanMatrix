package com.example.matrix.main.games

import com.example.matrix.main.ModifiedMatrixHandler.Companion.XYPositions
import kotlin.random.Random

class MatrixGameSimulation {
    companion object {
        data class SimulationResult(
            val firstPlayerDecisionIndexes: Array<Int>,
            val firstPlayerRandom: Array<Double>,
            val secondPlayerDecisionIndexes: Array<Int>,
            val secondaryPlayerRandom: Array<Double>,
            val firstPlayerGain: Array<Double>,
            val cumulativeGain: Array<Double>
        )

        fun simulateMatrixGame(
            matrix: Array<Array<Double>>,
            firstPlayersOdds: Array<Double>,
            secondPlayersOdds: Array<Double>,
            numberOfGames: Int = 50,
        ): SimulationResult {
            require(matrix.size == firstPlayersOdds.size)
            require(matrix.first().size == secondPlayersOdds.size)

            val firstPlayerDecisionIndexes = mutableListOf<Int>()
            val secondPlayerDecisionIndexes = mutableListOf<Int>()

            val firstPlayerGain = mutableListOf<Double>()
            val firstPlayerCumulativeGain = mutableListOf<Double>()

            val firstPlayerRandom = mutableListOf<Double>()
            val secondaryPlayerRandom = mutableListOf<Double>()

            for (number in 0..numberOfGames) {
                val randomFirstPlayer = Random.nextDouble()
                val firstPlayerDecision = MatrixGameSimulation().findSegmentIndex(
                    randomNumber = randomFirstPlayer,
                    segments = firstPlayersOdds
                )
                val randomSecondaryPlayer = Random.nextDouble()
                val secondPlayerDecision = MatrixGameSimulation().findSegmentIndex(
                    randomNumber = randomSecondaryPlayer,
                    segments = secondPlayersOdds
                )

                firstPlayerDecisionIndexes.add(firstPlayerDecision)
                secondPlayerDecisionIndexes.add(secondPlayerDecision)

                firstPlayerRandom.add(randomFirstPlayer)
                secondaryPlayerRandom.add(randomSecondaryPlayer)

                firstPlayerGain.add(matrix[firstPlayerDecision][secondPlayerDecision])
                firstPlayerCumulativeGain.add(firstPlayerGain.last() / number)
            }

            return SimulationResult(
                firstPlayerDecisionIndexes = firstPlayerDecisionIndexes.toTypedArray(),
                firstPlayerRandom = firstPlayerRandom.toTypedArray(),
                secondaryPlayerRandom = secondaryPlayerRandom.toTypedArray(),
                secondPlayerDecisionIndexes = secondPlayerDecisionIndexes.toTypedArray(),
                firstPlayerGain = firstPlayerGain.toTypedArray(),
                cumulativeGain = firstPlayerCumulativeGain.toTypedArray(),
            )
        }

        fun printSimulation(simulation: SimulationResult, xyPositions: XYPositions) {
            println("№     | rand A    | name A   |   rand B   | name B   | gain   | cumulative")
            for ((index, value) in simulation.firstPlayerDecisionIndexes.withIndex()) {
                print("$index   ${MatrixGameSimulation().round(simulation.firstPlayerRandom[index])}   ${xyPositions.rows[simulation.firstPlayerDecisionIndexes[index]]}")
                print("   ${MatrixGameSimulation().round(simulation.secondaryPlayerRandom[index])}   ${xyPositions.cols[simulation.secondPlayerDecisionIndexes[index]]}")
                print("   ${simulation.firstPlayerGain}   ${simulation.cumulativeGain}")
            }
        }
    }

    fun round(input: Double): Double {
        return Math.round(input * 100) / 100.0
    }

    fun findSegmentIndex(randomNumber: Double = Random.nextDouble(), segments: Array<Double>): Int {
        var sum = 0.0
        for ((index, segment) in segments.withIndex()) {
            sum += segment
            if (randomNumber <= sum) {
                return index
            }
        }
        return segments.size - 1
    }
}