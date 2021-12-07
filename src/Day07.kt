import kotlin.math.abs

fun main() {

    fun solve(input: List<String>, costOf: (Int) -> Int): Int {
        val list = input[0].split(",").map { it.toInt() }
        fun fuelCost(n: Int) = list.sumOf { costOf(abs(it - n)) }
        return (0..2000).fold(Int.MAX_VALUE) { old, new -> minOf(old, fuelCost(new)) }
    }

    fun part1(input: List<String>) = solve(input) { it }
    fun part2(input: List<String>) = solve(input) { it * (it + 1) / 2 }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
