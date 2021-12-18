private class Node(
    var left: Node? = null,
    var right: Node? = null,
    var value: Int? = null,
    var parent: Node? = null,
) {
    val magnitude: Int get() = value ?: (3 * left!!.magnitude + 2 * right!!.magnitude)

    operator fun plus(other: Node): Node {
        return Node(this, other).apply {
            left?.parent = this
            right?.parent = this
            reduce()
        }
    }

    fun reduce() {
        findNodeToExplode()?.apply {
            val (x, y) = left?.value!! to right?.value!!
            left = null
            right = null
            findNodeOnLeft()?.let { it.value = it.value!! + x }
            findNodeOnRight()?.let { it.value = it.value!! + y }
            value = 0
            this@Node.reduce()
        }
        findNodeToSplit()?.apply {
            val (a, b) = value!!.let { it / 2 to (it + 1) / 2 }
            left = Node(value = a, parent = this)
            right = Node(value = b, parent = this)
            value = null
            this@Node.reduce()
        }
    }

    fun findNodeToExplode(d: Int = 0): Node? =
        if (value == null && d >= 4) this else left?.findNodeToExplode(d + 1) ?: right?.findNodeToExplode(d + 1)

    fun findNodeToSplit(): Node? =
        if ((value ?: 0) >= 10) this else left?.findNodeToSplit() ?: right?.findNodeToSplit()

    fun findNodeOnLeft(): Node? {
        if (value != null) return this
        if (this == parent?.left) return parent!!.findNodeOnLeft()
        if (this == parent?.right) return parent!!.left?.findRightMostNode()
        return null
    }

    fun findNodeOnRight(): Node? {
        if (value != null) return this
        if (this == parent?.left) return parent!!.right?.findLeftMostNode()
        if (this == parent?.right) return parent!!.findNodeOnRight()
        return null
    }

    fun findLeftMostNode(): Node? = if (value != null) this else left?.findLeftMostNode()
    fun findRightMostNode(): Node? = if (value != null) this else right?.findRightMostNode()
}

fun main() {

    fun getNode(input: String): Node {
        var i = 0
        fun parse(parent: Node): Node = Node(parent = parent).apply {
            if (input[i++] == '[') {
                left = parse(this).also { i++ }
                right = parse(this).also { i++ }
            } else value = input[i - 1].digitToInt()
        }
        return parse(Node())
    }

    fun part1(input: List<String>): Int {
        return input.map(::getNode).reduce(Node::plus).magnitude
    }

    fun part2(input: List<String>): Int {
        return input.indices.flatMap { i -> input.indices.map { j -> i to j } }
            .filter { (i, j) -> i != j }
            .maxOf { (i, j) -> (getNode(input[i]) + getNode(input[j])).magnitude }
    }

    val testInput = readInput("Day18_test")
    checkEquals(part1(testInput), 4140)
    checkEquals(part2(testInput), 3993)

    val input = readInput("Day18")
    println(part1(input))
    println(part2(input))
}
