private val String.missingChar get() = ('a'..'g').find { it !in this }!!
private val String.missingChars get() = ('a'..'g').filter { it !in this }

fun main() {

    fun String.partition(): List<List<String>> {
        return this.split("|").map {
            it.trim().split(" ").map { segment ->
                segment.toList().sorted().joinToString("")
            }
        }
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            line.partition()[1].count { it.length in listOf(2, 3, 4, 7) }
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val (left, right) = line.partition()
            val wordsOfLen = List(8) { len -> left.filter { it.length == len } }
            val segment = Array(10) { "" }
            segment[1] = wordsOfLen[2].single()
            segment[4] = wordsOfLen[4].single()
            segment[7] = wordsOfLen[3].single()
            segment[8] = wordsOfLen[7].single()
            segment[6] = wordsOfLen[6].find { it.missingChar in segment[1] }!!
            segment[9] = wordsOfLen[6].find { it.missingChar !in segment[4] }!!
            segment[0] = wordsOfLen[6].find { it != segment[6] && it != segment[9] }!!
            segment[2] = wordsOfLen[5].find { s -> s.missingChars.all { it in segment[4] } }!!
            segment[3] = wordsOfLen[5].find { s -> s.missingChars.none { it in segment[1] } }!!
            segment[5] = wordsOfLen[5].find { it != segment[2] && it != segment[3] }!!
            right.map { segment.indexOf(it) }.joinToString("").toInt()
        }
    }

    val testInput = readInput("Day08_test")
    checkEquals(part1(testInput), 26)
    checkEquals(part2(testInput), 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
