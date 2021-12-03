fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map(String::toInt)
            .windowed(size = 2) { it[1] > it[0] }
            .count { it }
    }

    fun part2(input: List<String>): Int {
        return input
            .map(String::toInt)
            .windowed(size = 3) { it.sum() }
            .windowed(size = 2) { it[1] > it[0] }
            .count { it }
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
