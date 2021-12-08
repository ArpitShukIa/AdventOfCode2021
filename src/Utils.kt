import java.io.File

fun readInput(name: String) = File("src", "$name.txt").readLines()

fun checkEquals(actual: Number, expected: Number) {
    if (expected != actual)
        throw IllegalStateException("\nExpected: $expected\nFound: $actual")
}