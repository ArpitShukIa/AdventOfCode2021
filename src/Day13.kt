import kotlin.math.abs

fun main() {

    fun solve(input: List<String>, part1: Boolean) {
        val index = input.indexOf("")
        val points = input.take(index).map { it.split(",").map(String::toInt) }
        val folds = input.drop(index + 1).map { s -> s.split(" ").last().let { it[0] to it.drop(2).toInt() } }
        var grid = points.map { it[0] to it[1] }.toSet()
        folds.forEach { (dir, n) ->
            grid = grid.map { (x, y) ->
                if (dir == 'x') n - abs(x - n) to y
                else x to n - abs(y - n)
            }.toSet()
            if (part1) {
                println(grid.size)
                return
            }
        }
        val (x, y) = grid.maxOf { it.first } to grid.maxOf { it.second }
        (0..y).forEach { j -> println((0..x).joinToString(" ") { i -> if (i to j in grid) "#" else " " }) }
    }

    val testInput = readInput("Day13_test")
    solve(testInput, part1 = true)
    solve(testInput, part1 = false)

    val input = readInput("Day13")
    solve(input, part1 = true)
    solve(input, part1 = false)
}

// Output:
// #     #   #     #   #     #       # #     # #       # #         # #   # # # #
// #     #   #   #     #     #         #   #     #   #     #         #         #
// # # # #   # #       #     #         #   #         #     #         #       #
// #     #   #   #     #     #         #   #   # #   # # # #         #     #
// #     #   #   #     #     #   #     #   #     #   #     #   #     #   #
// #     #   #     #     # #       # #       # # #   #     #     # #     # # # #
