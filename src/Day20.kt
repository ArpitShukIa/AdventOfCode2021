fun main() {

    fun List<MutableList<Char>>.at(i: Int, j: Int, inf: Char) =
        if (i in indices && j in this[0].indices) this[i][j] else inf

    fun solve(input: List<String>, times: Int): Int {
        val algo = input[0]
        var image = input.drop(2)
        var inf = '.'
        repeat(times) {
            val (m, n) = image.size to image[0].length
            val img = List(m + 4) { MutableList(n + 4) { inf } }
            for (i in image.indices) for (j in image[0].indices) img[i + 2][j + 2] = image[i][j]
            image = img.indices.map { i ->
                img[0].indices.map { j ->
                    var bin = ""
                    for (x in (i - 1)..(i + 1))
                        for (y in (j - 1)..(j + 1))
                            bin += img.at(x, y, inf).let { if (it == '#') "1" else "0" }
                    algo[bin.toInt(2)]
                }.joinToString("")
            }
            if (algo[0] == '#') inf = if (inf == '.') '#' else '.'
        }
        return image.joinToString("").count { it == '#' }
    }

    fun part1(input: List<String>) = solve(input, 2)
    fun part2(input: List<String>) = solve(input, 50)

    val testInput = readInput("Day20_test")
    checkEquals(part1(testInput), 35)
    checkEquals(part2(testInput), 3351)

    val input = readInput("Day20")
    println(part1(input))
    println(part2(input))
}
