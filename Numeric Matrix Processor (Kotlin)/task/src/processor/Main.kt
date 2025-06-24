package processor

fun main() {
    val (n, m) = readln().split(" ").map { it.toInt() }
    val matrixA = Array(n) { readln().split(" ").map { it.toInt() }.toIntArray() }


    val (n1, m1) = readln().split(" ").map { it.toInt() }
    val matrixB = Array(n1) { readln().split(" ").map { it.toInt() }.toIntArray() }

    if (n1 != n || m1 != m) {
        println("ERROR")
        return
    }

    val sumMatrix = matrixSum(matrixA, matrixB)
    printMatrix(sumMatrix)
}

fun matrixSum(matrixA: Array<IntArray>, matrixB: Array<IntArray>): Array<IntArray> =
    matrixA.zip(matrixB).map { pair -> pair.first.zip(pair.second).map { it.first + it.second }.toIntArray() }
        .toTypedArray()

fun printMatrix(matrix: Array<IntArray>) {
    println()
    matrix.forEach {
        println(it.joinToString(" "))
    }
}