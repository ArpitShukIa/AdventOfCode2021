fun main() {

    val allIndices = (0..9).flatMap { i -> (0..9).map { j -> i to j } }

    fun solve(input: List<String>, isPart1: Boolean): Int {
        val arr = Array(10) { i -> Array(10) { j -> input[i][j] - '0' } }
        var count = 0
        fun flash(x: Int, y: Int) {
            count++
            arr[x][y] = -1
            for (i in (x - 1)..(x + 1))
                for (j in (y - 1)..(y + 1))
                    if (i in 0..9 && j in 0..9 && arr[i][j] != -1)
                        if (++arr[i][j] >= 10)
                            flash(i, j)
        }
        repeat(if (isPart1) 100 else Int.MAX_VALUE) { step ->
            allIndices.forEach { (i, j) -> arr[i][j]++ }
            allIndices.forEach { (i, j) -> if (arr[i][j] == 10) flash(i, j) }
            if (arr.flatten().all { it == -1 } && !isPart1) return step + 1
            allIndices.forEach { (i, j) -> if (arr[i][j] == -1) arr[i][j] = 0 }
        }
        return count
    }

    fun part1(input: List<String>) = solve(input, true)
    fun part2(input: List<String>) = solve(input, false)

    val testInput = readInput("Day11_test")
    checkEquals(part1(testInput), 1656)
    checkEquals(part2(testInput), 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
