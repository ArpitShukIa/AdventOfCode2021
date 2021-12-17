import java.math.BigInteger

fun main() {

    val input = readInput("Day16").first()
    var bits = BigInteger(input, 16).toString(2).let { "000$it".takeLast(input.length * 4) }
    var versionSum = 0

    fun pick(n: Int) = bits.take(n).also { bits = bits.drop(n) }

    fun solve(): Long {
        versionSum += pick(3).toInt(2)
        val typeId = pick(3).toInt(2)

        if (typeId == 4) {
            var str = ""
            while (true) {
                val lengthTypeId = pick(1)
                str += pick(4)
                if (lengthTypeId == "0") break
            }
            return BigInteger(str, 2).toLong()
        }

        return mutableListOf<Long>().run {
            if (pick(1) == "0") {
                val endLength = pick(15).toInt(2).let { bits.length - it }
                while (bits.length > endLength) {
                    add(solve())
                }
            } else {
                repeat(pick(11).toInt(2)) {
                    add(solve())
                }
            }
            when (typeId) {
                0 -> sum()
                1 -> fold(1L, Long::times)
                2 -> minOf { it }
                3 -> maxOf { it }
                5 -> if (this[0] > this[1]) 1 else 0
                6 -> if (this[0] < this[1]) 1 else 0
                7 -> if (this[0] == this[1]) 1 else 0
                else -> throw IllegalStateException()
            }
        }
    }

    println(solve().let { versionSum to it })
}
