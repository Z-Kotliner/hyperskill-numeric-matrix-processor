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
            4. Transpose matrix
            5. Calculate a determinant
            6. Inverse matrix
            0. Exit
        """.trimIndent()
        )

        print("Your choice: ")
        val input = readln().toInt()

        when (input) {
            1 -> addMatrices()
            2 -> multiplyMatrixByScalar()
            3 -> multiplyMatrices()
            4 -> transposeMatrix()
            5 -> calculateDeterminant()
            6 -> inverseMatrix()
            0 -> break
            else -> continue
        }
    }
}

fun readDoubleInputs() = readln().split(" ").map { it.toDouble() }
fun readIntInputs() = readln().split(" ").map { it.toInt() }

private fun readMatrix(): Array<DoubleArray> {
    println("Enter matrix size:")
    val (n, m) = readIntInputs()

    println("Enter matrix:")
    val matrix = Array(n) { readDoubleInputs().toDoubleArray() }
    return matrix
}

private fun readTwoMatrices(): Pair<Array<DoubleArray>, Array<DoubleArray>> {
    println("Enter size of first matrix: ")
    val (n, m) = readIntInputs()

    println("Enter first matrix:")
    val matrixA = Array(n) { readDoubleInputs().toDoubleArray() }

    println("Enter size of second matrix: ")
    val (n1, m1) = readIntInputs()

    println("Enter second matrix:")
    val matrixB = Array(n1) { readDoubleInputs().toDoubleArray() }

    return Pair(matrixA, matrixB)
}


fun inverseMatrix() {
    val matrix = readMatrix()

    // verify
    if (determinant(matrix) == 0.0 || !matrix.all { it.size == matrix.size }) {
        println("This matrix doesn't have an inverse.")
        return
    }

    val determinant = determinant(matrix)

    val cofactorMatrix = cofactorOfMatrix(matrix)

    val cofactorTranspose = transposeByMainDiagonal(cofactorMatrix)

    val inverse = matrixScalarProduct(cofactorTranspose, 1.0 / determinant)

    println("The result is:")
    printMatrix(inverse)
}

fun calculateDeterminant() {
    val matrix = readMatrix()

    if (!matrix.all { it.size == matrix.size }) {
        println("Undefined determinant")
        return
    }

    println("The result is:")
    println(determinant(matrix))
}

fun transposeMatrix() {
    println(
        """
        1. Main diagonal
        2. Side diagonal
        3. Vertical line
        4. Horizontal line
    """.trimIndent()
    )

    println("Your choice:")
    val k = readln().toInt()

    val matrix = readMatrix()

    val a = matrix[0].size
    val b = matrix.size

    val transpose = when (k) {
        1 -> transposeByMainDiagonal(matrix)
        2 -> transposeBySideDiagonal(matrix, b, a)
        3 -> matrix.map { r -> r.reversedArray() }.toTypedArray()
        4 -> matrix.reversedArray()
        else -> return
    }

    println("The result is:")
    printMatrix(transpose)
}

fun addMatrices() {
    val (matrixA, matrixB) = readTwoMatrices()

    if (matrixA.size != matrixB.size || matrixA[0].size != matrixB[0].size) {
        println("The operation cannot be performed.")
        return
    }

    val sumMatrix = matrixSum(matrixA, matrixB)

    println("The result is:")
    printMatrix(sumMatrix)
}

fun multiplyMatrixByScalar() {
    val matrix = readMatrix()

    println("Enter constant: ")
    val c = readln().toDouble()

    println("The result is:")
    printMatrix(matrixScalarProduct(matrix, c))
}

fun multiplyMatrices() {
    val (matrixA, matrixB) = readTwoMatrices()

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

fun matrixScalarProduct(matrix: Array<DoubleArray>, c: Double): Array<DoubleArray> =
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

fun determinant(matrix: Array<DoubleArray>): Double {
    return when (matrix.size) {
        1 -> matrix[0][0]
        2 -> matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]
        else -> matrix[0].indices.sumOf { c ->
            (if (c % 2 == 0) 1.0 else -1.0) * matrix[0][c] * determinant(minor(matrix, 0, c))
        }
    }
}

fun minor(matrix: Array<DoubleArray>, row: Int, col: Int): Array<DoubleArray> =
    matrix.filterIndexed { x, _ -> x != row }.map { it.filterIndexed { c, _ -> c != col }.toDoubleArray() }
        .toTypedArray()

private fun cofactorOfMatrix(matrix: Array<DoubleArray>): Array<DoubleArray> {
    return Array(matrix.size) { i ->
        DoubleArray(matrix.size) { j ->
            (if ((i + j) % 2 == 0) 1.0 else -1.0) * determinant(minor(matrix, i, j))
        }
    }
}

private fun transposeBySideDiagonal(matrix: Array<DoubleArray>, b: Int, a: Int): Array<DoubleArray> =
    Array(matrix[0].size) { y -> DoubleArray(matrix.size) { x -> matrix[b - 1 - x][a - 1 - y] } }

private fun transposeByMainDiagonal(matrix: Array<DoubleArray>): Array<DoubleArray> =
    Array(matrix[0].size) { y -> DoubleArray(matrix.size) { x -> matrix[x][y] } }

fun printMatrix(matrix: Array<DoubleArray>) {
    println()
    matrix.forEach {
        println(it.joinToString(" "))
    }
}