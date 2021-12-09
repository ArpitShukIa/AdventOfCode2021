fun main() {

    fun List<String>.neighboursOf(x: Int, y: Int) = listOf(
        x - 1 to y, x + 1 to y, x to y - 1, x to y + 1
    ).filter { (i, j) -> i in indices && j in this[0].indices }

    fun part1(arr: List<String>): Int {
        var sum = 0
        for (i in arr.indices)
            for (j in arr[0].indices)
                if (arr[i][j] < arr.neighboursOf(i, j).minOf { (x, y) -> arr[x][y] })
                    sum += arr[i][j] - '0' + 1
        return sum
    }

    fun part2(arr: List<String>): Int {
        val visited = mutableSetOf<Pair<Int, Int>>()

        fun dfs(x: Int, y: Int): Int {
            if (x to y in visited || arr[x][y] == '9') return 0
            visited.add(x to y)
            return 1 + arr.neighboursOf(x, y).sumOf { (i, j) -> dfs(i, j) }
        }

        return arr.indices.flatMap { i -> arr[0].indices.map { j -> dfs(i, j) } }
            .sortedDescending().let { it[0] * it[1] * it[2] }
    }

    val testInput = readInput("Day09_test")
    checkEquals(part1(testInput), 15)
    checkEquals(part2(testInput), 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
