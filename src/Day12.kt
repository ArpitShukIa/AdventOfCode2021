fun main() {

    fun solve(input: List<String>, part1: Boolean): Int {
        val g = mutableMapOf<String, List<String>>()
        input.map { it.split("-") }.forEach { (a, b) ->
            g[a] = (g[a] ?: listOf()) + b
            g[b] = (g[b] ?: listOf()) + a
        }
        var count = 0
        val queue = ArrayDeque<Triple<String, List<String>, Boolean>>()
        queue.add(Triple("start", listOf("start"), false))
        while (queue.isNotEmpty()) {
            val (curr, visited, twice) = queue.removeFirst()
            if (curr == "end") count++
            else
                g[curr]!!.forEach { next ->
                    if (next !in visited) {
                        val newVisited = if (next == next.lowercase()) visited + next else visited
                        queue.add(Triple(next, newVisited, twice))
                    } else if (!twice && next != "start" && !part1) {
                        queue.add(Triple(next, visited, true))
                    }
                }
        }
        return count
    }

    fun part1(input: List<String>) = solve(input, true)
    fun part2(input: List<String>) = solve(input, false)

    val testInput = readInput("Day12_test")
    checkEquals(part1(testInput), 10)
    checkEquals(part2(testInput), 36)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
