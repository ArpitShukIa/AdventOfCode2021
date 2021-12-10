fun main() {

    val closing = "()[]{}<>".chunked(2).associate { it[0] to it[1] }

    fun String.getIllegalCharAndStack(): Pair<Char?, List<Char>?> {
        val stack = mutableListOf<Char>()
        for (c in this) {
            if (c in "({[<") stack.add(c)
            else if (c == closing[stack.last()]) stack.removeLast()
            else return c to null
        }
        return null to stack
    }

    fun part1(input: List<String>): Int {
        val points = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
        return input.sumOf { it.getIllegalCharAndStack().first?.let { points[it]!! } ?: 0 }
    }

    fun part2(input: List<String>): Long {
        val points = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)
        return input.mapNotNull {
            it.getIllegalCharAndStack().second?.foldRight(0L) { c, sum ->
                sum * 5 + points[closing[c]!!]!!
            }
        }.sorted().let { it[it.size / 2] }
    }

    val testInput = readInput("Day10_test")
    checkEquals(part1(testInput), 26397)
    checkEquals(part2(testInput), 288957)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
