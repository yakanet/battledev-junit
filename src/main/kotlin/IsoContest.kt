/*******
 * Read input from System.in
 * Use: println to ouput your result to STDOUT.
 * Use: System.err.println to ouput debugging information to STDERR.
 * ***/

fun main() {
    val input = generateSequence(::readLine)
    val lines = input.toList().drop(1)
    val result = mutableMapOf<String, MutableList<String>>()
    lines.forEach { line ->
        val (key, value) = line.split(" ")
        result.putIfAbsent(value, mutableListOf())
        result[value]?.add(key)
    }
    result.entries.first { it.value.size==1 }.let {
        println(it.value.first())
    }
}
