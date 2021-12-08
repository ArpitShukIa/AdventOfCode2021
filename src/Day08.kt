fun main() {

    fun String.partition() = split("|").map { it.trim().split(" ").map(String::toSet) }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            line.partition()[1].count { it.size in listOf(2, 3, 4, 7) }
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val (left, right) = line.partition()
            val wordsOfLen = List(8) { len -> left.filter { it.size == len } }
            val arr = Array(10) { setOf<Char>() }
            arr[1] = wordsOfLen[2].first()
            arr[4] = wordsOfLen[4].first()
            arr[7] = wordsOfLen[3].first()
            arr[8] = wordsOfLen[7].first()
            arr[6] = wordsOfLen[6].first { it + arr[1] == arr[8] }
            arr[5] = wordsOfLen[5].first { it + arr[6] != arr[8] }
            arr[2] = wordsOfLen[5].first { it + arr[5] == arr[8] }
            arr[9] = wordsOfLen[6].first { it + arr[4] != arr[8] }
            arr[0] = wordsOfLen[6].first { it + arr[5] == arr[8] }
            arr[3] = wordsOfLen[5].first { it !in arr }
            right.map { arr.indexOf(it) }.joinToString("").toInt()
        }
    }

    val testInput = readInput("Day08_test")
    checkEquals(part1(testInput), 26)
    checkEquals(part2(testInput), 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
