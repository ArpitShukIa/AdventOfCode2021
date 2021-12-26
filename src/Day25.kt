fun main() {

    fun part1(input: List<String>): Int {
        val arr = input.map { it.toMutableList() }
        val (x, y) = arr.size to arr[0].size
        var count = 0
        fun move(ch: Char, dx: Int, dy: Int): Boolean {
            val moves = mutableListOf<Pair<Int, Int>>()
            for (i in arr.indices) for (j in arr[0].indices)
                if (arr[i][j] == ch && arr[(i + dx) % x][(j + dy) % y] == '.')
                    moves.add(i to j)
            for ((i, j) in moves) {
                arr[i][j] = '.'
                arr[(i + dx) % x][(j + dy) % y] = ch
            }
            return moves.isNotEmpty()
        }
        while (true) {
            count++
            val movedEast = move('>', 0, 1)
            val movedSouth = move('v', 1, 0)
            if (!movedEast && !movedSouth) return count
        }
    }

    val testInput = readInput("Day25_test")
    checkEquals(part1(testInput), 58)

    val input = readInput("Day25")
    println(part1(input))
}
