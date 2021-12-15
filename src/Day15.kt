import java.util.PriorityQueue

fun main() {

    fun solve(input: List<String>, scale: Int): Int {
        val n = input.size
        val grid = (0 until n * scale).map { i ->
            (0 until n * scale).map { j ->
                (input[i % n][j % n] - '0' + (i / n + j / n)).let { if (it < 10) it else it - 9 }
            }
        }
        val pq = PriorityQueue<Triple<Int, Int, Int>> { a, b -> a.third - b.third }
        val risk = Array(grid.size) { Array(grid.size) { Int.MAX_VALUE } }
        risk[0][0] = 0
        pq.add(Triple(0, 0, 0))
        while (pq.isNotEmpty()) {
            val (x, y, dist) = pq.poll()
            listOf(x to y + 1, x to y - 1, x + 1 to y, x - 1 to y).forEach { (X, Y) ->
                if (X in grid.indices && Y in grid[0].indices && risk[X][Y] > dist + grid[X][Y]) {
                    risk[X][Y] = dist + grid[X][Y]
                    pq.add(Triple(X, Y, risk[X][Y]))
                }
            }
        }
        return risk.last().last()
    }

    fun part1(input: List<String>) = solve(input, scale = 1)
    fun part2(input: List<String>) = solve(input, scale = 5)

    val testInput = readInput("Day15_test")
    checkEquals(part1(testInput), 40)
    checkEquals(part2(testInput), 315)

    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}
