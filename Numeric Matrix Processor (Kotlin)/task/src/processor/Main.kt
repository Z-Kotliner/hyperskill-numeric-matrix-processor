package processor

fun main() {
    val (n, m) = readln().split(" ").map { it.toInt() }
    val matrixA = Array(n) { readln().split(" ").map { it.toInt() }.toIntArray() }

    val c = readln().toInt()

    printMatrix(matrixScalarProduct(matrixA, c))
}

fun matrixSum(matrixA: Array<IntArray>, matrixB: Array<IntArray>): Array<IntArray> =
    matrixA.zip(matrixB).map { pair -> pair.first.zip(pair.second).map { it.first + it.second }.toIntArray() }
        .toTypedArray()

fun matrixScalarProduct(matrix: Array<IntArray>, c: Int): Array<IntArray> =
    matrix.map { row -> row.map { it * c }.toIntArray() }.toTypedArray()

fun printMatrix(matrix: Array<IntArray>) {
    println()
    matrix.forEach {
        println(it.joinToString(" "))
    }
}