fun main() {

    fun countFish(input: List<String>, days: Int): Long {
        val day = Array(9) { 0L }
        input[0].split(",").forEach { day[it.toInt()]++ }
        repeat(days) {
            val x = day[0]
            (0..7).forEach { day[it] = day[it + 1] }
            day[8] = x
            day[6] += x
        }
        return day.sum()
    }

    fun part1(input: List<String>) = countFish(input, 80)
    fun part2(input: List<String>) = countFish(input, 256)

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
