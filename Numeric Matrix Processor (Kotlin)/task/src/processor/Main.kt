package processor

import kotlin.DoubleArray
import kotlin.collections.component1
import kotlin.collections.component2

fun main() {
    while (true) {
        println(
            """
            1. Add matrices
            2. Multiply matrix by a constant
            3. Multiply matrices
            0. Exit
        """.trimIndent()
        )

        print("Your choice: ")
        val input = readln().toInt()

        when (input) {
            1 -> addMatrices()
            2 -> multiplyMatrixByScalar()
            3 -> multiplyMatrices()
            0 -> break
            else -> continue
        }
    }
}

fun readDoubleInputs() = readln().split(" ").map { it.toDouble() }
fun readIntInputs() = readln().split(" ").map { it.toInt() }

fun addMatrices() {
    println("Enter size of first matrix: ")
    val (n, m) = readIntInputs()

    println("Enter first matrix:")
    val matrixA = Array(n) { readDoubleInputs().toDoubleArray() }

    println("Enter size of second matrix: ")
    val (n1, m1) = readIntInputs()

    println("Enter second matrix:")
    val matrixB = Array(n1) { readDoubleInputs().toDoubleArray() }

    if (n1 != n || m1 != m) {
        println("The operation cannot be performed.")
        return
    }

    val sumMatrix = matrixSum(matrixA, matrixB)

    println("The result is:")
    printMatrix(sumMatrix)
}

fun multiplyMatrixByScalar() {
    println("Enter size of first matrix: ")
    val (n, m) = readIntInputs()

    println("Enter matrix:")
    val matrix = Array(n) { readDoubleInputs().toDoubleArray() }

    println("Enter constant: ")
    val c = readln().toInt()

    println("The result is:")
    printMatrix(matrixScalarProduct(matrix, c))
}

fun multiplyMatrices() {
    println("Enter size of first matrix: ")
    val (n, m) = readIntInputs()

    println("Enter first matrix:")
    val matrixA = Array(n) { readDoubleInputs().toDoubleArray() }

    println("Enter size of second matrix: ")
    val (n1, m1) = readIntInputs()

    println("Enter second matrix:")
    val matrixB = Array(n1) { readDoubleInputs().toDoubleArray() }

    // verify
    if (matrixA[0].size != matrixB.size) {
        println("The operation cannot be performed.")
        return
    }

    val productMatrix = matrixMultiplication(matrixA, matrixB)

    println("The result is:")
    printMatrix(productMatrix)
}

fun matrixSum(matrixA: Array<DoubleArray>, matrixB: Array<DoubleArray>): Array<DoubleArray> =
    matrixA.zip(matrixB).map { pair -> pair.first.zip(pair.second).map { it.first + it.second }.toDoubleArray() }
        .toTypedArray()

fun matrixScalarProduct(matrix: Array<DoubleArray>, c: Int): Array<DoubleArray> =
    matrix.map { row -> row.map { it * c }.toDoubleArray() }.toTypedArray()

fun matrixMultiplication(matrixA: Array<DoubleArray>, matrixB: Array<DoubleArray>): Array<DoubleArray> =
    Array(matrixA.size) { i ->
        DoubleArray(matrixB[0].size) { j ->
            var sum = 0.0
            for (k in 0 until matrixA[0].size) {
                sum += matrixA[i][k] * matrixB[k][j]
            }
            sum
        }
    }


fun printMatrix(matrix: Array<DoubleArray>) {
    println()
    matrix.forEach {
        println(it.joinToString(" "))
    }
}