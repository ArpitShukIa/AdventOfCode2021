fun main() {

    fun part1(input: List<String>): Int {
        val pos = Array(2) { input[it].substringAfterLast(" ").toInt() }
        val score = Array(2) { 0 }
        var (rolls, dice, i) = Triple(0, 1, 0)
        fun roll() = dice.also { dice = dice % 100 + 1; rolls++ }
        while (true) {
            pos[i] = (pos[i] + roll() + roll() + roll() - 1) % 10 + 1
            score[i] += pos[i]
            if (score[i] >= 1000) return score[1 - i] * rolls
            i = 1 - i
        }
    }

    fun part2(input: List<String>): Long {
        data class Universe(val p1: Int, val p2: Int, val s1: Int, val s2: Int)

        val pos = Array(2) { input[it].substringAfterLast(" ").toInt() }
        val dp = mutableMapOf<Universe, Pair<Long, Long>>()
        fun solve(u: Universe): Pair<Long, Long> {
            dp[u]?.let { return it }
            if (u.s1 >= 21) return 1L to 0L
            if (u.s2 >= 21) return 0L to 1L
            var ans = 0L to 0L
            for (d1 in 1..3) for (d2 in 1..3) for (d3 in 1..3) {
                val newP1 = (u.p1 + d1 + d2 + d3 - 1) % 10 + 1
                val newS1 = u.s1 + newP1
                val (x, y) = solve(Universe(u.p2, newP1, u.s2, newS1))
                ans = ans.first + y to ans.second + x
            }
            return ans.also { dp[u] = it }
        }
        return solve(Universe(pos[0], pos[1], 0, 0)).let { maxOf(it.first, it.second) }
    }

    val testInput = readInput("Day21_test")
    checkEquals(part1(testInput), 739785)
    checkEquals(part2(testInput), 444356092776315)

    val input = readInput("Day21")
    println(part1(input))
    println(part2(input))
}
