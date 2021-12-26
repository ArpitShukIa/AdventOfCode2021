fun main() {

    fun solve(input: List<String>): Pair<String, String> {
        val digits = mutableMapOf<Int, Pair<Int, Int>>()
        val stack = mutableListOf<Pair<Int, Int>>()
        var (push, sub, dig) = Triple(false, 0, 0)
        input.forEachIndexed { i, line ->
            val operand = line.substringAfterLast(" ")
            if (i % 18 == 4) push = operand == "1"
            if (i % 18 == 5) sub = operand.toInt()
            if (i % 18 == 15) {
                if (push) {
                    stack.add(dig to operand.toInt())
                } else {
                    val (sibling, add) = stack.removeLast()
                    val diff = add + sub
                    if (diff < 0) {
                        digits[sibling] = -diff + 1 to 9
                        digits[dig] = 1 to 9 + diff
                    } else {
                        digits[sibling] = 1 to 9 - diff
                        digits[dig] = 1 + diff to 9
                    }
                }
                dig++
            }
        }
        return digits.toSortedMap().values.unzip().let { (a, b) ->
            b.joinToString("") to a.joinToString("")
        }
    }

    fun part1(input: List<String>) = solve(input).first
    fun part2(input: List<String>) = solve(input).second

    val input = readInput("Day24")
    println(part1(input))
    println(part2(input))
}
