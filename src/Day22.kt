data class Instruction(val on: Boolean, val x1: Int, val x2: Int, val y1: Int, val y2: Int, val z1: Int, val z2: Int)

fun main() {

    fun parseInput(input: List<String>): List<Instruction> {
        val regex = Regex("x=([-\\d]+)..([-\\d]+),y=([-\\d]+)..([-\\d]+),z=([-\\d]+)..([-\\d]+)")
        return input.map {
            val l = regex.matchEntire(it.substringAfter(" "))!!.groupValues.drop(1).map(String::toInt)
            Instruction("on" in it, l[0], l[1], l[2], l[3], l[4], l[5])
        }
    }

    fun part1(input: List<String>): Int {
        val instructions = parseInput(input).filter { instruction ->
            instruction.run { listOf(x1, x2, y1, y2, z1, z2).all { it in -50..50 } }
        }
        val cubes = List(101) { List(101) { MutableList(101) { false } } }
        instructions.forEach {
            for (x in it.x1..it.x2) for (y in it.y1..it.y2) for (z in it.z1..it.z2)
                cubes[x + 50][y + 50][z + 50] = it.on
        }
        return cubes.flatten().flatten().count { it }
    }

    fun part2(input: List<String>): Long {
        val instructions = parseInput(input)
        val cubes = mutableListOf<Instruction>()
        instructions.forEach {
            val overlaps = mutableListOf<Instruction>()
            cubes.forEach { cube ->
                val x1 = maxOf(it.x1, cube.x1)
                val x2 = minOf(it.x2, cube.x2)
                val y1 = maxOf(it.y1, cube.y1)
                val y2 = minOf(it.y2, cube.y2)
                val z1 = maxOf(it.z1, cube.z1)
                val z2 = minOf(it.z2, cube.z2)

                if (x1 <= x2 && y1 <= y2 && z1 <= z2)
                    overlaps.add(Instruction(!cube.on, x1, x2, y1, y2, z1, z2))
            }
            cubes.addAll(overlaps)
            if (it.on) cubes.add(it)
        }
        return cubes.sumOf { (if (it.on) 1L else -1L) * (it.x2 - it.x1 + 1) * (it.y2 - it.y1 + 1) * (it.z2 - it.z1 + 1) }
    }

    val input = readInput("Day22")
    println(part1(input))
    println(part2(input))
}
