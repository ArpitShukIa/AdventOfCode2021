import kotlin.math.abs

data class Point(val x: Int, val y: Int, val z: Int) {

    operator fun plus(other: Point) = Point(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Point) = Point(x - other.x, y - other.y, z - other.z)
    fun dist(other: Point) = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)

    private fun faces() = listOf(
        Point(x, y, z), Point(x, -y, -z), Point(x, -z, y), Point(-y, -z, x), Point(y, -z, -x), Point(-x, -z, -y)
    )

    private fun rotations() = listOf(
        Point(x, y, z), Point(-y, x, z), Point(-x, -y, z), Point(y, -x, z)
    )

    fun rotate(n: Int) = faces()[n % 6].rotations()[n / 6 % 4]

}

fun main() {

    fun solve(input: List<String>): Pair<Int, Int> {
        val scanners = mutableListOf<MutableList<Point>>()
        input.filter { it.isNotEmpty() }.forEach { s ->
            if ("scanner" in s) scanners.add(mutableListOf())
            else scanners.last().add(s.split(",").map { it.toInt() }.let { Point(it[0], it[1], it[2]) })
        }

        val pos = arrayOfNulls<Point>(scanners.size)
        pos[0] = Point(0, 0, 0)

        fun overlap(i: Int, j: Int): Boolean {
            repeat(24) { rot ->
                val rotatedScanner = scanners[j].map { it.rotate(rot) }
                for (beacon in scanners[i])
                    for (rotatedBeacon in rotatedScanner) {
                        val diff = beacon - rotatedBeacon
                        val newBeacons = rotatedScanner.map { it + diff }
                        if ((scanners[i].toSet() intersect newBeacons.toSet()).size >= 12) {
                            scanners[j] = newBeacons.toMutableList()
                            pos[j] = diff
                            return true
                        }
                    }
            }
            return false
        }

        while (pos.any { it == null }) {
            for (i in scanners.indices)
                for (j in scanners.indices)
                    if (pos[i] != null && pos[j] == null)
                        overlap(i, j)
        }

        val beacons = mutableSetOf<Point>()
        scanners.forEach { beacons.addAll(it) }
        val distances = buildList { for (i in pos) for (j in pos) add(i!!.dist(j!!)) }
        return beacons.size to distances.maxOf { it }
    }

    val testInput = readInput("Day19_test")
    check(solve(testInput) == 79 to 3621)

    val input = readInput("Day19")
    println(solve(input))
}