import kotlin.math.abs

fun main() {
    data class Burrow(
        val roomSize: Int,
        val hallway: Map<Int, Char>,
        val rooms: Map<Int, List<Char>>,
    )

    val roomAssignments = mapOf(2 to 'A', 4 to 'B', 6 to 'C', 8 to 'D')

    fun getPossibleMoves(burrow: Burrow): List<Pair<Int, Int>> = buildList {
        fun pathExists(i1: Int, i2: Int): Boolean {
            val min = minOf(i1, i2)
            val max = maxOf(i1, i2)
            return burrow.hallway.all { (index, value) ->
                index !in (min + 1) until max || value == '.'
            }
        }

        burrow.hallway.forEach { (hindex, hvalue) ->
            burrow.rooms.forEach inner@{ (rindex, rvalue) ->
                // Both empty
                if (hvalue == '.' && rvalue.isEmpty()) {
                    return@inner
                }

                // Hallway to room (room compatible)
                if (hvalue != '.') {
                    if (hvalue == roomAssignments[rindex]
                        && rvalue.all { it == hvalue }
                        && pathExists(hindex, rindex)
                    ) {
                        add(hindex to rindex)
                    }
                    return@inner
                }

                // Room to hallway (hallway empty)
                if (pathExists(rindex, hindex)
                    && rvalue.any { it != roomAssignments[rindex] }
                ) {
                    add(rindex to hindex)
                }
            }
        }
    }

    fun execute(burrow: Burrow, move: Pair<Int, Int>): Pair<Burrow, Int> {
        fun costFor(amphipod: Char) = when (amphipod) {
            'A' -> 1
            'B' -> 10
            'C' -> 100
            'D' -> 1000
            else -> error("Invalid amphipod provided")
        }

        fun roomToHallway(burrow: Burrow, ri: Int, hi: Int): Pair<Burrow, Int> {
            val room = burrow.rooms.getValue(ri)
            val amphipod = room.first()

            val upwards = burrow.roomSize - room.size + 1
            val sideways = abs(ri - hi)
            val cost = (upwards + sideways) * costFor(amphipod)

            val modifiedBurrow = burrow.copy(
                rooms = burrow.rooms.plus(ri to room.drop(1)),
                hallway = burrow.hallway.plus(hi to amphipod),
            )

            return Pair(modifiedBurrow, cost)
        }

        fun hallwayToRoom(burrow: Burrow, hi: Int, ri: Int): Pair<Burrow, Int> {
            val room = burrow.rooms.getValue(ri)
            val amphipod = burrow.hallway.getValue(hi)

            val sideways = abs(ri - hi)
            val downwards = burrow.roomSize - room.size
            val cost = (sideways + downwards) * costFor(amphipod)

            val modifiedBurrow = burrow.copy(
                rooms = burrow.rooms.plus(ri to listOf(amphipod) + room),
                hallway = burrow.hallway.plus(hi to '.'),
            )

            return Pair(modifiedBurrow, cost)
        }

        return if (move.first in roomAssignments.keys) {
            roomToHallway(burrow, move.first, move.second)
        } else {
            hallwayToRoom(burrow, move.first, move.second)
        }
    }

    fun organizeAmphipods(input: List<String>): Int {
        val rooms = input.drop(2).dropLast(1).map { line ->
            line.filter { char -> char in 'A'..'D' }
        }
        val a = rooms.map { it[0] }
        val b = rooms.map { it[1] }
        val c = rooms.map { it[2] }
        val d = rooms.map { it[3] }

        val initialBurrow = Burrow(
            roomSize = a.size,
            hallway = mapOf(
                0 to '.',
                1 to '.',
                3 to '.',
                5 to '.',
                7 to '.',
                9 to '.',
                10 to '.',
            ),
            rooms = mapOf(
                2 to a,
                4 to b,
                6 to c,
                8 to d,
            ),
        )

        var bestCost = Int.MAX_VALUE
        val seen = mutableMapOf<Burrow, Int>()

        fun simulate(prevBurrow: Burrow, move: Pair<Int, Int>, prevCost: Int): Int {
            if (prevCost >= bestCost) return Int.MAX_VALUE

            val (burrow, cost) = execute(prevBurrow, move)
            val totalCost = prevCost + cost

            if (burrow in seen) {
                if (seen.getValue(burrow) <= totalCost) {
                    return Int.MAX_VALUE
                }
            }
            seen[burrow] = totalCost

            if (roomAssignments.all { (ri, amph) ->
                    val r = burrow.rooms.getValue(ri)
                    r.size == burrow.roomSize && r.all { it == amph }
                }) {
                if (totalCost < bestCost) bestCost = totalCost
                return totalCost
            }

            val moves = getPossibleMoves(burrow)
            if (moves.isEmpty()) return Int.MAX_VALUE
            return moves.minOf { simulate(burrow, it, totalCost) }
        }

        return getPossibleMoves(initialBurrow).minOf { simulate(initialBurrow, it, 0) }
    }

    fun part1(input: List<String>) = organizeAmphipods(input)

    fun part2(input: List<String>): Int {
        val newInput = input.take(3) + "  #D#C#B#A#" + "  #D#B#A#C#" + input.takeLast(2)
        return organizeAmphipods(newInput)
    }

    val testInput = readInput("Day23_test")
    checkEquals(part1(testInput), 12521)
    checkEquals(part2(testInput), 44169)

    val input = readInput("Day23")
    println(part1(input))
    println(part2(input))
}
