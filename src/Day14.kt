fun main() {

    fun <K> MutableMap<K, Long>.incr(key: K, by: Long = 1) {
        put(key, getOrDefault(key, 0) + by)
    }

    fun solve(input: List<String>, times: Int): Long {
        val map = input.drop(2).map { it.split(" -> ") }.associate { it[0] to it[1] }
        var pairFreq = mutableMapOf<String, Long>()
        input[0].windowed(2).forEach { pairFreq.incr(it) }
        repeat(times) {
            val newMap = mutableMapOf<String, Long>()
            pairFreq.forEach { (k, v) ->
                newMap.incr(k[0] + map[k]!!, v)
                newMap.incr(map[k]!! + k[1], v)
            }
            pairFreq = newMap
        }
        val charFreq = mutableMapOf<Char, Long>()
        pairFreq.forEach { (k, v) -> charFreq.incr(k[0], v) }
        charFreq.incr(input[0].last())
        return charFreq.values.sorted().let { it.last() - it.first() }
    }

    fun part1(input: List<String>) = solve(input, 10)
    fun part2(input: List<String>) = solve(input, 40)

    val testInput = readInput("Day14_test")
    checkEquals(part1(testInput), 1588)
    checkEquals(part2(testInput), 2188189693529)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
