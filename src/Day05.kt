import kotlin.math.abs

fun main() {

    fun countCells(input: List<String>, countDiagonals: Boolean): Int {
        val grid = Array(1000) { Array(1000) { 0 } }
        input.forEach {
            val (x1, y1, x2, y2) = it.split(Regex("[^0-9]+")).map(String::toInt)
            val (dx, dy) = x2 - x1 to y2 - y1
            for (i in 0..maxOf(abs(dx), abs(dy))) {
                val x = x1 + i * (if (dx == 0) 0 else dx / abs(dx))
                val y = y1 + i * (if (dy == 0) 0 else dy / abs(dy))
                if (dx == 0 || dy == 0 || countDiagonals)
                    grid[x][y]++
            }
        }
        return grid.flatten().count { it >= 2 }
    }

    fun part1(input: List<String>) = countCells(input, false)
    fun part2(input: List<String>) = countCells(input, true)

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
