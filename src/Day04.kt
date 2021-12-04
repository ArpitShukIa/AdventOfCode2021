fun main() {

    fun getAnswers(input: List<String>): MutableList<Int> {
        val result = mutableListOf<Int>()
        val numbers = input[0].split(",").map { it.toInt() }
        var boards = input.drop(2).windowed(5, 6).map { board ->
            board.flatMap { row ->
                row.trim().split(Regex("[ ]+")).map { it.toInt() to false }
            }
        }
        numbers.forEach { n ->
            boards = boards
                .map { board ->
                    board.map { if (it.first == n) n to true else it }
                }
                .filterNot { board ->
                    val rows = board.chunked(5)
                    val columns = List(5) { col -> rows.map { it[col] } }
                    val bingo = rows.any { row -> row.all { it.second } } || columns.any { col -> col.all { it.second } }
                    if (bingo) result += n * board.sumOf { if (it.second) 0 else it.first }
                    bingo
                }
        }
        return result
    }

    fun part1(input: List<String>) = getAnswers(input).first()
    fun part2(input: List<String>) = getAnswers(input).last()

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
