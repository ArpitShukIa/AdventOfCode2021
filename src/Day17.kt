fun main() {

    fun solve(input: String): Pair<Int, Int> {
        val regex = Regex("target area: x=(\\d+)..(\\d+), y=([-\\d]+)..([-\\d]+)")
        val (x1, x2, y1, y2) = regex.matchEntire(input)!!.groupValues.drop(1).map(String::toInt)
        var (best, count) = 0 to 0
        for (i in 0..200) {
            for (j in -200..200) {
                var (vx, vy) = i to j
                var (x, y, max) = Triple(0, 0, 0)
                for (k in 0..500) {
                    x += vx
                    y += vy
                    max = maxOf(max, y)
                    if (x in x1..x2 && y in y1..y2) {
                        best = maxOf(best, max)
                        count++
                        break
                    }
                    if (vx > 0) vx-- else if (vx < 0) vx++
                    vy--
                }
            }
        }
        return best to count
    }

    fun part1(input: List<String>) = solve(input[0]).first
    fun part2(input: List<String>) = solve(input[0]).second

    val testInput = readInput("Day17_test")
    checkEquals(part1(testInput), 45)
    checkEquals(part2(testInput), 112)

    val input = readInput("Day17")
    println(part1(input))
    println(part2(input))
}
