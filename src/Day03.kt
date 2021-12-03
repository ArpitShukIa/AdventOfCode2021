fun main() {
    fun List<String>.findMostAndLeastCommonBitAt(col: Int): Pair<Char, Char> {
        val ones = count { it[col] == '1' }
        return if (ones >= size - ones) '1' to '0' else '0' to '1'
    }

    fun List<Char>.decimal() = joinToString("").toInt(2)

    fun part1(input: List<String>): Int {
        val bits = List(input[0].length) { input.findMostAndLeastCommonBitAt(it) }
        return bits.map { it.first }.decimal() * bits.map { it.second }.decimal()
    }

    fun List<String>.filterByBit(getBit: (Pair<Char, Char>) -> Char): Int {
        var list = this
        var c = -1
        while (list.size > 1) {
            val bits = list.findMostAndLeastCommonBitAt(++c)
            list = list.filter { it[c] == getBit(bits) }
        }
        return list[0].toInt(2)
    }

    fun part2(input: List<String>): Int {
        return input.filterByBit { it.first } * input.filterByBit { it.second }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
