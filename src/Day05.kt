fun main() {

    fun List<String>.countCells(countDiagonals: Boolean): Int {
        val grid = Array(1000) { Array(1000) { 0 } }
        map { it.split(Regex("[^0-9]+")).map(String::toInt) }
            .forEach { (x1, y1, x2, y2) ->
                when {
                    x1 == x2 -> (minOf(y1, y2)..maxOf(y1, y2)).forEach { grid[x1][it]++ }
                    y1 == y2 -> (minOf(x1, x2)..maxOf(x1, x2)).forEach { grid[it][y1]++ }
                    countDiagonals -> when {
                        x1 < x2 && y1 < y2 -> (x1..x2).forEach { grid[it][y1 + it - x1]++ }
                        x1 < x2 -> (x1..x2).forEach { grid[it][y1 - it + x1]++ }
                        y1 < y2 -> (x2..x1).forEach { grid[it][y2 - it + x2]++ }
                        else -> (x2..x1).forEach { grid[it][y2 + it - x2]++ }
                    }
                }
            }
        return grid.flatten().count { it >= 2 }
    }

    fun part1(input: List<String>) = input.countCells(false)
    fun part2(input: List<String>) = input.countCells(true)

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
