import java.io.File

fun readInput(name: String) = File("src", "$name.txt").readLines()

fun checkEquals(actual: Number, expected: Number) {
    if (expected.toLong() != actual.toLong())
        throw IllegalStateException("\nExpected: $expected\nFound: $actual")
}