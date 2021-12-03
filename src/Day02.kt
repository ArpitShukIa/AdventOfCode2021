private val String.dir get() = substringBefore(' ')
private val String.step get() = substringAfter(' ').toInt()

fun main() {

    fun part1(input: List<String>): Int {
        val commands = input.groupBy(String::dir, String::step)
        val x = commands["forward"]!!.sum()
        val y = commands["down"]!!.sum() - commands["up"]!!.sum()
        return x * y
    }

    fun part2(input: List<String>): Int {
        var (x, y, aim) = Triple(0, 0, 0)
        input.forEach {
            val (dir, step) = it.dir to it.step
            when (dir) {
                "forward" -> {
                    x += step
                    y += aim * step
                }
                "up" -> aim -= step
                "down" -> aim += step
            }
        }
        return x * y
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
